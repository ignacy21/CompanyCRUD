package Company.api.DTOs;

import java.util.List;

public class CompanyDTO {
    public CompanyDTO(Long companyId, String name, List<DepartmentDTO> departmentDTOs) {
        this.companyId = companyId;
        this.name = name;
        this.departmentDTOs = departmentDTOs;
    }

    private Long companyId;
    private String name;
    private List<DepartmentDTO> departmentDTOs;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DepartmentDTO> getDepartmentDTOs() {
        return departmentDTOs;
    }

    public void setDepartmentDTOs(List<DepartmentDTO> departmentDTOs) {
        this.departmentDTOs = departmentDTOs;
    }
}
