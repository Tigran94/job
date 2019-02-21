package clientSide.repositories;

import clientSide.entities.CompanyEntity;
import clientSide.entities.CompanyHistory;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CompanyHistoryRepository extends CrudRepository<CompanyHistory,Long> {

//    Optional<CompanyEntity> findByCompanyName(String companyName);
//
//    Optional<CompanyEntity> findByUsername(String userName);
//    Optional<CompanyEntity> findByEmail(String email);
}
