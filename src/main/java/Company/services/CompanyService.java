package Company.services;

import Company.database.entities.Company;
import Company.database.repositories.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElseThrow(
                () -> new RuntimeException("There is no Company with id: " + id)
        );
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
                    if (company.getName() != null) {
                        company.setName(updatedCompany.getName());
                    }
                    return companyRepository.save(company);
                })
                .orElseThrow(() -> new RuntimeException("Company not found id[%s]".formatted(id)));
    }

    @Transactional
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }
}
