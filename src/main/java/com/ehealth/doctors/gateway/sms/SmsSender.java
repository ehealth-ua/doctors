package com.ehealth.doctors.gateway.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import ua.turbosms.Service;
import ua.turbosms.ServiceSoap;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.xml.namespace.QName;
import java.net.URL;

/**
 * Created by vilyam on 21.02.17.
 */
@Component
public class SmsSender {
    @Autowired
    private Environment env;

    private static final QName SERVICE_NAME = new QName("http://turbosms.in.ua/api/Turbo", "Service");
    private Service service;
    private ServiceSoap port;

    @PostConstruct
    public void init() {
        URL wsdlURL = getClass().getClassLoader().getResource("gateway/turbosms.wsdl");
        service = new Service(wsdlURL, SERVICE_NAME);
        port = service.getServiceSoap();

        System.out.println("Invoking auth...");
        String _auth_login = env.getProperty("db.url");
        String _auth_password = env.getProperty("db.url");
        String _auth__return = port.auth(_auth_login, _auth_password);
        System.out.println("auth.result=" + _auth__return);
    }
}
