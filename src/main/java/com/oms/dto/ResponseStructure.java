package com.oms.dto;

import com.oms.execeptions.OMSError;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseStructure<T> {

    private T result;
    private OMSError error;
    private boolean flag;
}
