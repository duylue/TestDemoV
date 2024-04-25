package com.example.demoV.base;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class SuccessResponse {
    private int status;
    private String message;
    private Object data;
}
