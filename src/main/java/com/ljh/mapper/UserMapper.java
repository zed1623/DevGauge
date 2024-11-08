package com.ljh.mapper;

import com.ljh.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    /**
     * 添加用户信息到user表
     * @param user
     */
    void save(User user);

    /**
     * 根据id查找用户信息
     * @param id
     * @return
     */
    User findById(Long id);

    /**
     *
     * @param newUser
     */
    void updateUser(User newUser);
}
