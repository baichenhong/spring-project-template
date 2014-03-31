package com.github.jbai.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.jbai.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	List<User> findByLoginName(String loginName);
	
}
