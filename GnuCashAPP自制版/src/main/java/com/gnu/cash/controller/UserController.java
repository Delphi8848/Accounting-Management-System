package com.gnu.cash.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gnu.cash.mapper.UserMapper;
import com.gnu.cash.pojo.User;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Resource
    private UserMapper userMapper;

    /* 更新用户状态接口 */
    @PostMapping("/updateStatus")
    public Boolean updateUserStatus(@RequestParam Integer userid, @RequestParam String status) {
        return userMapper.updateStatus(userid, status) > 0;
    }



    /*创建*/
    @PostMapping("insert")
    public Integer createUser(@RequestBody User user) {
        userMapper.insert(user);
        return user.getId();
    }


    /*更新*/
    @PostMapping("/update")
    public Boolean updateUser(@RequestBody User user) {
        return userMapper.updateById(user) > 0;
    }


    /*查询所有用户*/
    @GetMapping
    public List<User> listAllUsers() {
        return userMapper.selectList(null);
    }


    /*删除*/
    @DeleteMapping("/{id}")
    public Boolean deleteUser(@PathVariable Integer id) {
        return userMapper.deleteById(id) > 0;
    }


    @GetMapping("/{id}")
    public User getById(@PathVariable Integer id) {
        return userMapper.selectById(id);
    }

    @GetMapping("/username/{username}")
    public User getByUsername(@PathVariable String username) {
        return userMapper.findByUsername(username);
    }


}