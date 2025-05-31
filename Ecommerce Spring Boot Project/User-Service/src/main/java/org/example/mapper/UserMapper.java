package org.example.mapper;

import org.example.dto.request.UpdateUserRequest;
import org.example.dto.response.UserDTO;
import org.example.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "userId", target = "userId")
    UserDTO toDTO(User user);

    @Mapping(source = "userId", target = "userId")
    User toUser(UserDTO userDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromRequest(UpdateUserRequest request, @MappingTarget User user);
}
