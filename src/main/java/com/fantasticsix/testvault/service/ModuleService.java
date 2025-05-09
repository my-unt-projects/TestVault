package com.fantasticsix.testvault.service;

import java.util.List;
import com.fantasticsix.testvault.model.Module;

public interface ModuleService {
    Module add(Module module);
    Module get(Long id);
    Module update(Module module);
    void delete(Long id);
    List<Module> getAllByProjectId(Long projectId);
    List<Module> getAll();
    List<Module> getModulesByProjectId(Long projectId);
}
