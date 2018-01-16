package com.huntkey.rx.ehcache;

import com.huntkey.rx.ehcache.common.model.User;
import com.huntkey.rx.ehcache.common.util.HttpClientUtil;
import com.huntkey.rx.ehcache.common.util.Result;
import com.huntkey.rx.ehcache.provider.EhcacheProviderApplication;
import com.huntkey.rx.ehcache.provider.service.UserService;
import org.apache.ibatis.cache.CacheException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sunwei on 2017/12/21 Time:9:05
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EhcacheProviderApplication.class)
public class UserServiceTest {

    private Logger logger = LoggerFactory.getLogger(UserServiceTest.class);
    @Autowired
    private UserService userService;

    @Test
    public void insertTest() {
        User user = new User();
//        user.setId(String.valueOf(UUID.randomUUID()));
        user.setUserId(101010);
        user.setUserName("小绿");
        user.setName("卫庄");
        user.setAge(20);
        user.setBalance("500");
        Result result = new Result();
        try {
            userService.insert(user);
            result.setData(user.getId());
        } catch (Exception e) {
            result.setRetCode(Result.RECODE_ERROR);
            result.setErrMsg("执行错误");
            logger.error("方法执行出错", e);
            throw new RuntimeException(e);
        }
        logger.info("插入结果为：" + result);
    }

    @Test
    public void deleteTest() {
        String id = "5e0e9de5e5f011e7bfbd88d7f63dcda2";
        Result result = new Result();
        userService.deleteByPrimaryKey(id);
        logger.info("删除结果" + userService.deleteByPrimaryKey(id));
    }

    @Test
    public void test() {
        String url = "http://" + "10.3.99.25:8585" + "/user" + "/insert";
        User user = new User();
        user.setUserId(1000);
        user.setUserName("nihao");
        user.setName("小红");
        user.setAge(20);
        user.setBalance("3000");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userid", user.getUserId());
        map.put("username", user.getUserName());
        map.put("age", user.getAge());
        map.put("name", user.getName());
        map.put("balance", user.getBalance());
        String answer = "";
        try {
            answer = HttpClientUtil.httpPostRequest(url, map);
        } catch (Exception e) {
            logger.error("方法执行出错", e);
            throw new RuntimeException(e);
        }
        logger.info("结果为" + answer);
    }

    @Test
    public void update() {
        User user = new User();
//        user.setId(String.valueOf(UUID.randomUUID()));
        user.setId("ee6f396de61e11e7bfbd88d7f63dcda2");
        user.setUserId(1010);
        user.setUserName("你好");
        user.setName("大家好");
        user.setAge(100);
        user.setBalance("20000");
        User user1 = userService.updateByPrimaryKey(user);
        logger.info("*********************************");
        logger.info("更新结果" + user.toString());
        logger.info("************根据更新的结果查询*********************");
        User user2 = userService.selectByPrimaryKey("ee6f396de61e11e7bfbd88d7f63dcda2");
        logger.info("**************查询到的结果为*****************");
        logger.info(user2.toString());
        logger.info("*********************************");
    }

    @Test
    public void select() {
        String id = "827b6cf9e62911e7bfbd88d7f63dcda2";
        Result result = new Result();
        try {
            User user = userService.selectByPrimaryKey(id);
            result.setData(user);
        } catch (Exception e) {
            result.setRetCode(Result.RECODE_ERROR);
            result.setErrMsg("执行错误");
            logger.error("方法执行出错", e);
            throw new RuntimeException(e);
        }
        logger.info("**************************************");
        logger.info("查询结果" + result);
        logger.info("**************************************");
    }

    @Test
    public void ehcache() {
        //先从数据库里面查一条信息出来,保证后面添加的数据和数据库里面的同步
        logger.info("===========  进行Encache缓存测试");
        List<User> allUsers = userService.selectAllUser();
        Result result = new Result();
        result.setData(allUsers);
        logger.info("查询的数据条数" + allUsers.size());
        logger.info("查询的所有数据为" + result);
        User lastUser = allUsers.get(allUsers.size() - 1);
        String lastUserUsername = lastUser.getUserName();
        String indexString = lastUserUsername.substring(4);

        //开始测试
        logger.info("===========  ====生成第一个用户====");
        User user1 = new User();
        //生成第一个用户的唯一标识符 UUID
        user1.setUserId(lastUser.getUserId());
        user1.setName("user" + (Integer.parseInt(indexString) + 1));
        user1.setUserName(user1.getName());
        user1.setAge(20);
        user1.setBalance("1000");
        int tag = userService.insert(user1);
        if (tag == 0) {
            throw new CacheException("用户对象插入数据库失败");
        }
        logger.info("**********************************");
        logger.info("插入数据的结果" + tag);
        logger.info("**********************************");
        allUsers = userService.selectAllUser();
        lastUser = allUsers.get(allUsers.size() - 1);
        logger.info("***********************************");
        logger.info("最后一个用户的ID为：" + lastUser.getId());
        logger.info("************************************");
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
        user2.setId(lastId);
        user2.setUserId(lastUser.getUserId());
        user2.setName(lastUser.getName());
        user2.setUserName(lastUser.getUserName());
        user2.setAge(lastUser.getAge() + 10);
        user2.setBalance(String.valueOf(user2.getAge()));
        logger.info("******************************");
        logger.info("需要修改的数据" + user2.toString());
        logger.info("******************************");
        try {
            User user = userService.updateByPrimaryKey(user2);
            logger.info("===========  ==== 修改数据 == {} ==成功" + user.toString());
        } catch (CacheException e) {
            e.printStackTrace();
        }
        try {
//            logger.info("=============清除缓存==============");
//            Result result1 = userService.deleteByPrimaryKey(result.getData().toString());
//            logger.info("=============清除结果================");
//            logger.info(" "+result1);
            logger.info("===========  ====修改后再次查询数据");
            User user = userService.selectByPrimaryKey(lastId);
            logger.info("===========  ====修改后再次查询数据结果: {}", user.toString());
            logger.info("执行结束");
        } catch (Exception e) {
            logger.error("方法执行出错", e);
            throw new RuntimeException(e);
        }

    }

}
