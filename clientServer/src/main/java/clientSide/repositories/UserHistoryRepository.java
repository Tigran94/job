package clientSide.repositories;

import clientSide.entities.CompanyHistory;
import clientSide.entities.UserHistory;
import org.springframework.data.repository.CrudRepository;

public interface UserHistoryRepository extends CrudRepository<UserHistory,Long> {

//    Optional<CompanyEntity> findByCompanyName(String companyName);
//
//    Optional<CompanyEntity> findByUsername(String userName);
//    Optional<CompanyEntity> findByEmail(String email);
}
