package clientSide.dao;

import clientSide.entities.Post;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

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
}
