package com.ehealth.doctors.gateway.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

/**
 * Created by vilyam on 21.02.17.
 */
@Service
public class SmsGateway {
    private static final Logger log = LoggerFactory.getLogger(SmsGateway.class);

    private JdbcTemplate jdbcTemplate;

    private String sign;
    private String tableName;
    private String smsSql;

    @Autowired
    public SmsGateway(@Qualifier("smsDataSource") DataSource smsDataSource, Environment env) {
        jdbcTemplate = new JdbcTemplate(smsDataSource);
        jdbcTemplate.execute("select 1");
        tableName = env.getProperty("db.sms.user");
        sign = env.getProperty("gateway.sms.sign");
        smsSql = "insert into " + tableName + " (number, sign, message, send_time) VALUES (?, '" + sign + "', ?, current_timestamp);";
    }

    public void sendSms(String number, String message) {
        jdbcTemplate.update(smsSql, number, message);

        log.info("Message [{} -> '{}'] sended", number, message);
    }
}
