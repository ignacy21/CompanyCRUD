package Company.services;

import Company.database.entities.Manager;
import Company.database.entities.Project;
import Company.database.repositories.ManagerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final ProjectService projectService;

    public ManagerService(ManagerRepository managerRepository, ProjectService projectService) {
        this.managerRepository = managerRepository;
        this.projectService = projectService;
    }


    @Transactional
    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }

    @Transactional
    public Manager getManagerById(Long id) {
        return managerRepository.findById(id).orElseThrow(
                () -> new RuntimeException("There is no Manager with id: " + id)
        );
    }

    @Transactional
    public Manager createManager(Manager manager) {
        if (manager.getProjectId() != null) {
            Project projectById = projectService.getProjectById(manager.getProjectId());
            manager.setProject(projectById);

            Manager save = managerRepository.save(manager);
            projectById.setManager(save);
            projectService.updateProject(projectById.getProjectId(), projectById);
            return save;
        }
        return managerRepository.save(manager);
    }

    @Transactional
    public Manager updateManager(Long id, Manager updatedManager) {
        return managerRepository
                .findById(id)
                .map(manager -> {
                    if (updatedManager.getName() != null) {
                        manager.setName(updatedManager.getName());
                    }
                    if (updatedManager.getPhone() != null) {
                        manager.setPhone(updatedManager.getPhone());
                    }
                    if (updatedManager.getProjectId() != null) {
                        Project newProject = projectService.getProjectById(updatedManager.getProjectId());

                        // Jeśli manager był przypisany do innego projektu, usuwamy tę relację
                        if (manager.getProjectId() != null && !manager.getProjectId().equals(newProject.getProjectId())) {
                            Project currentProject = projectService.getProjectById(manager.getProjectId());
                            currentProject.setManager(null);
                            projectService.updateProject(currentProject.getProjectId(), currentProject);
                        }

                        // Przypisanie nowego projektu do menadżera
                        manager.setProject(newProject);
                        newProject.setManager(manager);
                        projectService.updateProject(newProject.getProjectId(), newProject);
                    }
                    return managerRepository.save(manager);
                })
                .orElseThrow(() -> new RuntimeException("Manager not found"));
    }

    @Transactional
    public void deleteManager(Long id) {
        managerRepository.deleteById(id);
    }
}
