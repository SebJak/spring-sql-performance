package com.langpath.configuration;

import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import net.sf.log4jdbc.Slf4jSpyLogDelegator;
import net.sf.log4jdbc.SpyLogDelegator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by Sebastian on 2016-03-14.
 */
@Configuration
@EnableTransactionManagement
public class PersistenceConf {

    @Autowired
    private Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(env.getRequiredProperty("sql.base.packagesToScan"));

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        //vendorAdapter.setGenerateDdl(true);
        em.setJpaVendorAdapter(vendorAdapter);

        em.setJpaProperties(additionalProperties());


        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    //@Bean
    public DataSource dataSourceSpied(){
        final String password = env.getRequiredProperty("sql.dataBase.password");
        final String user = env.getRequiredProperty("sql.dataBase.user");
        final String url = env.getProperty("sql.dataBase.url");
        final String driverClass = env.getRequiredProperty("sql.dataBase.driver");

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public Log4jdbcProxyDataSource dataSource() {
        Log4jdbcProxyDataSource proxyDataSource = new Log4jdbcProxyDataSource(dataSourceSpied());
        return proxyDataSource;
    }

    @Bean
    public Properties additionalProperties() {
        //final String hbm2ddl = env.getRequiredProperty("hibernate.hbm2ddl.auto");
        final String dialect = env.getRequiredProperty("hibernate.dialect");
        final String generateDdl = env.getRequiredProperty("spring.jpa.generate-ddl");
        final String showSql = env.getRequiredProperty("hibernate.show_sql");
        
        Properties properties = new Properties();
        //properties.setProperty("hibernate.hbm2ddl.auto", hbm2ddl); //Update for updating schema validate for validating.
        //properties.setProperty("spring.jpa.generate-ddl", generateDdl);
        properties.setProperty("hibernate.dialect", dialect);
        properties.setProperty("hibernate.show_sql", showSql);
        properties.setProperty("hibernate.jdbc.batch_size","50");
        properties.setProperty("hibernate.order_inserts","true");
        properties.setProperty("hibernate.order_updates","true");

        return properties;
    }


}
