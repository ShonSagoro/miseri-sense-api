package com.miseri.miserisense.services.Interface;

import com.miseri.miserisense.controllers.dtos.request.CreateDeviceRequest;
import com.miseri.miserisense.controllers.dtos.response.BaseResponse;

public interface IDeviceService {

    BaseResponse get(Long id);

    BaseResponse create(CreateDeviceRequest request);

    BaseResponse getAll();

    void delete(long id);
}
