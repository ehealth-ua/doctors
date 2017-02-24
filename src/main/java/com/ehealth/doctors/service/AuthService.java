package com.ehealth.doctors.service;

import com.ehealth.doctors.gateway.sms.SmsGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by vilyam on 21.02.17.
 */
@Service
public class AuthService {
    @Autowired
    private SmsGateway smsGateway;
}
