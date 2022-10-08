package com.oms.mapper;

import com.oms.models.POMasterEntity;
import com.oms.models.ProductOrderManagerEntity;
import com.oms.pojo.PODetails;
import com.oms.pojo.ProductOrderDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PODetailsMapper {

//    @Mapping(target = "mfgItemNumber",source ="")
            PODetails poDetailsPOJOMapper(POMasterEntity poMasterEntity,String customerName);
    @Mapping(target = "mfgItemNumber",source = "orderManagerEntity.mfgItemNumber.mfgItemNumber")
    ProductOrderDetails orderDetailsPOJOMapper(ProductOrderManagerEntity orderManagerEntity);
}
