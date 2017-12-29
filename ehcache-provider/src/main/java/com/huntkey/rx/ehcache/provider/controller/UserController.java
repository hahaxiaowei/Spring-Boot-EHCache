package com.huntkey.rx.ehcache.provider.controller;

import com.huntkey.rx.ehcache.common.model.User;
import com.huntkey.rx.ehcache.common.util.Result;
import com.huntkey.rx.ehcache.provider.service.UserService;
import org.apache.ibatis.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by sunwei on 2017/12/20 Time:16:27
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Result insert(User user) {
        Result result = new Result();
        try {
            userService.insert(user);
            result.setData(user.getId());
        } catch (Exception e) {
            result.setRetCode(Result.RECODE_ERROR);
            result.setErrMsg("方法执行出错");
            logger.error("insert方法执行出错", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Result delete(@RequestParam String id) {
        Result result = new Result();
        try {
            userService.deleteByPrimaryKey(id);
        } catch (Exception e) {
            result.setRetCode(Result.RECODE_ERROR);
            result.setErrMsg("方法执行出错");
            logger.error("delete方法执行出错", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result update(@RequestBody User user) {
        Result result = new Result();
        try {
            userService.updateByPrimaryKey(user);
        } catch (Exception e) {
            result.setRetCode(Result.RECODE_ERROR);
            result.setErrMsg("方法执行出错");
            logger.error("update方法执行出错", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    @RequestMapping(value = "/selectByPrimarykey", method = RequestMethod.GET)
    public Result selectByPrimarykey(@RequestParam String id) {
        Result result = new Result();
        try {
            User user = userService.selectByPrimaryKey(id);
            result.setData(user);
        } catch (Exception e) {
            result.setRetCode(Result.RECODE_ERROR);
            result.setErrMsg("方法执行出错");
            logger.error("update方法执行出错", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    @RequestMapping(value = "/selectAllUser", method = RequestMethod.GET)
    public Result selectAllUser() {
        Result result = new Result();
        try {
            List<User> list = userService.selectAllUser();
            result.setData(list);
        } catch (Exception e) {
            result.setRetCode(Result.RECODE_ERROR);
            result.setErrMsg("方法执行出错");
            logger.error("selectAllUser方法执行出错", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    @RequestMapping(value = "/selectUserByAcount", method = RequestMethod.GET)
    public Result selectUserByAcount(@RequestParam Integer userId, @RequestParam String userName) {
        Result result = new Result();
        try {
            List<User> list = userService.selectUserByAcount(userId, userName);
            result.setData(list);
        } catch (Exception e) {
            result.setRetCode(Result.RECODE_ERROR);
            result.setErrMsg("方法执行出错");
            logger.error("selectAllUser方法执行出错", e);
            throw new RuntimeException(e);
        }
        return result;
    }


    @RequestMapping(value = "/ehcache", method = RequestMethod.GET)
    public String ehcache() {
        logger.info("===========  进行Encache缓存测试");
        List<User> allUsers = userService.selectAllUser();
        User lastUser = allUsers.get(allUsers.size() - 1);
        String lastUserUsername = lastUser.getUserName();
        String indexString = lastUserUsername.substring(4);

        logger.info("===========  ====生成第一个用户====");
        User user1 = new User();
        //生成第一个用户的唯一标识符 UUID
        user1.setUserId(100);
        user1.setName("user" + (Integer.parseInt(indexString) + 1));
        user1.setUserName(user1.getName());
        user1.setAge(20);
        user1.setBalance("1000");
        if (userService.insert(user1) == 0) {
            throw new CacheException("用户对象插入数据库失败");
        }

        allUsers = userService.selectAllUser();
        lastUser = allUsers.get(allUsers.size() - 1);
        String lastId = lastUser.getId();

        //第一次查询
        logger.info("===========  第一次查询");
        logger.info("===========  第一次查询结果: {}", userService.selectByPrimaryKey(lastId));
        //通过缓存查询
        logger.info("===========  通过缓存第 1 次查询");
        logger.info("===========  通过缓存第 1 次查询结果: {}", userService.selectByPrimaryKey(lastId));
        logger.info("===========  通过缓存第 2 次查询");
        logger.info("===========  通过缓存第 2 次查询结果: {}", userService.selectByPrimaryKey(lastId));
        logger.info("===========  通过缓存第 3 次查询");
        logger.info("===========  通过缓存第 3 次查询结果: {}", userService.selectByPrimaryKey(lastId));

        logger.info("===========  ====准备修改数据====");
        User user2 = new User();
        user2.setName(lastUser.getName());
        user2.setUserName(lastUser.getUserName());
        user2.setAge(lastUser.getAge() + 10);
        user2.setBalance(String.valueOf(user2.getAge()));
        user2.setUserId(3);
        user2.setId(lastId);
        try {
            userService.updateByPrimaryKey(user2);
        } catch (CacheException e) {
            e.printStackTrace();
        }
        logger.info("===========  ====修改后再次查询数据");
        Object resultObj = userService.selectByPrimaryKey(lastUser.getId());
        logger.info("===========  ====修改后再次查询数据结果: {}", resultObj);
        return "success";
    }

}
