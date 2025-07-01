
package com.ejemplo.SCruzProgramacionNCapasMaven.Configuration;

import java.sql.DriverManager;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataSourceConfig {
    
    @Bean
    public DataSource dataSource(){
        
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        
        dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:orcl");
        dataSource.setUsername("SCruzProgramacionNCapas");
        dataSource.setPassword("password1");
        
//        return dataSource(); --- Esto era lo que tenías  
        return dataSource;
    }
    
    @Bean
    public JdbcTemplate jdbcTemplate (DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
    
}
