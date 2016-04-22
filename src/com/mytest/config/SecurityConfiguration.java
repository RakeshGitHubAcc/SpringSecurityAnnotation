package com.mytest.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	/**
	 * successfully executed
	 */
	/*
	 * @Autowired 
	 * CustomSuccessHandler customSuccessHandler;
	 * 
	 * @Autowired public void configureGlobal(AuthenticationManagerBuilder auth)
	 * throws Exception {
	 * auth.inMemoryAuthentication().withUser("user").password("password")
	 * .roles("USER");
	 * auth.inMemoryAuthentication().withUser("admin").password("root123")
	 * .roles("ADMIN");
	 * auth.inMemoryAuthentication().withUser("dba").password("root123")
	 * .roles("ADMIN", "DBA");// dba have two roles. }
	 */
	@Autowired
    DataSource dataSource;
	
	@Autowired
	@Qualifier("customUserDetailsService")
	UserDetailsService userDetailsService;
	
	@Autowired 
	CustomSuccessHandler customSuccessHandler;
	
	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		/**
		 * successfully executed
		 */
		
		/*
		 * http.authorizeRequests() .antMatchers("/", "/home").permitAll()
		 * .antMatchers("/admin/**").access("hasRole('ADMIN')")
		 * .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
		 * .and().formLogin()
		 * .and().exceptionHandling().accessDeniedPage("/Access_Denied");
		 */

		/**
		 * successfully executed custom login example
		 */
		
		/*
		 * http.authorizeRequests() .antMatchers("/", "/home").permitAll()
		 * .antMatchers("/admin/**").access("hasRole('ADMIN')")
		 * .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
		 * .and().formLogin().loginPage("/login")
		 * .usernameParameter("ssoId").passwordParameter("password")
		 * .and().csrf()
		 * .and().exceptionHandling().accessDeniedPage("/Access_Denied");
		 */

		/**
		 * successfully executed custom handler for redirecting depending upon
		 * user role(after login)
		 */
		
		/*
		 * http.authorizeRequests() .antMatchers("/","/home").access("hasRole('USER')")
		 * .antMatchers("/admin/**").access("hasRole('ADMIN')")
		 * .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
		 * .and().formLogin().loginPage("/login").successHandler(customSuccessHandler).usernameParameter("ssoId").passwordParameter("password")
		 * .and().csrf()
		 * .and().exceptionHandling().accessDeniedPage("/Access_Denied");
		 */
		
		/**
		 * successfully executed with hibernate with auth.userDetailsService(userDetailsService);
		 * 
		 */
		
		/*http.authorizeRequests()
        .antMatchers("/", "/home").permitAll()
        .antMatchers("/admin/**").access("hasRole('ADMIN')")
        .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
        .and().formLogin().loginPage("/login")
        .usernameParameter("ssoId").passwordParameter("password")
        .and().csrf()
        .and().exceptionHandling().accessDeniedPage("/Access_Denied");*/
		
		/**
		 * successfully executed with hibernate with encrypted password
		 * 
		 */
		
		/*http.authorizeRequests()
        .antMatchers("/", "/home").access("hasRole('USER')")
        .antMatchers("/admin/**").access("hasRole('ADMIN')")
        .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
        .and().formLogin().loginPage("/login").successHandler(customSuccessHandler)
        .usernameParameter("username").passwordParameter("password")
        .and().csrf()
        .and().exceptionHandling().accessDeniedPage("/Access_Denied");*/
		
		http.authorizeRequests()
        .antMatchers("/", "/home","/list").access("hasRole('USER') or hasRole('ADMIN') or hasRole('DBA')")
        .antMatchers("/admin/**").access("hasRole('ADMIN')")
        .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
        .and().formLogin().loginPage("/login").successHandler(customSuccessHandler)
        .usernameParameter("username").passwordParameter("password")
        .and().rememberMe().rememberMeParameter("remember-me").tokenRepository(persistentTokenRepository()).tokenValiditySeconds(86400)
        .and().csrf()
        .and().exceptionHandling().accessDeniedPage("/Access_Denied");
	}
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
		tokenRepositoryImpl.setDataSource(dataSource);
		return tokenRepositoryImpl;
	}
	
}
