package xyz.yanyj.ticket.service;

import xyz.yanyj.base.User;
import xyz.yanyj.util.PageUtil.Page;

import java.util.List;

/**
 * Created by yanyj on 2017/4/17.
 */
public interface UserService {
//    User load(String id);

    User getUser(String id);

    Page<User> getUserList();

//    List<User> findAll();
//
//    void persist(User entity);
//
//    String save(User entity);
//
//    void saveOrUpdate(User entity);
//
//    void delete(String id);
//
//    void flush();
}
