package com.cos.security1.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

//Security Session => Authentication => UserDetails(PrincipalDetails)만 담을수 있음

//Authentication을 만드는 구간
//시큐리티 설정에서 loginProcessingUrl("/login");
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 Ioc되어 있는 loadUserByUsername 함수가 실행이됨
// 그냥 규칙임
@Service
public class PrincipalDetailsService implements UserDetailsService {
// /login이 발동되면 Spring은 Ioc 컨테이너에서 userdetaileService를 찾아서 loadUserByUsername을 호출함 이때 파라미터 username을 가져옴
	
	@Autowired
	private UserRepository userRepository;
		
	// Security Session( Authentication (UserDetails) ) )
	//여기서 return된 값은 Authentication여기에 들어가면서 위의 형태가 만들어지고 로그인이 완료가됨
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
										//form에서 name을 꼭 무조건 절대 username으로 해줘야함
										//만약에 바꿀거라면 시큐리티에서 .usernameParamter해서 바꿔줘야하는데 굳이?
										//그냥 username으로 쓰는걸로
		System.out.println("username: " + username);
		//username의 이름으로 유저가 있는지 확인을 함
		User userEntity = userRepository.findByUsername(username);
									//Repository는 기본적인 CRUD만 가지고 있기때문에 findByUsername를 만들어줘야함
		if(userEntity != null) {
			return new PrincipalDetails(userEntity);
		}

		return null;
	}

}
