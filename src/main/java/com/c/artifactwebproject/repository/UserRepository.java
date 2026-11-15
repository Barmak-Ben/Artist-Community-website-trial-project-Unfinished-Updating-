package com.c.artifactwebproject.repository;

import com.c.artifactwebproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
};