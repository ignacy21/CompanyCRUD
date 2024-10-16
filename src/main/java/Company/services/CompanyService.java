package Company.services;

import Company.database.entities.Company;
import Company.database.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Transactional
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Transactional
    public Optional<Company> getCompanyById(Long id) {
        return companyRepository.findById(id);
    }

    @Transactional
    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    @Transactional
    public Company updateCompany(Long id, Company updatedCompany) {
        return companyRepository
                .findById(id)
                .map(company -> {
                    company.setName(updatedCompany.getName());
                    // TODO what when i want to add new departments not change it all
                    company.setDepartments(updatedCompany.getDepartments());
                    return companyRepository.save(company);
                })
                .orElseThrow(() -> new RuntimeException("Company not found"));
    }

    @Transactional
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }
}
