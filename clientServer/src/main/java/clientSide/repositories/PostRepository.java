package clientSide.repositories;

import clientSide.entities.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreFilter;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends CrudRepository<Post,Long> {

    @PreFilter("workTime== " )
    List<Post> findAll();

    Optional<Post> findById(Long id);
    List<Post> findByEmail(String email);

    List<Post> findByTypeAndSalaryAndWorkTime(String type,String salary, String workTime);
    List<Post> findByTypeAndSalary(String type,String salary);
    List<Post> findByTypeAndWorkTime(String type,String workTime);
    List<Post> findBySalaryAndWorkTime(String salary, String workTime);

    List<Post> findBySalary(String salary);
    List<Post> findByWorkTime(String workTime);
    List<Post> findByType(String type);

    List<Post> findByTypeAndSalaryAndWorkTimeAndEmail(String type,String salary, String workTime,String email);
    List<Post> findByTypeAndSalaryAndEmail(String type,String salary,String email);
    List<Post> findByTypeAndWorkTimeAndEmail(String type,String workTime,String email);
    List<Post> findBySalaryAndWorkTimeAndEmail(String salary, String workTime,String email);
    List<Post> findBySalaryAndEmail(String salary,String email);
    List<Post> findByWorkTimeAndEmail(String workTime,String email);
    List<Post> findByTypeAndEmail(String type,String email);

}
