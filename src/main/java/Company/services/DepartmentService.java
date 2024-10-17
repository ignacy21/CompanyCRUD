package Company.services;

import Company.database.entities.Company;
import Company.database.entities.Department;
import Company.database.repositories.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final CompanyService companyService;

    public DepartmentService(DepartmentRepository departmentRepository, CompanyService companyService) {
        this.departmentRepository = departmentRepository;
        this.companyService = companyService;
    }

    @Transactional
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Transactional
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id).orElseThrow(
                () -> new RuntimeException("There is no Department with id: " + id)
        );
    }

    @Transactional
    public Department createDepartment(Department department) {
        if (department.getCompanyId() != null) {
            Company companyById = companyService.getCompanyById(department.getCompanyId());
            department.setCompany(companyById);
        }
        return departmentRepository.save(department);
    }

    @Transactional
    public Department updateDepartment(Long id, Department updatedDepartment) {
        long l = updatedDepartment.getCompanyId();
        return departmentRepository
                .findById(id)
                .map(depart -> {
                    if (updatedDepartment.getName() != null) {
                        depart.setName(updatedDepartment.getName());
                    }
                    if (updatedDepartment.getCompanyId() != null) {
                        Company byId = companyService.getCompanyById(updatedDepartment.getCompanyId());
                        depart.setCompany(byId);
                    }
                    return departmentRepository.save(depart);
                })
                .orElseThrow(() -> new RuntimeException("Department not found"));
    }

    @Transactional
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

}
