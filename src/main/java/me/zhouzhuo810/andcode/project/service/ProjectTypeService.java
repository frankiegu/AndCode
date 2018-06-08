package me.zhouzhuo810.andcode.project.service;

import me.zhouzhuo810.andcode.common.result.BaseResult;
import me.zhouzhuo810.andcode.common.service.BaseService;
import me.zhouzhuo810.andcode.project.entity.ProjectTypeEntity;

/**
 * Created by zz on 2018/6/7.
 */
public interface ProjectTypeService  extends BaseService<ProjectTypeEntity> {

    BaseResult addProjectType(String userId, String typeName, String groupId);

    BaseResult deleteProjectType(String userId, String id);

    BaseResult updateProjectType(String userId, String typeId, String typeName, String groupId);

    BaseResult getAllProjectType();

}
