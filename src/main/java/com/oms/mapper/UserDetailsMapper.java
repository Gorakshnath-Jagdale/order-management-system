package com.oms.mapper;

import com.oms.models.UserDetailsEntity;
import com.oms.pojo.UserDetailsPojo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Date;

@Mapper(componentModel = "spring", imports = Date.class)
public interface UserDetailsMapper {

    UserDetailsPojo getUserDetailsPojo(UserDetailsEntity user);

    //@Mapping(target = "createdBy", source = "createdBy")
    @Mapping(target = "createdDate", expression = "java(new Date())")
    UserDetailsEntity getUserDetailsEntity(UserDetailsPojo userDetails);


    //@Mapping(target = "modifiedBy", source = "modifiedBy")
    @Mapping(target = "modifiedDate", expression = "java(new Date())")
    void updateUserDetailsEntity(@MappingTarget UserDetailsEntity userDetails, UserDetailsPojo user);

}
