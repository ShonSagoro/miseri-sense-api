package com.miseri.miserisense.services;

import com.miseri.miserisense.controllers.dtos.request.CorrelationRequest;
import com.miseri.miserisense.controllers.dtos.response.BaseResponse;
import com.miseri.miserisense.controllers.dtos.response.DataFrequencyTableResponse;
import com.miseri.miserisense.controllers.dtos.response.FrequencyResponse;
import com.miseri.miserisense.controllers.dtos.response.GetCorrelationResponse;
import com.miseri.miserisense.services.intefaces.ISensorDataService;
import com.miseri.miserisense.services.intefaces.IStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticServiceImpl implements IStatisticService {

    @Autowired
    private ISensorDataService sensorDataService;

    @Override
    public BaseResponse getAllDataFrequency() {
        List<FrequencyResponse> responses=getListFrequency();
        return BaseResponse.builder()
                .data(responses)
                .message("All calculates made")
                .success(true)
                .httpStatus(HttpStatus.FOUND).build();
    }

    @Override
    public BaseResponse getGasDataFrequency() {
        List<Double> list=sensorDataService.getAllGas();
        FrequencyResponse response=getFrequency(list, "Gas");
        return BaseResponse.builder()
                .data(response)
                .message("data frequency of gas")
                .success(true)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public BaseResponse getQualityAirDataFrequency() {
        List<Double> list=sensorDataService.getAllQualityAir();
        FrequencyResponse response=getFrequency(list, "Quality Air");
        return BaseResponse.builder()
                .data(response)
                .message("data frequency of quality air")
                .success(true)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public BaseResponse getLightDataFrequency() {
        List<Double> list=sensorDataService.getAllLight();
        FrequencyResponse response=getFrequency(list, "Light");
        return BaseResponse.builder()
                .data(response)
                .message("data frequency of light")
                .success(true)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public BaseResponse getHumidityDataFrequency() {
        List<Double> list=sensorDataService.getAllHumidity();
        FrequencyResponse response=getFrequency(list, "Humidity");
        return BaseResponse.builder()
                .data(response)
                .message("data frequency of humidity")
                .success(true)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public BaseResponse getTemperatureDataFrecuency() {
        List<Double> list=sensorDataService.getAllTemperature();
        FrequencyResponse response=getFrequency(list, "Temperature");
        return BaseResponse.builder()
                .data(response)
                .message("data frequency of temperature")
                .success(true)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public BaseResponse getCorrelation(CorrelationRequest request) {
        balanceArrangements(request);
        GetCorrelationResponse response=new GetCorrelationResponse();
        response.setCorrelation(madeCalculateOfCorrelaion(request));
        return BaseResponse.builder()
                .data(response)
                .message("The correlation is: "+response.getCorrelation())
                .success(true)
                .httpStatus(HttpStatus.OK).build();
    }


    private void balanceArrangements(CorrelationRequest request){
        if(request.getXdata().size()!=request.getYdata().size()){
            int maxIndex=Math.max(request.getXdata().size(),request.getYdata().size());
            int maxXIndex=maxIndex-request.getXdata().size();
            int maxYIndex=maxIndex-request.getYdata().size();
            balance(request.getYdata(), maxYIndex);
            balance(request.getXdata(), maxXIndex);
        }
    }

    private void balance(List<Double> list, int maxIndex){
        for ( int i= 0;  i<maxIndex ; ++i) {
            list.add(0.0);
        }
    }
    private Double madeCalculateOfCorrelaion(CorrelationRequest request){
        double xMedia=getAritmethicMediaCorrelation(request.getXdata());
        double yMedia=getAritmethicMediaCorrelation(request.getYdata());
        Double typicalDeviationX=0.0;
        Double typicalDeviationY=0.0;
        Double typicalDeviationXY=0.0;
        int maxIndex=Math.max(request.getXdata().size(),request.getYdata().size());
        for (int i=0; i<maxIndex;i++) {
            typicalDeviationX+=Math.pow(request.getXdata().get(i)-xMedia, 2);
            typicalDeviationY+=Math.pow(request.getYdata().get(i)-yMedia,2);
            typicalDeviationXY+=(request.getXdata().get(i)-xMedia)*(request.getYdata().get(i)-yMedia);

        }
        Double result= typicalDeviationXY/Math.sqrt(typicalDeviationX*typicalDeviationY);
        return roundUnit(result, 4);
    }


    private Double getAritmethicMediaCorrelation(List<Double> list){
        double sum=0.0;
        for (Double number:list) {
            sum+=number;
        }
        return roundUnit(sum/list.size(), 4);
    }



    private List<FrequencyResponse> getListFrequency(){
        List<FrequencyResponse> response= new ArrayList<>();
        response.add(getFrequency(sensorDataService.getAllGas(),"Gas"));
        response.add(getFrequency(sensorDataService.getAllLight(),"Light"));
        response.add(getFrequency(sensorDataService.getAllHumidity(),"Humidity"));
        response.add(getFrequency(sensorDataService.getAllQualityAir(),"Quality Air"));
        response.add(getFrequency(sensorDataService.getAllTemperature(),"Temperature"));
        return response;
    }


    private FrequencyResponse getFrequency(List<Double> list, String sensor){
        FrequencyResponse response=new FrequencyResponse();

        response.setSensor(sensor);
        List<Double> sortedList=list.stream().sorted().toList();

        Double range=getRange(sortedList);
        response.setRange(range);

        Integer klasses=getKlasses(sortedList.size());
        response.setKlasses(klasses);

        Double amplitude=getAmplitude(range, klasses);
        response.setAmplitude(amplitude);

        Double unit=getVariationUnit(sortedList);
        response.setUnit(unit);

        List<DataFrequencyTableResponse> listFrequency=makeFrequencyTable(sortedList, klasses, amplitude, unit);
        response.setFrequency(listFrequency);

        Double media=getMediana(listFrequency, sortedList.size(),amplitude);
        Double mediaArit=getAritmethicMedia(listFrequency, sortedList.size());
        Double moda=getModa(listFrequency,amplitude);
        response.setMedia(media);
        response.setMediaArit(mediaArit);
        response.setModa(moda);


        Double meanDeviation=getMeanDeviation(listFrequency, media, list.size());
        Double variance=getVariance(listFrequency,media,list.size());
        Double standardDeviation=getStandardDeviation(variance);
        response.setMeanDeviation(meanDeviation);
        response.setVariance(variance);
        response.setStandardDeviation(standardDeviation);
        return response;
    }


    private Double getRange(List<Double> data){
        return roundUnit(data.get(data.size()-1) -data.get(0),4);
    }

    private Integer getKlasses(Integer length){
        return (int) (1+3.322*Math.log10(length));
    }

    private Double getAmplitude(Double range, Integer klasses){

        return (double) ((range%klasses==0)? range/klasses+1  : range/klasses);

    }

    private Integer getDigits(Double data){
        int digitDecimal = 0;
        if (String.valueOf(data).contains(".")) {
            digitDecimal = Math.max(String.valueOf(data).split("\\.")[1].length(), digitDecimal);
        }
        return digitDecimal;
    }

    private Double getVariationUnit(List<Double> data){
        int maxDecimalNumber= 0;
        for(Double number: data){
            int decimalCount = getDigits(number);
            maxDecimalNumber = Math.max(decimalCount, maxDecimalNumber);
        }
        return (double) roundUnit(Math.pow(10, -1 * maxDecimalNumber),4);
    }


    private List<DataFrequencyTableResponse> makeFrequencyTable(List<Double> data, Integer klasses, Double amplitude, Double variationUnit){
        Double limInf=data.get(0);
        Integer cumulativeFrequency=0;
        int cantDec=4;
        List<DataFrequencyTableResponse> response = new ArrayList<>();
        for (int i = 0; i < klasses; i++) {
            Double limSup= limInf+amplitude-variationUnit;
            Integer frequency = getFrequencyRange(data, limInf, limSup);
            cumulativeFrequency  += frequency;
            DataFrequencyTableResponse frequencyTableResponse= new DataFrequencyTableResponse();
            frequencyTableResponse.setLimInf(roundUnit(limInf,cantDec));
            frequencyTableResponse.setLimSup(roundUnit(limSup,cantDec));
            frequencyTableResponse.setFrequency(getFrequencyRange(data, limInf, limSup));
            frequencyTableResponse.setClassMark(roundUnit(getMarkClass(limSup,limInf), cantDec));
            frequencyTableResponse.setCumulativeFrequency(cumulativeFrequency);
            frequencyTableResponse.setLimInfEx(roundUnit(limInf - (variationUnit / 2), cantDec));
            frequencyTableResponse.setLimSupEx(roundUnit(limSup+(variationUnit/2), cantDec));
            limInf = limSup + variationUnit;
            response.add(frequencyTableResponse);
        }

        return  response;
    }

    private Double getMarkClass(Double limInf, Double limSup){
        return roundUnit(limInf+limSup/2, 4);
    }


    private Integer getFrequencyRange (List<Double> data, Double limInf, Double limSup){
        Integer frequency=0;
        for (Double number : data) {
            if(number<=limSup && number>=limInf) {
                frequency++;
            }
        }
        return frequency;
    }

    private Double getAritmethicMedia(List<DataFrequencyTableResponse> data, Integer size){
        double summation=0.0;
        for(DataFrequencyTableResponse number: data){
            summation+=number.getFrequency()*number.getClassMark();
        }
        return roundUnit( summation/size,4);
    }

    private Double getModa(List<DataFrequencyTableResponse> data, Double amplitude){
        Double maxValor = 0.0;
        int maxFilaIndex = -1;

        for (int index = 0; index < data.size(); index++) {
            DataFrequencyTableResponse fila = data.get(index);

            if (maxValor == -1 || fila.getFrequency() > maxValor) {
                maxValor = Double.valueOf(fila.getFrequency());
                maxFilaIndex = index;
            }
        }


        Integer dataAnt=maxFilaIndex-1<=-1? 0 : data.get(maxFilaIndex-1).getFrequency();
        Integer dataNext=maxFilaIndex+1 >= data.size()? 0:data.get(maxFilaIndex+1 ).getFrequency();

        Double limInfEx=data.get(maxFilaIndex).getLimInfEx();
        double d1 = data.get(maxFilaIndex).getFrequency()- dataAnt;
        double d2 = data.get(maxFilaIndex).getFrequency()- dataNext;
        double result=limInfEx + (d1 / (d1 + d2)) * amplitude;

        return roundUnit(result, 4);
    }

    private Double getMediana(List<DataFrequencyTableResponse> data, Integer size, Double amplitude){
        Integer location= (size+1)/2;
        int mediaIndex=-1;

        for (int index = 0; index < data.size(); index++) {
            DataFrequencyTableResponse fila = data.get(index);

            if (mediaIndex == -1 && fila.getCumulativeFrequency()>=location){
                mediaIndex=index;
            }
        }
        Double limInfEx= data.get(mediaIndex).getLimInfEx();
        int mediaN= (int) data.size()/2;


        int lastFreq=mediaN-1<=-1? 0 :data.get(mediaN-1).getFrequency();
        int freq=data.get(mediaN).getFrequency()<=0?1:data.get(mediaN).getFrequency();

        Double result=limInfEx+((mediaN-lastFreq)/freq)*(amplitude);
        return  roundUnit(result, 4);

    }



    private Double getMeanDeviation(List<DataFrequencyTableResponse> data, Double media, Integer size){
        double dataDM=0.0;
        for (DataFrequencyTableResponse number: data){
            dataDM+=Math.abs(number.getFrequency()-media);
        }
        double result=dataDM/size;
        return roundUnit(result,4);
    }

    private Double getVariance(List<DataFrequencyTableResponse> data, double media,Integer size){
        double variance=0.0;
        for (DataFrequencyTableResponse number: data){
            variance+=Math.pow((number.getClassMark()-media),2)*number.getFrequency();
        }
        double result=variance/size;
        return roundUnit(result,4);
    }

    private  Double getStandardDeviation(Double variance){
        return roundUnit(Math.sqrt(variance), 4);
    }

    private Double roundUnit(Double valor, int cantDec){
        double factor = Math.pow(10, cantDec);
        return Math.round(valor * factor) / factor;
    }
}
