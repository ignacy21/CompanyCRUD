package Company.services;

import Company.api.DTOs.DepartmentDTO;
import Company.api.mappers.DepartmentMapper;
import Company.database.entities.Company;
import Company.database.entities.Department;
import Company.database.repositories.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final CompanyService companyService;
    private final DepartmentMapper departmentMapper;

    public DepartmentService(DepartmentRepository departmentRepository, CompanyService companyService, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.companyService = companyService;
        this.departmentMapper = departmentMapper;
    }

    @Transactional
    public List<DepartmentDTO> getAllDepartments() {
        List<Department> all = departmentRepository.findAll();
        List<DepartmentDTO> allDTO = new ArrayList<>();
        for (Department department : all) {
            allDTO.add(departmentMapper.mapToDTO(department));
        }
        return allDTO;
    }

    @Transactional
    public DepartmentDTO getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(
                () -> new RuntimeException("There is no Department with id: " + id)
        );
        return departmentMapper.mapToDTO(department);
    }

    @Transactional
    public DepartmentDTO createDepartment(Department department) {
        if (department.getCompany() != null) {
            Company byId = companyService.getCompanyById(department.getCompany().getCompanyId());
            department.setCompany(byId);
        }
        Department save = departmentRepository.save(department);
        return departmentMapper.mapToDTO(save);
    }

    @Transactional
    public DepartmentDTO updateDepartment(Long id, Department updatedDepartment) {
        Department department = departmentRepository
                .findById(id)
                .map(depart -> {
                    if (updatedDepartment.getName() != null) {
                        depart.setName(updatedDepartment.getName());
                    }
//                    if (updatedDepartment.getTeams() != null) {
//                        depart.setTeams(updatedDepartment.getTeams());
//                    }
                    if (updatedDepartment.getCompany() != null) {
                        Company byId = companyService.getCompanyById(depart.getCompany().getCompanyId());
                        depart.setCompany(byId);
                    }
                    return departmentRepository.save(depart);
                })
                .orElseThrow(() -> new RuntimeException("Department not found"));
        return departmentMapper.mapToDTO(department);
    }

    @Transactional
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

}
