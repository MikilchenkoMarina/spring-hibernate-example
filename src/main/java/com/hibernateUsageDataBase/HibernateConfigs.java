package com.hibernateUsageDataBase;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by mmikilchenko on 19.03.2017.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan

public class HibernateConfigs {
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/inspominder");
        ds.setUsername("root");
        ds.setPassword("admin");
        Properties props = new Properties();
        props.setProperty("defaultAutoCommit", "true");
        ds.setConnectionProperties(props);
        return ds;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
        sfb.setDataSource(dataSource());
        sfb.setPackagesToScan(new String[]{"com.entity","com.hibernateUsageDataBase.dao.impl","com.hibernateUsageDataBase.dao"});
        Properties props = new Properties();
        props.setProperty("dialect", "org.hibernate.dialect.H2Dialect");
        props.setProperty("connection.autocommit", "true");
        props.setProperty("javax.persistence.validation.mode", "AUTO");
        sfb.setHibernateProperties(props);
        return sfb;
    }

    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }


}
