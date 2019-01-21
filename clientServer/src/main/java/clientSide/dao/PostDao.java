package clientSide.dao;

import clientSide.dto.JobTitle;
import clientSide.entities.Post;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PostDao extends Security {

    public Post addPost(Post post) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(post);
        transaction.commit();
        if(transaction.isActive()){
            session.flush();
        }
        session.close();
        return post;
    }

    public List<JobTitle> getJobTitlesStream() {
        Session session = sessionFactory.openSession();
        Query<Post> query = session.createQuery("from Post j", Post.class);
        List<Post> posts = query.getResultList();

        return posts
                .stream()
                .map(j -> new JobTitle(j.getId(), j.getTitle()))
                .collect(Collectors.toList());
    }

    public List<JobTitle> getJobTitlesStream(String type, String salary, String workTime) {
        Session session = sessionFactory.openSession();
        String sql;
        Query<Post> query=null;
        if(!type.equals("") && !salary.equals("") && !workTime.equals("")){
           sql="from Post j where j.type=:type and j.salary=:salary and j.workTime=:workTime";
           query = session.createQuery(sql, Post.class);
           query.setParameter("type",type).setParameter("workTime",workTime).setParameter("salary",salary);
        }else if(!type.equals("") && !salary.equals("")){
            sql="from Post j where j.type=:type and j.salary=:salary";
            query = session.createQuery(sql, Post.class);
            query.setParameter("type",type).setParameter("salary",salary);
        }
        else if(!type.equals("") && !workTime.equals("")){
            sql="from Post j where j.type=:type and j.workTime=:workTime";
            query = session.createQuery(sql, Post.class);
            query.setParameter("type",type).setParameter("workTime",workTime);
        }else if(!salary.equals("") && !workTime.equals("")){
            sql="from Post j where j.salary=:salary and j.workTime=:workTime";
            query = session.createQuery(sql, Post.class);
            query.setParameter("salary",salary).setParameter("workTime",workTime);
        }else if(!salary.equals("")){
            sql="from Post j where j.salary=:salary";
            query = session.createQuery(sql, Post.class);
            query.setParameter("salary",salary);
        }else if(!workTime.equals("")){
            sql="from Post j where j.workTime=:workTime";
            query = session.createQuery(sql, Post.class);
            query.setParameter("workTime",workTime);
        }else if(!type.equals("")){
            sql="from Post j where j.type=:type";
            query = session.createQuery(sql, Post.class);
            query.setParameter("type",type);
        }else{
            return getJobTitlesStream();
        }

        List<Post> posts = query.getResultList();

        return posts
                .stream()
                .map(j -> new JobTitle(j.getId(), j.getTitle()))
                .collect(Collectors.toList());
    }
    public Post getJobAnnouncementByIdWithStream(long id) {
        Session session = sessionFactory.openSession();
        Query<Post> query = session.createQuery("from Post j where j.id=:id", Post.class);
        query.setParameter("id", id);

        return query.getSingleResult();
    }
}
