package com.oms.dto.requests;

import lombok.Data;

@Data
public class Manufacturer {

    private String manufacturer;

    public Manufacturer(String manufacturer)
    {
        this.manufacturer=manufacturer;
    }
}
