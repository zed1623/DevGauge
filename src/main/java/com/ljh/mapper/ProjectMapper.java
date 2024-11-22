package com.ljh.mapper;

import com.ljh.pojo.entity.Project;
import com.ljh.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProjectMapper {

    /**
     * 保存查询的仓库信息
     * @param projectDetails
     */
    void insert(Project projectDetails);

    /**
     * 根据仓库id查询仓库信息
     * @param id
     * @return
     */
    Project findById(Long id);

    /**
     * 修改仓库信息
     * @param projectDetails
     */
    void update(Project projectDetails);
}
