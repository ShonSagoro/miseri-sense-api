package com.miseri.miserisense;

import com.miseri.miserisense.controllers.dtos.response.DataFrequencyTableResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class MiseriSenseApplicationTests {

	@Test
	void contextLoads() {
		List<Double> list= Arrays.asList(50.0,56.0,55.0,49.0,52.0,57.0,56.0,57.0,56.0,59.0,54.0,55.0,61.0,60.0,51.0,
				59.0,62.0,52.0,54.0,49.0);
		List<Double> sortedList=list.stream().sorted().toList();

		Double range=getRange(sortedList);
		System.out.println(range);
		Integer klasses=getKlasses(sortedList.size());
		System.out.println(klasses);
		Double amplitude=getAmplitude(range, klasses);
		System.out.println(amplitude);
		Double unit=getVariationUnit(sortedList);
		System.out.println(unit);

		List<DataFrequencyTableResponse> listFrequency=makeFrequencyTable(sortedList, klasses, amplitude, unit);

		listFrequency.forEach(x-> System.out.println(x.toString()));

		Double media=getMediana(listFrequency, sortedList.size(),amplitude);
		Double mediaArit=getAritmethicMedia(listFrequency, sortedList.size());
		Double moda=getModa(listFrequency,amplitude);

		System.out.println("Media arit: "+ mediaArit);
		System.out.println("Moda: "+moda);
		System.out.println("media: "+media);


		Double DM=getMeanDeviation(listFrequency, media, list.size());
		Double variance=getVariance(listFrequency,media,list.size());
		Double DE=getStandardDeviation(variance);
		System.out.println("DM: "+DM);
		System.out.println("variance: "+variance);
		System.out.println("DE: "+DE);


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
		return (double) Math.pow(10, -1 * maxDecimalNumber);
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
		Double maxValor = null;
		Integer maxFilaIndex = null;

		for (int index = 0; index < data.size(); index++) {
			DataFrequencyTableResponse fila = data.get(index);

			if (maxValor == null || fila.getFrequency() > maxValor) {
				maxValor = Double.valueOf(fila.getFrequency());
				maxFilaIndex = index;
			}
		}
		Double limInfEx=data.get(maxFilaIndex).getLimInfEx();
		double d1 = data.get(maxFilaIndex).getFrequency()- data.get(maxFilaIndex - 1).getFrequency();
		double d2 = data.get(maxFilaIndex).getFrequency()- data.get(maxFilaIndex + 1).getFrequency();

		return roundUnit(limInfEx + (d1 / (d1 + d2)) * amplitude, 4);
	}

	private Double getMediana(List<DataFrequencyTableResponse> data, Integer size, Double amplitude){
		Integer location= (size+1)/2;
		Integer mediaIndex=null;

		for (int index = 0; index < data.size(); index++) {
			DataFrequencyTableResponse fila = data.get(index);

			if (mediaIndex == null && fila.getCumulativeFrequency()>=location){
				mediaIndex=index;
			}
		}
		Double limInfEx= data.get(mediaIndex).getLimInfEx();
		Integer mediaN= (int) data.size()/2;
		System.out.println(mediaN);
		Double lastFreq= Double.valueOf(data.get(mediaN-1).getFrequency());
		Double freq=Double.valueOf(data.get(mediaN).getFrequency());


		return  roundUnit(limInfEx+((mediaN-lastFreq)/freq)*(amplitude), 4);

	}

	private Double roundUnit(Double valor, int cantDec){
		double factor = Math.pow(10, cantDec);
		return Math.round(valor * factor) / factor;
	}

	private Double getMeanDeviation(List<DataFrequencyTableResponse> data, Double media, Integer size){
		double dataDM=0.0;
		for (DataFrequencyTableResponse number: data){
			dataDM=Math.abs(number.getFrequency()-media);
		}
		return roundUnit(dataDM/size,4);
	}

	private Double getVariance(List<DataFrequencyTableResponse> data, Double media,Integer size){
		double variance=0.0;
		for (DataFrequencyTableResponse number: data){
			variance=Math.pow(2, (number.getClassMark()-media)*number.getFrequency());
		}
		return roundUnit(variance/size,4);
	}

	private  Double getStandardDeviation(Double variance){
		return roundUnit(Math.sqrt(variance), 4);
	}


}
