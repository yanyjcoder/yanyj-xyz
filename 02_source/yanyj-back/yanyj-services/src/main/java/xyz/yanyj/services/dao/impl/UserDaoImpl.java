package xyz.yanyj.services.dao.impl;


import org.springframework.stereotype.Repository;

import xyz.yanyj.base.HibernateDAO;
import xyz.yanyj.base.User;
import xyz.yanyj.services.dao.UserDao;
import xyz.yanyj.util.PageUtil.Page;
import xyz.yanyj.util.PageUtil.QueryParameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by yanyj on 2017/4/17.
 */
@Repository("userDao")
public class UserDaoImpl extends HibernateDAO<User, String> implements UserDao {




    @Override
    public User getUser(String id) {
        return get(id);
    }

    @Override
    public Page<User> getUserList() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> list = new ArrayList<String>();
        list.add("andy");
        map.put("name", list);
        return findPage(new QueryParameter(),"from User user where user.username in (:name)", map);
    }


}