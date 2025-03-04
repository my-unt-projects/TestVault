package com.fantasticsix.testvault.repository;

import com.fantasticsix.testvault.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
