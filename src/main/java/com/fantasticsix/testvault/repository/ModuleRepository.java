package com.fantasticsix.testvault.repository;

import com.fantasticsix.testvault.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fantasticsix.testvault.model.Module;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {
    List<Module> getModulesByProject(Project project);
    List<Module> findByProjectId(Long projectId);
}
