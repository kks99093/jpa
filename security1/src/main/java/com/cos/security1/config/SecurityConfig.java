package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration //메모리에 올림
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터체인에 등록이 됨
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured 어노테이션 활성화, 특정 메서드에 권한을 줄수있다(ex. @Secured("ROLE_ADMIN"))
public class SecurityConfig extends WebSecurityConfigurerAdapter{ //PreAuthorize PostAuthorize 어노테이션 활성화
	
	//시큐리티 암호화
	//Bean어노테이션을 적으면 해당 메서드의 리턴되는 오브젝트를 Ioc로 등록해준다.
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	
	//접근권한 설정
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
		.antMatchers("/user/**").authenticated() //인증만되면 들어갈수 있음
		.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
		.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
		.anyRequest().permitAll() //위의 주소 이외에는 전부 접근가능
		.and()
		.formLogin()//권한이 없는 페이지에 요청이 들어갈때 로그인 페이지로 보내기
		.loginPage("/loginForm")
		.loginProcessingUrl("/login") //(loginProc) login 주소가 호출이 되면 시큐리가 낚아채서 대신 로그인을 진행해 줌
		.defaultSuccessUrl("/");//로그인이 완료되면 "/"페이로 가게함
	}

}
