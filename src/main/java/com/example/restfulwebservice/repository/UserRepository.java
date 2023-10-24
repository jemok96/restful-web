package com.example.restfulwebservice.repository;

import com.example.restfulwebservice.domain.Usertable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Usertable, Integer> {
}
