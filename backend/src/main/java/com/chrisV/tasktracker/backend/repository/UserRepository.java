package com.chrisV.tasktracker.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chrisV.tasktracker.backend.model.User;

public interface UserRepository extends JpaRepository<User, Long> {}
