package com.huntkey.rx.ehcache.provider.dao;


import com.huntkey.rx.ehcache.common.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by sunwei on 2017/12/20 Time:16:00
 */
public interface UserDao {

    int insert(User user);

    int deleteByPrimaryKey(String id);

    int updateByPrimaryKey(User user);

    User selectByPrimaryKey(String id);

    List<User> selectAllUser();

    List<User> selectUserByAcount(@Param("userId") Integer userId,@Param("name") String userName);
}
