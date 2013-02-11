package com.polmos.webchess.dao;

import com.polmos.webchess.matchmgnt.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Piotrek
 */
@Service(value = "userDAO")
@Transactional
public class UserDAOImpl implements UserDAO {

    private static Logger logger = Logger.getLogger(UserDAOImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<User>();
        Query createNamedQuery = entityManager.createNamedQuery("User.findAll");
        result = createNamedQuery.getResultList();
        return result;
    }

    @Override
    public User findUserById(Integer id) {
        User user = entityManager.find(User.class, id);

        return user;
    }

    @Override
    public void saveUser(User user) {
        logger.debug("saving user: " + user);
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user) {
        entityManager.persist(user);

    }

    @Override
    public void removeUser(User user) {
        User merge = entityManager.merge(user);
        entityManager.remove(merge);
    }

    @Override
    public User findUserByName(String userName) {
        Query namedQuery = entityManager.createNamedQuery("User.findByLogin");
        namedQuery.setParameter("login", userName);
        User user = (User)namedQuery.getSingleResult();
        return user;
    }
}
