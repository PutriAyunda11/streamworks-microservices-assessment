package com.streamworks.api_service_1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Todo implements Serializable {
    private Long userId;
    private Long id;
    private String title;
    private Boolean completed;
}
