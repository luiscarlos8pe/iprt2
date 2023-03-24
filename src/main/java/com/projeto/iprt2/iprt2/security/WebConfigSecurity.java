package com.projeto.iprt2.iprt2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter{
	
	
	@Autowired
	private ImplementacaoUserDetailsService implementacaoUserDetailsService;

	@Override // Configura as solicitações de acesso por Http
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
		.disable() // Desativa as configurações padrão de memória.
		.authorizeRequests() // Pertimi restringir acessos
		.antMatchers(HttpMethod.GET, "/").permitAll() // Qualquer usuário acessa a pagina inicial
		.antMatchers(HttpMethod.GET, "/cadastropessoa").hasAnyRole("ADMIN")
		.anyRequest().authenticated()
		.and().formLogin().permitAll()// permite qualquer usuário
        .loginPage("/login")
        .defaultSuccessUrl("/cadastropessoa")
        .failureUrl("/login?error=true")
        .and()
        .logout().logoutSuccessUrl("/login")
		// Mapeia URL de Logout e invalida usuário autenticado
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
		
	}
	
	
	@Override // Cria autenticação do usuário com banco de dados ou em memória
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		
		auth.userDetailsService(implementacaoUserDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
	
		// auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance()) PADRAO PARA NAO CODIFICAR A SENHA 
		/*auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
		.withUser("alex")
		.password("$2a$10$fBBO5ie7/oD1UTD28/2GUuSolWiY2hdKRbDf1bi5kRdEYWVKIno8e")
		.roles("ADMIN");*/
	}
	
	@Override // Ignora URL especificas
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/materialize/**");
	}
	
	 public void addResourceHandlers(ResourceHandlerRegistry registry) {
	 registry.addResourceHandler("/webjars/**", "/resources/**", "/static/**", "/img/**", "/css/**", "/js/**",
					"classpath:/static/", "classpath:/resources/")
			.addResourceLocations("/webjars/", "/resources/",
							"classpath:/static/**", "classpath:/static/img/**", "classpath:/static/",
							"classpath:/resources/", "classpath:/static/css/", "classpath:/static/js/", "/resources/**",
							"/WEB-INF/classes/static/**");
			
	  }

}