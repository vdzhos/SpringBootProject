package com.sbproject.schedule.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sbproject.schedule.repositories.UserRepository;
import com.sbproject.schedule.services.implementations.CustomUserDetailsService;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsService userDService;

	//@Bean
	//public PasswordEncoder getPasswordEncoder() {
	//	return new BCryptPasswordEncoder();
	//}

	//temporary solution
	private PasswordEncoder getPasswordEncoder() {
		return new PasswordEncoder() {
			@Override
			public String encode(CharSequence charSequence) {
				return charSequence.toString();
			}

			@Override
			public boolean matches(CharSequence charSequence, String s) {
				return s.contentEquals(charSequence);
			}
		};
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.userDService).passwordEncoder(getPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.logout()
				.and()
				.authorizeRequests()
				.antMatchers("/login/**", "/h2-console/**").permitAll()
				.anyRequest().authenticated()
//        .and().csrf().ignoringAntMatchers("/h2-console/**")
				.and().headers().frameOptions().sameOrigin()
				.and()
				.formLogin()
				.loginPage("/login")
				.permitAll();

	}
}
