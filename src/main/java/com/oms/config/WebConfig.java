package com.oms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebConfig  extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
              //  .antMatchers("*/api/user/**").authenticated()
              .antMatchers("/api/**").authenticated() //
              .antMatchers("/user/**").permitAll()
                .and().cors().and().httpBasic() //
;

//                .antMatchers("/login").permitAll()
//                .antMatchers(HttpMethod.POST,"*/api/user/*").permitAll()
              //  .antMatchers(HttpMethod.DELETE,"*/api/user/removeuser/*").permitAll()
//                .antMatchers(HttpMethod.GET,"*/api/user/*").permitAll()
//                .antMatchers(HttpMethod.GET,"/exploreCourse").permitAll()
                ;
    }
}