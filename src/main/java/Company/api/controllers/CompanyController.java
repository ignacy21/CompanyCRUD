package Company.api.controllers;

import Company.api.DTOs.CompanyDTO;
import Company.database.entities.Company;
import Company.services.CompanyService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping(value = "/getAllCompanies", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CompanyDTO> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping(value = "/find/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompanyDTO getCompanyById(@PathVariable Long id) {
        return companyService.getCompanyDTOById(id);
    }

    @PostMapping("/create")
    public CompanyDTO createCompany(@RequestBody Company company) {
        return companyService.createCompany(company);
    }

    @PutMapping("/update/{id}")
    public CompanyDTO updateCompany(@PathVariable Long id, @RequestBody Company company) {
        return companyService.updateCompany(id, company);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
    }
}
