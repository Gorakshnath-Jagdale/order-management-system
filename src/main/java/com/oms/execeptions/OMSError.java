package com.oms.execeptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class OMSError {
    private String errorCode;
    private String errorMessage;
}

