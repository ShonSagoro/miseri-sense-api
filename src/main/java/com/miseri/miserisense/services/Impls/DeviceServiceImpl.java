package com.miseri.miserisense.services;

import com.miseri.miserisense.controllers.advice.exceptions.NotFoundException;
import com.miseri.miserisense.controllers.dtos.request.CreateDeviceRequest;
import com.miseri.miserisense.controllers.dtos.response.BaseResponse;
import com.miseri.miserisense.controllers.dtos.response.GetDeviceResponse;
import com.miseri.miserisense.controllers.dtos.response.GetUserResponse;
import com.miseri.miserisense.models.Device;
import com.miseri.miserisense.models.User;
import com.miseri.miserisense.repositories.IDeviceRepository;
import com.miseri.miserisense.services.Interface.IDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceServiceImpl implements IDeviceService {
    @Autowired
    private IDeviceRepository repository;

    @Autowired
    private SequenceGeneratorServiceImpl sequenceGeneratorService;


    @Override
    public BaseResponse get(Long id) {
        Device device = repository.findById(id)
                .orElseThrow(NotFoundException::new);
        GetDeviceResponse response=toGetDeviceResponse(device);
        return BaseResponse.builder()
                .data(response)
                .message("The device has been found with the id:" + response.getId())
                .success(true)
                .httpStatus(HttpStatus.FOUND).build();
    }

    @Override
    public BaseResponse create(CreateDeviceRequest request) {
        Device device = repository.save(toDevice(request));
        GetDeviceResponse response= toGetDeviceResponse(device);

        return BaseResponse.builder()
                .data(response)
                .message("The device has been created with id: "+ response.getId())
                .success(true)
                .httpStatus(HttpStatus.CREATED).build();
    }

    @Override
    public BaseResponse getAll() {
        List<GetDeviceResponse> responses = repository
                .findAll()
                .stream()
                .map(this::toGetDeviceResponse).collect(Collectors.toList());
        return BaseResponse.builder()
                .data(responses)
                .message("The all devices hava been found")
                .success(true)
                .httpStatus(HttpStatus.FOUND).build();
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

    private GetDeviceResponse toGetDeviceResponse(Device device){
        GetDeviceResponse response= new GetDeviceResponse();
        response.setId(device.getId());
        response.setUserId(device.getUserId());
        return  response;
    }


    private Device toDevice(CreateDeviceRequest request){
        Device device= new Device();
        device.setId(sequenceGeneratorService.generateSequence(device.SEQUENCE_NAME));
        device.setUserId(request.getUserId());
        return  device;
    }
}
