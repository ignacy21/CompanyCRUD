package Company.api.DTOs;

import Company.database.entities.Team;

import java.util.List;

public class DepartmentDTO {
    public DepartmentDTO(Long departmentId, String name, List<Team> teams) {
        this.departmentId = departmentId;
        this.name = name;
        this.teams = teams;
    }

    private Long departmentId;
    private String name;
    private List<Team> teams;

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
