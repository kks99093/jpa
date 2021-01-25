package com.cos.security1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cos.security1.model.User;

//CRUD 함수를 JpaRepository가 들고 있음
// @Repository라는 어노테이션이 없어도 IoC가됨. 이유는 JpaRepository를 상속했기때문에 자동으로 Bean으로 등록됨
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
													//타입, 프라이머리키 타입
	
	//findBy규칙 -> Username문법
	// select * from user where username = 1? 이게 호출이됨(?에는 파라미터로 넘어온 username이 들어옴)
	public User findByUsername(String username);
	//findBy가 궁금하면 jpa Query methods를 검색하면 어떤종류가 있는지 알수있음
	


}
