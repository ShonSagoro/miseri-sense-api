package com.miseri.miserisense.services;

import com.miseri.miserisense.controllers.advice.exceptions.NotFoundException;
import com.miseri.miserisense.controllers.dtos.request.CreateUserRequest;
import com.miseri.miserisense.controllers.dtos.request.UpdateUserRequest;
import com.miseri.miserisense.controllers.dtos.response.BaseResponse;
import com.miseri.miserisense.controllers.dtos.response.GetUserResponse;
import com.miseri.miserisense.models.User;
import com.miseri.miserisense.repositories.IUserRepository;
import com.miseri.miserisense.services.intefaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SequenceGeneratorServiceImpl sequenceGeneratorService;


    @Override
    public BaseResponse get(String email) {
        User user = repository.findByEmail(email)
                .orElseThrow(NotFoundException::new);

        GetUserResponse response=toGetUserResponse(user);
        return BaseResponse.builder()
                .data(response)
                .message("The user has been found with the email:" + response.getEmail())
                .success(true)
                .httpStatus(HttpStatus.FOUND).build();
    }

    @Override
    public BaseResponse create(CreateUserRequest request) {
        Optional<User> possibleCopy = repository.findByEmail(request.getEmail());

        if(possibleCopy.isPresent()){
            throw new RuntimeException("the user exist"); // (RegisterException)
        }
        User user = repository.save(toUser(request));
        GetUserResponse response=toGetUserResponse(user);
        return BaseResponse.builder()
                .data(response)
                .message("The user has been created with id: "+ response.getId())
                .success(true)
                .httpStatus(HttpStatus.CREATED).build();
    }


    @Override
    public BaseResponse update(UpdateUserRequest request, Long idUser) {
        User user = repository.findById(idUser).orElseThrow(RuntimeException::new);
        User userSave = repository.save(update(user, request));
        GetUserResponse response=toGetUserResponse(userSave);
        return BaseResponse.builder()
                .data(response)
                .message("The user has been updated with id: "+ response.getId())
                .success(true)
                .httpStatus(HttpStatus.ACCEPTED).build();
    }

    @Override
    public BaseResponse getAll() {
        List<GetUserResponse> responses = repository
                .findAll()
                .stream()
                .map(this::toGetUserResponse).collect(Collectors.toList());
        return BaseResponse.builder()
                .data(responses)
                .message("The all users have been found")
                .success(true)
                .httpStatus(HttpStatus.FOUND).build();
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(NotFoundException::new);
    }


    private User update(User user, UpdateUserRequest update){
        user.setEmail(update.getEmail());
        user.setName(update.getName());
        return user;
    }

    private GetUserResponse toGetUserResponse(User user){

        GetUserResponse response= new GetUserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setName(user.getName());

        return response;
    }


    private User toUser(CreateUserRequest request){
        User user = new User();
        user.setId(sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME));
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return user;
    }
}
