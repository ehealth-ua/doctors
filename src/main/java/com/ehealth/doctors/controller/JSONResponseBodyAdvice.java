package com.ehealth.doctors.controller;

import com.ehealth.doctors.model.rest.BaseResponseMessage;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Created by vilyam on 24.02.17.
 */
//@ControllerAdvice
public class JSONResponseBodyAdvice implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response) {

        BaseResponseMessage message = new BaseResponseMessage();
        message.data = body;
        message.path = request.getURI().getPath();

        return message;
    }
}
