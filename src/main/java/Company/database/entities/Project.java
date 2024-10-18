package Company.database.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "manager_id", referencedColumnName = "manager_id")
    private Manager manager;

    @OneToOne(mappedBy = "project")
    private Team team;

    public Long getProjectId() {
        return projectId;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    @JsonIgnore
    public Long getTeamId() {
        if (team == null) {
            return null;
        }
        return team.getTeamId();
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
