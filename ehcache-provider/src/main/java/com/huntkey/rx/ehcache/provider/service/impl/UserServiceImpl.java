package com.huntkey.rx.ehcache.provider.service.impl;

import com.github.pagehelper.util.StringUtil;
import com.huntkey.rx.ehcache.common.model.User;
import com.huntkey.rx.ehcache.common.util.Result;
import com.huntkey.rx.ehcache.provider.dao.UserDao;
import com.huntkey.rx.ehcache.provider.service.UserService;
import com.mysql.jdbc.StringUtils;
import com.sun.org.apache.regexp.internal.RE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sunwei on 2017/12/20 Time:15:53
 */
@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String CACHE_KEY = "'user'";
    private static final String CACHE_NAME_B = "cache-b";

    //* @Cacheable : Spring在每次执行前都会检查Cache中是否存在相同key的缓存元素，如果存在就不再执行该方法，而是直接从缓存中获取结果进行返回，否则才会执行并将返回结果存入指定的缓存中。
    //* @CacheEvict : 清除缓存。
    //* @CachePut : @CachePut也可以声明一个方法支持缓存功能。使用@CachePut标注的方法在执行前不会去检查缓存中是否存在之前执行过的结果，而是每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中。

    @Autowired
    private UserDao userDao;


    @CacheEvict(value = CACHE_NAME_B, key = CACHE_KEY)
    @Override
    public int insert(User user) {
        return userDao.insert(user);
    }

    @CacheEvict(value = CACHE_NAME_B, key = "'user_' + #id") //这是清除缓存
    @Override
    public int deleteByPrimaryKey(String id) {
        Result result = new Result();
        return userDao.deleteByPrimaryKey(id);
    }

    @CacheEvict(value = CACHE_NAME_B, key = "'user_'+ #user.id")
    @Override
    public User updateByPrimaryKey(User user) {
        userDao.updateByPrimaryKey(user);
        return user;
    }

    @Cacheable(value = CACHE_NAME_B, key = "'user_'+ #id")
    @Override
    public User selectByPrimaryKey(String id) {
        return userDao.selectByPrimaryKey(id);
    }

    @Override
    public List<User> selectAllUser() {
        return userDao.selectAllUser();
    }

    @Cacheable(value = CACHE_NAME_B, key = "#userId+'_'+#userName")
    @Override
    public Result selectUserByAcount(Integer userId, String userName) {
        Result result = new Result();
        try {
            List<User> list = userDao.selectUserByAcount(userId, userName);
            if (list.size() == 0 || list.isEmpty()) {
                result.setRetCode(Result.RECODE_ERROR);
                result.setErrMsg("查找数据不存在");
                return result;
            }
            result.setData(list);
        } catch (Exception e) {
            result.setRetCode(Result.RECODE_ERROR);
            result.setErrMsg("方法执行出错");
            logger.error("方法执行出错", e);
            throw new RuntimeException(e);
        }
        return result;
    }
}
