package clientSide.repositories;

import clientSide.entities.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends CrudRepository<Post,Long> {

    Optional<Post> findById(Long id);
    List<Post> findByEmail(String email);


    List<Post> findBySalary(String salary);
    List<Post> findByWorkTime(String workTime);
    List<Post> findByType(String type);

    List<Post> findBySalaryAndEmail(String salary,String email);
    List<Post> findByWorkTimeAndEmail(String workTime,String email);
    List<Post> findByTypeAndEmail(String type,String email);

}
