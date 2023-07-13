package com.miseri.miserisense;

import com.miseri.miserisense.controllers.dtos.request.CorrelationRequest;
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
//		List<Double> listx=new ArrayList<>(Arrays.asList(1.0, 3.0, 5.0, 7.0, 9.0));
		List<Double> listx=new ArrayList<>(Arrays.asList(2.0, 4.0, 6.0, 8.0, 10.0, 12.0, 14.0, 16.0));
		List<Double> listy=new ArrayList<>(Arrays.asList(2.0, 4.0, 6.0, 8.0, 10.0, 12.0, 14.0, 16.0));
		CorrelationRequest request=new CorrelationRequest();

		request.setXdata(listx);
		request.setYdata(listy);
		balanceArrangements(request);

		System.out.println("x size: "+request.getXdata().size());
		System.out.println("y size: "+request.getYdata().size());

		Double correlation=madeCalculateOfCorrelaion(request);
		System.out.println("Correlation: "+correlation);

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

	private Double roundUnit(Double valor, int cantDec){
		double factor = Math.pow(10, cantDec);
		return Math.round(valor * factor) / factor;
	}


}
