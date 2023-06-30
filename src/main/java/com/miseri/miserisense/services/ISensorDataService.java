package com.miseri.miserisense.services.Interface;

import com.miseri.miserisense.controllers.dtos.request.CreateSensorDataRequest;
import com.miseri.miserisense.controllers.dtos.request.UpdateSensorDataRequest;
import com.miseri.miserisense.controllers.dtos.response.BaseResponse;

public interface ISensorDataService {
    BaseResponse get(Long id);

    BaseResponse create(CreateSensorDataRequest request);

    BaseResponse update(UpdateSensorDataRequest request, Long idUser);

    BaseResponse getAll();

    void delete(long id);
}
