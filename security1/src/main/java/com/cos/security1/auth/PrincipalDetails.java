package com.cos.security1.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.security1.model.User;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킴
// 이때 로그인 진행이 완료가되면 session을 만들어서 넣어줌(Security ContextHolder)
// 시큐리티가 가지고있는 세션에 들어갈수있는 오브젝트타입이 정해져있음
// Authentication 타입의 객체여야만 함
// Authentication 안에 User정보가 있어야 됨.
// User오브젝트타입도 정해져있음 => UserDetails타입의 객체여야만 함

// Security Session => Authentication => UserDetails(PrincipalDetails)만 담을수 있음


// 유저정보(UserDetails)를 만드는구간
// implements UserDetails를 하면 PrincopalDetails를 Authentication에 담을수 있게됨
// 어노테이션 안적는 이유 : 나중에 New로 강제로 띄울거
public class PrincipalDetails implements UserDetails{
	
	private User user; //콤포지션(우리의 유저정보는 User객체가 갖고있으니까)
	
	public PrincipalDetails(User user) { //생성자를 만들어줌
		this.user = user;
	}

	
	//해당 유저의 권한을 리턴하는 곳
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// user.getRole()얘가 권한인데 얘는 String타입이라 얘를 적을순 없음
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				return user.getRole();
			}
		});
		return collect;
		//솔직히 여기는 잘 모르겠네;;
	}

	//비밀번호
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	//아이디
	@Override
	public String getUsername() {
		return user.getUsername();
	}

	//계정이 만료되지 않았는가?
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	//계정이 안잠겨 있는가?
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	//비밀번호가 오랫동안 병경되지 않은게 아닌가?
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	//계정이 활성화 되어있는가?
	@Override
	public boolean isEnabled() {		
		return true;
	}

	
}
