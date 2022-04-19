package com.mzr.sm.security

import com.mzr.sm.security.service.ServiceAuthenticationProvider
import com.mzr.sm.security.service.ServiceTokenAuthenticationFilter
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig(
    private val serviceAuthenticationProvider: ServiceAuthenticationProvider,
) : WebSecurityConfigurerAdapter() {

    override fun configure(builder: AuthenticationManagerBuilder) {
        builder.authenticationProvider(serviceAuthenticationProvider)
    }

    override fun configure(http: HttpSecurity) {
        http
            .addFilterAfter(
                ServiceTokenAuthenticationFilter(serviceAuthenticationProvider),
                BasicAuthenticationFilter::class.java)
            .cors()
            .and()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests{ configurer ->
                configurer
                    .anyRequest()
                    .authenticated()
            }
            .oauth2ResourceServer { obj: OAuth2ResourceServerConfigurer<HttpSecurity?> -> obj.jwt() }
    }
}