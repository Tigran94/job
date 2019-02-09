package clientSide.repositories;

import clientSide.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends CrudRepository<PostEntity,Long>, JpaSpecificationExecutor<PostEntity> {

    Optional<PostEntity> findById(Long id);
    List<PostEntity> findAll();
    List<PostEntity> findByEmail(String email);

    List<PostEntity> findBySalary(String salary);
    List<PostEntity> findByWorkTime(String workTime);
    List<PostEntity> findByType(String type);

    List<PostEntity> findBySalaryAndEmail(String salary, String email);
    List<PostEntity> findByWorkTimeAndEmail(String workTime, String email);
    List<PostEntity> findByTypeAndEmail(String type, String email);

}
