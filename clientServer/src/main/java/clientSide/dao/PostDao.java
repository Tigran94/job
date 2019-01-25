package clientSide.dao;

import clientSide.dto.JobTitle;
import clientSide.entities.Post;
import clientSide.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PostDao {

    @Autowired
    SessionFactory sessionFactory;

    public Post addPost(Post post, Authentication user) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from User j where j.username=:username", User.class).setParameter("username",user.getName());
        User user1 = (User) query.getSingleResult();

        post.setUser(user1);
        post.setEmail(user1.getEmail());
        Transaction transaction = session.beginTransaction();
        session.save(post);
        transaction.commit();
        if(transaction.isActive()){
            session.flush();
        }
        session.close();
        return post;
    }

    public void deletePost(long id){
        Session session = sessionFactory.openSession();
        Post post;
        Transaction transaction = session.beginTransaction();
        post = (Post)session.load(Post.class,id);
        session.delete(post);
        transaction.commit();
        if(transaction.isActive()){
            session.flush();
        }
        session.close();
    }
    public List<JobTitle> getJobTitles() {
        Session session = sessionFactory.openSession();
        Query<Post> query = session.createQuery("from Post j", Post.class);
        List<Post> posts = query.getResultList();

        return posts
                .stream()
                .map(j -> new JobTitle(j.getId(), j.getTitle()))
                .collect(Collectors.toList());
    }

    public List<JobTitle> getJobTitles(String username) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from User j where j.username=:username", User.class).setParameter("username",username);
        User user1 = (User) query.getSingleResult();

        Query<Post> query2 = session.createQuery("from Post j where j.email=:email", Post.class);
        query2.setParameter("email",user1.getEmail());
        List<Post> posts = query2.getResultList();

        return posts
                .stream()
                .map(j -> new JobTitle(j.getId(), j.getTitle()))
                .collect(Collectors.toList());
    }

    public List<JobTitle> getJobTitles(String type, String salary, String workTime,String... email) {
        if(email.length!=0)
            return getJobTitlesStream(type,salary,workTime,email[0]);
        else
            return getJobTitlesStream(type,salary,workTime);
    }
    public Post getJobAnnouncementByIdWithStream(long id) {
        Session session = sessionFactory.openSession();
        Query<Post> query = session.createQuery("from Post j where j.id=:id", Post.class);
        query.setParameter("id", id);

        return query.getSingleResult();
    }

    private List<JobTitle> getJobTitlesStream(String type, String salary, String workTime) {
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
            return getJobTitles();
        }

        List<Post> posts = query.getResultList();

        return posts
                .stream()
                .map(j -> new JobTitle(j.getId(), j.getTitle()))
                .collect(Collectors.toList());
    }
    private List<JobTitle> getJobTitlesStream(String type, String salary, String workTime,String email) {
        Session session = sessionFactory.openSession();
        String sql;
        Query<Post> query=null;
        if(!type.equals("") && !salary.equals("") && !workTime.equals("")){
            sql="from Post j where j.type=:type and j.salary=:salary and j.workTime=:workTime and j.email=:email";
            query = session.createQuery(sql, Post.class);
            query.setParameter("type",type).setParameter("workTime",workTime).setParameter("salary",salary).setParameter("email",email);
        }else if(!type.equals("") && !salary.equals("")){
            sql="from Post j where j.type=:type and j.salary=:salary and j.email=:email";
            query = session.createQuery(sql, Post.class);
            query.setParameter("type",type).setParameter("salary",salary).setParameter("email",email);
        }
        else if(!type.equals("") && !workTime.equals("")){
            sql="from Post j where j.type=:type and j.workTime=:workTime and j.email=:email";
            query = session.createQuery(sql, Post.class);
            query.setParameter("type",type).setParameter("workTime",workTime).setParameter("email",email);
        }else if(!salary.equals("") && !workTime.equals("")){
            sql="from Post j where j.salary=:salary and j.workTime=:workTime and j.email=:email";
            query = session.createQuery(sql, Post.class);
            query.setParameter("salary",salary).setParameter("workTime",workTime).setParameter("email",email);
        }else if(!salary.equals("")){
            sql="from Post j where j.salary=:salary and j.email=:email";
            query = session.createQuery(sql, Post.class);
            query.setParameter("salary",salary).setParameter("email",email);
        }else if(!workTime.equals("")){
            sql="from Post j where j.workTime=:workTime and j.email=:email";
            query = session.createQuery(sql, Post.class);
            query.setParameter("workTime",workTime).setParameter("email",email);
        }else if(!type.equals("")){
            sql="from Post j where j.type=:type and j.email=:email";
            query = session.createQuery(sql, Post.class);
            query.setParameter("type",type).setParameter("email",email);
        }else{
            return getJobTitles(email);
        }

        List<Post> posts = query.getResultList();

        return posts
                .stream()
                .map(j -> new JobTitle(j.getId(), j.getTitle()))
                .collect(Collectors.toList());
    }
}
