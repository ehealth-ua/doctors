package com.ehealth.doctors.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.ehealth.doctors.dao")
@PropertySource("classpath:config.properties")
public class DatabaseConfig {
    @Autowired
    private Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory()
            throws ClassNotFoundException, PropertyVetoException {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        emf.setPackagesToScan("com.ehealth.doctors.model");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        emf.setJpaVendorAdapter(vendorAdapter);
        emf.setJpaProperties(additionalProperties());
        return emf;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("db.default.driver"));
        dataSource.setUrl(env.getProperty("db.default.url"));
        dataSource.setPassword(env.getProperty("db.default.password"));
        dataSource.setUsername(env.getProperty("db.default.user"));
        return dataSource;
    }

    @Bean
    public DataSource smsDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("db.sms.driver"));
        dataSource.setUrl(env.getProperty("db.sms.url"));
        dataSource.setPassword(env.getProperty("db.sms.password"));
        dataSource.setUsername(env.getProperty("db.sms.user"));
        return dataSource;
    }

    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.enable_lazy_load_no_trans", "true");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("show_sql", "true");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        return properties;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }
}
