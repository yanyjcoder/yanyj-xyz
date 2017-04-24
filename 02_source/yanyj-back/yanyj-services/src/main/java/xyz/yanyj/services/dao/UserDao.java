package xyz.yanyj.services.dao;

import xyz.yanyj.base.User;
import xyz.yanyj.util.PageUtil.Page;


/**
 * Created by yanyj on 2017/4/19.
 */
public interface UserDao {

    User getUser(String id);

    Page<User> getUserList();
}
