package com.oms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestStructure<T> {

    private T request;
    private Requester requester;
}
