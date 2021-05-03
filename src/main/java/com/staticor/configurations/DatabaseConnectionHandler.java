package com.staticor.configurations;

//import oracle.jdbc.pool.OracleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Properties;
/*
@Configuration
public class DatabaseConnectionHandler {

    @Value("${spring.datasource.url}")
    private String url;

    @Bean
    public DataSource dataSource() {
        OracleDataSource dataSource = null;
        try {
            dataSource = new OracleDataSource();
            Properties props = new Properties();
            String oracle_net_wallet_location = "/Users/nadeengamage/Projects/staticor/staticor-web-services/src/main/resources/databases/dev";
            System.getProperty("oracle.net.wallet_location");
            props.put("oracle.net.wallet_location", "(source=(method=file)(method_data=(directory="+oracle_net_wallet_location+")))");
            dataSource.setConnectionProperties(props);
            dataSource.setURL(url);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
        jdbcTemplate.setFetchSize(20000);
        return jdbcTemplate;
    }
}*/
