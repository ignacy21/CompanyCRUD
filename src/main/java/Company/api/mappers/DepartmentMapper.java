package Company.api.mappers;

import Company.api.DTOs.DepartmentDTO;
import Company.database.entities.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {


    public DepartmentDTO mapToDTO(Department department) {
        return new DepartmentDTO(department.getDepartmentId(), department.getName(), null);
    }
}
