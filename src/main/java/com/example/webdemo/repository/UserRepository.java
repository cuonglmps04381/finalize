package com.example.webdemo.repository;

import com.example.webdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@Query("select user from User user "
			+ "where user.email = :email and user.removalFlag = true ")
	User findByEmail(@Param("email") String email);


	@Query("select user from User user ")
    List<User> getAll();
}
