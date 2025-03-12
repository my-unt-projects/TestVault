package com.fantasticsix.testvault.service;

import java.util.List;
import com.fantasticsix.testvault.model.Module;

public interface ModuleService {
    Module addModule(Module module);
    Module getModule(Long id);
    Module updateModule(Module module);
    void deleteModule(Long id);
    List<Module> getModulesByProjectId(Long projectId);
}
