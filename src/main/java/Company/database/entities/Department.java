package Company.database.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Team> teams;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public Long getDepartmentId() {
        return departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public Long getCompanyId() {
        if (company == null) {
            return null;
        }
        return company.getCompanyId();
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

}
