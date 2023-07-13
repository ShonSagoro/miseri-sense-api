package com.miseri.miserisense.services;

import com.google.gson.Gson;
import com.miseri.miserisense.configuration.SocketIOClient;
import com.miseri.miserisense.controllers.advice.exceptions.NotFoundException;
import com.miseri.miserisense.controllers.dtos.request.CreateSensorDataRequest;
import com.miseri.miserisense.controllers.dtos.request.UpdateSensorDataRequest;
import com.miseri.miserisense.controllers.dtos.response.BaseResponse;
import com.miseri.miserisense.controllers.dtos.response.GetSensorDataResponse;
import com.miseri.miserisense.models.SensorData;
import com.miseri.miserisense.repositories.ISensorDataRepository;
import com.miseri.miserisense.services.intefaces.ISensorDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SensorDataServiceImpl implements ISensorDataService {
    @Autowired
    private ISensorDataRepository repository;

    @Autowired
    private SocketIOClient client;

    @Autowired
    private SequenceGeneratorServiceImpl sequenceGeneratorService;

    @Override
    public BaseResponse get(Long id) {
        SensorData sensorData = repository.findById(id)
                .orElseThrow(NotFoundException::new);
        GetSensorDataResponse response=toGetSensorDataResponse(sensorData);
        return BaseResponse.builder()
                .data(response)
                .message("The sensor data has been found with the id:" + response.getId())
                .success(true)
                .httpStatus(HttpStatus.FOUND).build();
    }

    @Override
    public BaseResponse create(CreateSensorDataRequest request) {
        SensorData sensorData = repository.save(toSensorData(request));
        GetSensorDataResponse response= toGetSensorDataResponse(sensorData);
        client.sendData(toJson(response));
        return BaseResponse.builder()
                .data(response)
                .message("The sensor data has been created with id: "+ response.getId())
                .success(true)
                .httpStatus(HttpStatus.CREATED).build();
    }

    @Override
    public BaseResponse createList(List<CreateSensorDataRequest> request) {
        List<SensorData> listSave=request.stream().map(this::toSensorData).toList();
        listSave.forEach(sensorData -> repository.save(sensorData));
        List<GetSensorDataResponse> responses=listSave.stream().map(this::toGetSensorDataResponse).toList();
        client.sendListData(toJson(responses));
        return BaseResponse.builder()
                .data(responses)
                .message("All data sensor saved bro")
                .success(true)
                .httpStatus(HttpStatus.CREATED).build();
    }

    @Override
    public BaseResponse update(UpdateSensorDataRequest request, Long id) {
        SensorData sensorData = repository.findById(id).orElseThrow(RuntimeException::new);
        SensorData sensorDataSave = repository.save(update(sensorData, request));
        GetSensorDataResponse response=toGetSensorDataResponse(sensorDataSave);
        return BaseResponse.builder()
                .data(response)
                .message("The sensor data has been updated with id: "+ response.getId())
                .success(true)
                .httpStatus(HttpStatus.ACCEPTED).build();
    }

    @Override
    public BaseResponse getAllBySession(String session) {
        List<GetSensorDataResponse> responses=repository.findBySession(session)
                .stream()
                .map(this::toGetSensorDataResponse)
                .toList();

        return BaseResponse.builder()
                .data(responses)
                .message("The sensor data has been found with the session"+ session)
                .success(true)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public BaseResponse getAllByDate(String date) {
        List<GetSensorDataResponse> responses=repository.findByDate(date)
                .stream()
                .map(this::toGetSensorDataResponse)
                .toList();

        return BaseResponse.builder()
                .data(responses)
                .message("The sensor data has been found with the date"+ date)
                .success(true)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public List<SensorData> getListBySession(String session) {
        return repository.findBySession(session);
    }

    @Override
    public List<SensorData> getListByDate(String date) {
        return repository.findByDate(date);
    }

    @Override
    public BaseResponse getAll() {
        List<GetSensorDataResponse> responses = repository
                .findAll()
                .stream()
                .map(this::toGetSensorDataResponse).collect(Collectors.toList());
        return BaseResponse.builder()
                .data(responses)
                .message("The all sensor data hava been found")
                .success(true)
                .httpStatus(HttpStatus.FOUND).build();
    }

    @Override
    public List<SensorData> getAllData() {
        return repository.findAll();
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Double> getAllGas() {
        return repository.findAll().stream().map(this::getGas).toList();
    }

    @Override
    public List<Double> getAllQualityAir() {
        return repository.findAll().stream().map(this::getQualityAir).toList();
    }

    @Override
    public List<Double> getAllLight() {
        return repository.findAll().stream().map(this::getLight).toList();
    }

    @Override
    public List<Double> getAllTemperature() {
        return repository.findAll().stream().map(this::getTemperature).toList();
    }

    @Override
    public List<Double> getAllHumidity() {
        return repository.findAll().stream().map(this::getHumidity).toList();
    }


    private Double getGas(SensorData sensorData){
        return sensorData.getAir().getGasPmm();
    }

    private Double getQualityAir(SensorData sensorData){
        return sensorData.getAir().getCoPmm();
    }

    private Double getHumidity(SensorData sensorData){
        return sensorData.getHumTemp().getHumidity();
    }

    private Double getTemperature(SensorData sensorData){
        return sensorData.getHumTemp().getTemperature();
    }

    private Double getLight(SensorData sensorData){
        return sensorData.getLight().getRaw();
    }


    private SensorData update(SensorData sensorData, UpdateSensorDataRequest update){
        sensorData.setAir(update.getAir());
        sensorData.setLight(update.getLight());
        sensorData.setHumTemp(update.getHumTemp());
        return sensorData;
    }

    private GetSensorDataResponse toGetSensorDataResponse(SensorData sensorData){

        GetSensorDataResponse response= new GetSensorDataResponse();
        response.setId(sensorData.getId());
        response.setAir(sensorData.getAir());
        response.setHumTemp(sensorData.getHumTemp());
        response.setLight(sensorData.getLight());
        response.setDate(sensorData.getDate());
        response.setDeviceId(sensorData.getDeviceId());
        response.setSession(sensorData.getSession());
        return response;
    }


    private SensorData toSensorData(CreateSensorDataRequest request){
        SensorData sensorData = new SensorData();
        sensorData.setId(sequenceGeneratorService.generateSequence(SensorData.SEQUENCE_NAME));
        sensorData.setAir(request.getAir());
        sensorData.setLight(request.getLight());
        sensorData.setHumTemp(request.getHumTemp());
        sensorData.setDate(request.getDate());
        sensorData.setDeviceId(request.getDeviceId());
        sensorData.setSession(request.getSession());
        return sensorData;
    }

    private String toJson(GetSensorDataResponse response){
        Gson gson = new Gson();
        return gson.toJson(response);
    }

    private String toJson(List<GetSensorDataResponse> response){
        Gson gson = new Gson();
        return gson.toJson(response);
    }
}
