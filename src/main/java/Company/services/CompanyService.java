package Company.services;

import Company.api.DTOs.CompanyDTO;
import Company.api.mappers.CompanyMapper;
import Company.database.entities.Company;
import Company.database.repositories.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    @Transactional
    public List<CompanyDTO> getAllCompanies() {
        List<Company> all = companyRepository.findAll();
        List<CompanyDTO> allDTO = new ArrayList<>();
        for (Company company : all) {
            allDTO.add(companyMapper.mapToDTO(company));
        }
        return allDTO;
    }

    @Transactional
    public CompanyDTO getCompanyDTOById(Long id) {
        Company company = getCompanyById(id);
        return companyMapper.mapToDTO(company);
    }

    @Transactional
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElseThrow(
                () -> new RuntimeException("There is no Company with id: " + id)
        );
    }

    @Transactional
    public CompanyDTO createCompany(Company company) {
        company.setDepartments(Collections.emptyList());
        Company save = companyRepository.save(company);
        return companyMapper.mapToDTO(save);
    }

    @Transactional
    public CompanyDTO updateCompany(Long id, Company updatedCompany) {
        Company company = companyRepository
                .findById(id)
                .map(comp -> {
                    if (updatedCompany.getName() != null) {
                        comp.setName(updatedCompany.getName());
                    }
//                    if (updatedCompany.getDepartments() != null) {
//                        comp.setDepartments(updatedCompany.getDepartments());
//                    }
                    return companyRepository.save(comp);
                })
                .orElseThrow(() -> new RuntimeException("Company not found"));
        return companyMapper.mapToDTO(company);
    }

    // TODO what when i want to add new departments not change it all

    @Transactional
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }
}
