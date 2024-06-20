package com.example.booking_service.mapper;

import com.example.booking_service.entity.User;
import com.example.booking_service.web.model.request.UserRequest;
import com.example.booking_service.web.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User requestToUser(UserRequest request);

    UserResponse userToUserResponse(User user);

}
