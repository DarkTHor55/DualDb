package com.dualdb.Configuration.Postgres;


import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.dualdb.Repository.Postgres",
        entityManagerFactoryRef = "blogEntityManagerFactory",
        transactionManagerRef = "blogTransactionManager"
)
@EntityScan(basePackages = "com.dualdb.Entity.Postgres")
public class PostgresDatabaseConfig {

    @Bean(name = "blogdb")
    @ConfigurationProperties(prefix = "spring.datasource.blogdb")
    public DataSource blogDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "blogEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean blogEntityManagerFactory(
            @Qualifier("blogdb") DataSource dataSource,
            Environment env) {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.dualdb.Entity.Postgres");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto", "update"));
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect"));
        properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql", "true"));

        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean(name = "blogTransactionManager")
    public PlatformTransactionManager blogTransactionManager(
            @Qualifier("blogEntityManagerFactory") LocalContainerEntityManagerFactoryBean factoryBean) {
        return new JpaTransactionManager(factoryBean.getObject());
    }
}
