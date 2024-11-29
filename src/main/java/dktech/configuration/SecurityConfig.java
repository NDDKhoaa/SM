package dktech.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import dktech.security.SanctionVoter;
import dktech.services.impl.AccountDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private AccountDetailsServiceImpl accountDetailsService;

	@Autowired
	public void setAccountDetailsService(AccountDetailsServiceImpl accountDetailsService) {
		this.accountDetailsService = accountDetailsService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		SanctionVoter sanctionVoter = new SanctionVoter();

		http.authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers("/public/**").permitAll()
				.requestMatchers("/store/**").access((auth, context) -> sanctionVoter.check(auth, context.getRequest()))
				.requestMatchers("/department/**")
				.access((auth, context) -> sanctionVoter.check(auth, context.getRequest()))
				.requestMatchers("/position/**")
				.access((auth, context) -> sanctionVoter.check(auth, context.getRequest()))
				.requestMatchers("/branch/**")
				.access((auth, context) -> sanctionVoter.check(auth, context.getRequest()))
				.requestMatchers("/employee/**")
				.access((auth, context) -> sanctionVoter.check(auth, context.getRequest()))
				.requestMatchers("/customer/**")
				.access((auth, context) -> sanctionVoter.check(auth, context.getRequest()))
				.requestMatchers("/product/**")
				.access((auth, context) -> sanctionVoter.check(auth, context.getRequest()))
				.requestMatchers("/category/**")
				.access((auth, context) -> sanctionVoter.check(auth, context.getRequest()))
				.requestMatchers("/storage/**")
				.access((auth, context) -> sanctionVoter.check(auth, context.getRequest()))
				.requestMatchers("/account/**")
				.access((auth, context) -> sanctionVoter.check(auth, context.getRequest()))
				.requestMatchers("/authorize/**")
				.access((auth, context) -> sanctionVoter.check(auth, context.getRequest()))
				.requestMatchers("/authorizegroup/**")
				.access((auth, context) -> sanctionVoter.check(auth, context.getRequest()))
				.requestMatchers("/sanction/**")
				.access((auth, context) -> sanctionVoter.check(auth, context.getRequest())).requestMatchers("/bill/**")
				.access((auth, context) -> sanctionVoter.check(auth, context.getRequest())).anyRequest()
				.authenticated()).formLogin(formLogin -> formLogin.defaultSuccessUrl("/", true))
				.logout(logout -> logout.permitAll());

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth, @Lazy PasswordEncoder passwordEncoder)
			throws Exception {
		auth.userDetailsService(accountDetailsService).passwordEncoder(passwordEncoder);
	}
}
