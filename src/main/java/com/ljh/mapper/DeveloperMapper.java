package com.ljh.mapper;



import com.ljh.pojo.entity.Developer;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface DeveloperMapper {

    /**
     * 添加开发者信息
     * @param developer
     */
    void save(Developer developer);

    /**
     * 根据id查找开发者
     * @param id
     * @return
     */
    Developer findById(long id);

    /**
     * 修改开发者
     * @param developer
     */
    void update(Developer developer);
}