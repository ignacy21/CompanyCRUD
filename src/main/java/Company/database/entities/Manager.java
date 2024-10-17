package Company.database.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "manager")
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_id")
    private Long managerId;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @OneToOne(mappedBy = "manager")
    private Project project;

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getProjectId() {
        if (project == null) {
            return null;
        }
        return project.getProjectId();
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
