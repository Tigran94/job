package clientSide.search;

import clientSide.entities.PostEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class PostSearch implements Specification<PostEntity> {


    String type;
    String salary;
    String workTime;


    @Override
    public Predicate toPredicate(Root<PostEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        criteriaQuery.distinct(true);
        if(type!=null && !type.equals("")){
            predicates.add(criteriaBuilder.equal(root.get("type"),type));
        }
        if(workTime!=null && !workTime.equals("")){
            predicates.add(criteriaBuilder.equal(root.get("workTime"),workTime));
        }
        if(salary!=null && !salary.equals("")){
            predicates.add(criteriaBuilder.equal(root.get("salary"),salary));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
