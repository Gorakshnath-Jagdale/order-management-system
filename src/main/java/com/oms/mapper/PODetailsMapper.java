package com.oms.mapper;

import com.oms.models.POMasterEntity;
import com.oms.models.ProductOrderManagerEntity;
import com.oms.pojo.PODetails;
import com.oms.pojo.ProductOrderDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.text.SimpleDateFormat;
import java.util.List;

@Mapper(componentModel = "spring",imports = {SimpleDateFormat.class})
public interface PODetailsMapper {

   @Mapping(target = "poDate",expression = "java(new SimpleDateFormat(\"dd/MM/yyyy\").format(poMasterEntity.getPoDate()))")
            PODetails poDetailsPOJOMapper(POMasterEntity poMasterEntity,String customerName);
    @Mapping(target = "mfgItemNumber",source = "orderManagerEntity.mfgItemNumber.mfgItemNumber")
    ProductOrderDetails orderDetailsPOJOMapper(ProductOrderManagerEntity orderManagerEntity);
}
