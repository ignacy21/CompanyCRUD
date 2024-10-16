package Company.database.repositories;

import Company.database.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
