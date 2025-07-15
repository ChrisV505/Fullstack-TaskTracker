package com.chrisV.tasktracker.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chrisV.tasktracker.backend.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {}
