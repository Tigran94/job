package clientSide.repositories;

import clientSide.entities.Company;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CompanyRepository extends CrudRepository<Company,Long> {

    Optional<Company> findByCompanyName(String companyName);

    Optional<Company> findByUsername(String userName);
    Optional<Company> findByEmail(String email);
}
