package com.ecommerce.library.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class BaseException extends RuntimeException{

    private String code;

    private String message;
}