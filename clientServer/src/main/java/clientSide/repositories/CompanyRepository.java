package clientSide.repositories;

import clientSide.entities.CompanyEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CompanyRepository extends CrudRepository<CompanyEntity,Long> {

    Optional<CompanyEntity> findByCompanyName(String companyName);

    Optional<CompanyEntity> findByUsername(String userName);
    Optional<CompanyEntity> findByEmail(String email);
}
