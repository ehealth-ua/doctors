package com.ehealth.doctors.model.rest;

import java.util.List;

public class BaseResponseMessage<T> {
    public long timestamp = System.currentTimeMillis();
    public int status;
    public String path;
    public T data;
    public List<ErrorMessage> error;
}
