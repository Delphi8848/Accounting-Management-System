package com.gnu.cash.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gnu.cash.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    User findByUsername(@Param("username") String username);

    int updatePassword(
        @Param("id") Integer id,
        @Param("newPassword") String newPassword);

    @Update("UPDATE users SET status = #{status} WHERE id = #{userid}")
    int updateStatus(@Param("userid") Integer userid, @Param("status") String status);

}