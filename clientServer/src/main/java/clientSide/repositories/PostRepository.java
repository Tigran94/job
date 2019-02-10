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

}
