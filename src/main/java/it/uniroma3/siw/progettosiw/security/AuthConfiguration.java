package it.uniroma3.siw.progettosiw.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class AuthConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment environment;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(buildDatasource())
				.authoritiesByUsernameQuery("SELECT nome, role FROM funzionario WHERE nome=?")
				.usersByUsernameQuery("SELECT nome, password, 1 as enabled FROM funzionario WHERE nome=?");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/", "/index").permitAll()
				.antMatchers(HttpMethod.GET, "/admin").hasAnyAuthority("ADMIN").and().formLogin().defaultSuccessUrl("/")
				.and().logout().logoutUrl("/logout").logoutSuccessUrl("/");
		// anyRequest().authenticated()
	}

	@Bean
	public DataSource buildDatasource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
		dataSource.setUrl(environment.getProperty("spring.datasource.url"));
		dataSource.setUsername(environment.getProperty("spring.datasource.username"));
		dataSource.setPassword(environment.getProperty("spring.datasource.password"));
		return dataSource;
	}

//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}

	@SuppressWarnings("deprecation")
	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}
}
