package com.gnu.cash.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("users")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    @TableField("created")
    private Date created;

    @TableField("avatar")
    private String avatar;

    private String phone;

    @TableField("user_role")
    private String userRole;

    private String email;

    private String gender;

    private String timezone;

    private String  status;


}