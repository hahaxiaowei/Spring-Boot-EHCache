package com.huntkey.rx.ehcache.provider.service;


import com.huntkey.rx.ehcache.common.model.User;
import com.huntkey.rx.ehcache.common.util.Result;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by sunwei on 2017/12/20 Time:15:53
 */
public interface UserService {

    int insert(User user);

    int deleteByPrimaryKey(String id);

    User updateByPrimaryKey(User user);

    User selectByPrimaryKey(String id);

    Result selectUserByAcount(@Param("userId") Integer userId, @Param("name") String userName);

    List<User> selectAllUser();
}
