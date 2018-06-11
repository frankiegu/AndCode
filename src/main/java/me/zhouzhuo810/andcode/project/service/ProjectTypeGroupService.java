package me.zhouzhuo810.andcode.project.service;

import me.zhouzhuo810.andcode.common.result.BaseResult;
import me.zhouzhuo810.andcode.common.service.BaseService;
import me.zhouzhuo810.andcode.project.entity.ProjectTypeGroupEntity;

/**
 * Created by zz on 2018/6/8.
 */
public interface ProjectTypeGroupService extends BaseService<ProjectTypeGroupEntity> {

    BaseResult addProjectTypeGroup(String userId, String groupName, String pid);

    BaseResult deleteProjectTypeGroup(String userId, String id);

    BaseResult updateProjectTypeGroup(String userId, String groupId,
                                      String groupName, String pid);

    BaseResult getAllProjectTypeGroup(String pid);

    BaseResult getAllProjectTypeGroupOnly(String pid);
}
