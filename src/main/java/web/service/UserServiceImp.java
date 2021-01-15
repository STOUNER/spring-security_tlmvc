package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.model.User;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    private final UserDao userDao;

    public UserServiceImp(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public void addUser(User user) {
     userDao.addUser(user);
    }

    @Override
    @Transactional
    public List<User> getAllUser() {
        List<User> resultList = userDao.getAllUser();
        return resultList;
    }

    @Override
    @Transactional
    public void updateUser(User user) {
     userDao.updateUser(user);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
     userDao.deleteUserById(id);
    }

    @Override
    @Transactional
    public User getUser(Long id) {
        return userDao.getUser(id);
    }

    @Override
    @Transactional
    public void getUserByRole() {
        userDao.getUserByRole();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userDao.getUserByName(s);
    }
}
