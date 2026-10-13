package dev.pronovich.tennis.scoreboard.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.hibernate.LocalSessionFactoryBean;
import org.springframework.context.annotation.DependsOn;
import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@PropertySource("classpath:application.properties")
public class DatabaseConfig {

    @Value("${spring.datasource.driver}")
    private String driverClassName;
    @Value("${spring.datasource.url}")
    private String jdbcUrl;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);

        return new HikariDataSource(hikariConfig);
    }

    @Bean
    @DependsOn("dataSourceInitializer")
    public LocalSessionFactoryBean sessionFactoryBean(DataSource dataSource){
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);

        sessionFactory.setPackagesToScan("dev/pronovich/tennis/scoreboard/model");

        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
        hibernateProperties.setProperty("hibernate.show_sql", "true");

        sessionFactory.setHibernateProperties(hibernateProperties);
        return sessionFactory;
    }
}
