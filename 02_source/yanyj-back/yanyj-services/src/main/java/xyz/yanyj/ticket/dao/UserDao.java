package xyz.yanyj.ticket.dao;

import xyz.yanyj.base.User;
import xyz.yanyj.util.PageUtil.Page;

import java.util.List;


/**
 * Created by yanyj on 2017/4/19.
 */
public interface UserDao {

    User getUser(String id);

    Page<User> getUserList();
}
