package com.streamworks.api_service_1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private Long id;
    private String name;
    private String username;
    private String email;
    private Map<String, Object> address;
    private String phone;
    private String website;
    private Map<String, Object> company;
}
