package Company.api.mappers;

import Company.api.DTOs.CompanyDTO;
import Company.api.DTOs.DepartmentDTO;
import Company.database.entities.Company;
import Company.database.entities.Department;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompanyMapper {

    private final DepartmentMapper departmentMapper;

    public CompanyMapper(DepartmentMapper departmentMapper) {
        this.departmentMapper = departmentMapper;
    }

    public CompanyDTO mapToDTO(Company company) {
        List<Department> departments = company.getDepartments();
        List<DepartmentDTO> departmentsDTO = new ArrayList<>();
        for (Department department : departments) {
            departmentsDTO.add(departmentMapper.mapToDTO(department));
        }
        return new CompanyDTO(company.getCompanyId(), company.getName(), departmentsDTO);
    }
}
