package Company.services;

import Company.database.entities.Department;
import Company.database.entities.Team;
import Company.database.repositories.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final DepartmentService departmentService;

    public TeamService(TeamRepository teamRepository, DepartmentService departmentService) {
        this.teamRepository = teamRepository;
        this.departmentService = departmentService;
    }


    @Transactional
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    @Transactional
    public Team getTeamById(Long id) {
        return teamRepository.findById(id).orElseThrow(
                () -> new RuntimeException("There is no Team with id: " + id)
        );
    }

    @Transactional
    public Team createTeam(Team team) {
        if (team.departmentId() != null) {
            Department departmentId = departmentService.getDepartmentById(team.departmentId());
            team.setDepartment(departmentId);
        }
        return teamRepository.save(team);
    }

    @Transactional
    public Team updateTeam(Long id, Team updatedTeam) {
        return teamRepository
                .findById(id)
                .map(team -> {
                    if (updatedTeam.getName() != null) {
                        team.setName(updatedTeam.getName());
                    }
                    if (updatedTeam.departmentId() != null) {
                        Department departmentById = departmentService.getDepartmentById(updatedTeam.departmentId());
                        team.setDepartment(departmentById);
                    }
                    return teamRepository.save(team);
                })
                .orElseThrow(() -> new RuntimeException("Team not found"));
    }

    @Transactional
    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }
}
