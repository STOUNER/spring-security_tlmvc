package web.dao;

import org.springframework.stereotype.Repository;
import web.model.Role;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public List<User> getAllUser() {
        Query query = entityManager.createQuery("FROM User ", User.class);
        return query.getResultList();
    }

    @Override
    public void deleteUserById(Long userId) {
        User user = entityManager.find(User.class, userId);
        entityManager.remove(user);
    }

    @Override
    public void updateUser(User user) {
        User oldUser = entityManager.find(User.class, user.getId());
        oldUser.setAge(user.getAge());
        oldUser.setName(user.getName());
        entityManager.persist(oldUser);
    }

    @Override
    public User getUser(Long id) {
        User user = entityManager.find(User.class, id);
        return user;
    }

    @Override
    public void getUserByRole() {
        User user1 = new User("Denis", 24, Collections.singleton(new Role(1L, "ROLE_USER")), "1224");
        User user2 = new User("Ivan", 32, Collections.singleton(new Role(1L, "ROLE_ADMIN")), "1224");
        entityManager.persist(user1);
        entityManager.persist(user2);
    }

    @Override
    public User getUserByName(String login) {
        User user = (User) entityManager.createQuery("FROM User u where u.name = :login").setParameter("login", login).getSingleResult();
        return user;
    }
}
