package com.newsletter.infra;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.newsletter.repository.feedback",
        entityManagerFactoryRef = "feedbackEntityManagerFactory",
        transactionManagerRef = "feedbackTransactionManager"
)
public class FeedbackDataSourceConfig {
    @Bean(name = "feedbackDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.feedback")
    public DataSource feedbackDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "feedbackEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean feedbackEntityManagerFactory(
            @Qualifier("feedbackDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.newsletter.model.feedback");
        Properties jpaProps = new Properties();

        jpaProps.put("hibernate.hbm2ddl.auto", "update");
        jpaProps.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

        em.setJpaProperties(jpaProps);
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return em;
    }

    @Bean(name = "feedbackTransactionManager")
    public PlatformTransactionManager feedbackTransactionManager(
            @Qualifier("feedbackEntityManagerFactory") LocalContainerEntityManagerFactoryBean factory) {
        return new JpaTransactionManager(Objects.requireNonNull(factory.getObject()));
    }
}
