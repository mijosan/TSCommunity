package com.dodge.board.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{//WebSecurityConfigurerAdapter 클래스를 상속한 객체가 빈으로 등록되기만해도 로그인강제 X
	
	@Autowired
	private SecurityUserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity security) throws Exception {//웹 시큐리티와 관련된 다양한 설정을 추가
		security.userDetailsService(userDetailsService);
		
		security.authorizeRequests().antMatchers("/", "/system/login", "/board/getBoardList", "/board/getBoard").permitAll(); //모두허가
		security.authorizeRequests().antMatchers("/system/myInfo", "/board/insertBoard", "/board/updateBoard", 
		"/board/getLikeList", "/board/getMyBoardList", "/board/getMyCommentList").authenticated(); //인증된 사용자 허가
		security.headers().frameOptions().disable();
		
		security.csrf().disable();//크로스 사이트위조 요청에 대한 설정 비활성화
		security.formLogin().loginPage("/system/login").defaultSuccessUrl("/home", true).failureUrl("/system/login?result=fail"); //로그인화면 제공
		security.logout().logoutUrl("/system/logout").invalidateHttpSession(true).logoutSuccessUrl("/home"); //세션종료
		
		//세션 이 끝났을경우 이동 URL + 최대허용 중복세션 1 + 세션이 중복되면 이동할 페이지(나중에 로그인한 사람이 들어오고 기존의 클라이언트는 페이지이동)
		security.sessionManagement().invalidSessionUrl("/system/login").maximumSessions(1).expiredUrl("/system/login");
		
	}
	//패스워드 인코더 빈 등록
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
