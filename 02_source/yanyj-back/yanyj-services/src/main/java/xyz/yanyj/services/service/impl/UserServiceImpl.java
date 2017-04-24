package xyz.yanyj.services.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.yanyj.base.User;
import xyz.yanyj.services.dao.UserDao;
import xyz.yanyj.services.service.UserService;
import xyz.yanyj.util.PageUtil.Page;

/**
 * Created by yanyj on 2017/4/17.
 */

@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

//    @Override
//    public User load(String id) {
//        return userDao.load(id);
//    }
//
    @Override
    public User getUser(String id) {
        return userDao.getUser(id);
    }

    @Override
    public Page<User> getUserList() {


        return userDao.getUserList();
    }
//
//    @Override
//    public List<User> findAll() {
//        return userDao.findAll();
//    }
//
//    @Override
//    public void persist(User entity) {
//        userDao.persist(entity);
//    }
//
//    @Override
//    public String save(User entity) {
//        return userDao.save(entity);
//    }
//
//    @Override
//    public void saveOrUpdate(User entity) {
//        userDao.saveOrUpdate(entity);
//    }
//
//    @Override
//    public void delete(String id) {
//        userDao.delete(id);
//    }
//
//    @Override
//    public void flush() {
//        userDao.flush();
//    }

}