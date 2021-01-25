package com.cos.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Controller //View를 리턴 하겠다
public class IndexController {
	
	@Autowired //레파지토리 등록함
	private UserRepository userRepository;
	
	@Autowired // 시큐리티에서 Bean으로 등록함
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping({"","/"})
	public String index() {
		// 머스테치 기본폴더 : src/main/resources/
		// 뷰리졸버 설정 : templates(prefix), /mustache(sufix)
		// 머스테치 의존성 추가했을시 뷰리졸버설정 생략 가능
		return "index"; //src/main/resources/templates/index.mustache
	}
	
	@GetMapping("/user")
	public @ResponseBody String user() {
		return "user";
	}
	
	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "admin";
	}
	
	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "manager";
	}
	
	// @GetMapping("/login")만 해놓으면 스프링 시큐리티가 먼저 낚아채버림
	// -SecurityConfig 설정후에 시큐리티가 안낚아챔
	@GetMapping("/loginForm")
	public String loginForm() {
		return "loginForm";
	}
	
	@PostMapping("/join") //얘가 Proc
	public String join(User user) {
		System.out.println(user);
		user.setRole("ROLE_USER"); //접근권한만 설정해주고 나머지는 model에서 설정해뒀기때문에 알아서 다 들어감
		//암호화
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		
		userRepository.save(user); // 회원가입, but 비밀번호: 1234 => 시큐리티로 로그인 할수 없음. 이유는 패스워드가 암호화가 안되어있기때문 (그래서 위에서 암호화 해줌)
					//save가 값을 넣을때 쓰는건가봄
		return "redirect:loginForm";
	}
	
	@GetMapping("/joinForm")
	public String joinForm() {
		return "joinForm";
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/info")
	public @ResponseBody String info(){
		return "개인정보";
	}
	
	@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") //둘중 하나의 권한이 있을때
	@GetMapping("/data")
	public @ResponseBody String data(){
		return "데이터정보";
	}
}
