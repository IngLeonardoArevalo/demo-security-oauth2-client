package demo.security.oauth2.client.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@Order(1)
public class SecurityConfiguration extends  WebSecurityConfigurerAdapter  {
	   @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.inMemoryAuthentication()
	            .withUser("sdk")
	            .password(passwordEncoder().encode("123"))
	            .roles("USER");
	    }

	    @Override
	protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/api/sdk/", "/**").permitAll();
			
	}

		@Bean
	    public BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
}
