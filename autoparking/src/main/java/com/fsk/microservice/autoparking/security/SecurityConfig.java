package com.fsk.microservice.autoparking.security;

/*@Configuration
@EnableWebSecurity*/
public class SecurityConfig /*extends WebSecurityConfigurerAdapter*/ {

	/*@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("fsk").password("{noop}14003352").roles("user");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 //http.cors().and().csrf().disable();
		//http.requestMatchers().antMatchers("/api**").and().cors().and().csrf().disable();
		http.authorizeRequests().anyRequest().authenticated().and().httpBasic().disable();
	}*/
}
