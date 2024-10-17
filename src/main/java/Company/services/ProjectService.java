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
                    return getProject(updatedProject, project);
                })
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    private Project getProject(Project updatedProject, Project project) {
        Long teamIdToMoveProject = updatedProject.getTeamId();
        Long teamIdToRemoveProject = project.getTeamId();

        Team teamToMoveProject = teamService.getTeamById(teamIdToMoveProject);
        Team teamToRemoveProject = teamService.getTeamById(teamIdToRemoveProject);

        project.setTeam(teamToMoveProject);
        teamToMoveProject.setProject(project);
        teamService.updateTeam(teamToMoveProject.getTeamId(), teamToMoveProject);

        teamToRemoveProject.setProject(null);
        teamService.updateTeam(teamIdToRemoveProject, teamToRemoveProject);

        return project;
    }

    @Transactional
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

}
