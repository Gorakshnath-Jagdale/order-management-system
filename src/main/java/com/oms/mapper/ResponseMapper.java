package com.oms.mapper;

import com.oms.models.CustomerDetailsEntity;
import com.oms.pojo.CustomerDetailsPojo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResponseMapper {

     CustomerDetailsPojo customerDetailsPojoMapper(CustomerDetailsEntity customerDetails);
    CustomerDetailsEntity customerDetailsEntityMapper(CustomerDetailsPojo customerDetails);
}
