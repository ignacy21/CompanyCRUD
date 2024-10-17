package Company.services;

import Company.database.entities.Project;
import Company.database.entities.Team;
import Company.database.repositories.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TeamService teamService;

    public ProjectService(ProjectRepository projectRepository, TeamService teamService) {
        this.projectRepository = projectRepository;
        this.teamService = teamService;
    }


    @Transactional
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Transactional
    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElseThrow(
                () -> new RuntimeException("There is no Project with id: " + id)
        );
    }

    @Transactional
    public Project createProject(Project project) {
        Team teamById = teamService.getTeamById(project.getTeamId());

        project.setTeam(teamById);
        Project save = projectRepository.save(project);
        teamById.setProject(save);
        teamService.updateTeam(teamById.getTeamId(), teamById);
        return save;
    }

    @Transactional
    public Project updateProject(Long id, Project updatedProject) {
        return projectRepository
                .findById(id)
                .map(project -> {
                    if (updatedProject.getManager() != null) {
                        project.setManager(updatedProject.getManager());
                    }
                    if (updatedProject.getTeamId() != null) {
                        Team teamById = teamService.getTeamById(updatedProject.getTeamId());
                        project.setTeam(teamById);
                    }
                    return projectRepository.save(project);
                })
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    @Transactional
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}
