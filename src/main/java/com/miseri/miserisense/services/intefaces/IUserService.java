package com.miseri.miserisense.services.intefaces;

import com.miseri.miserisense.controllers.dtos.request.CreateUserRequest;
import com.miseri.miserisense.controllers.dtos.request.UpdateUserRequest;
import com.miseri.miserisense.controllers.dtos.response.BaseResponse;
import com.miseri.miserisense.models.User;

public interface IUserService {
    BaseResponse get(String email);

    BaseResponse create(CreateUserRequest request);


    BaseResponse update(UpdateUserRequest request, Long idUser);

    BaseResponse getAll();

    void delete(long id);

    User getUserByEmail(String email);
}
