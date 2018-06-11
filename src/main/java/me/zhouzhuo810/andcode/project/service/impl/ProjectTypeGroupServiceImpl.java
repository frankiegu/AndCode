package me.zhouzhuo810.andcode.project.service.impl;

import me.zhouzhuo810.andcode.common.dao.BaseDao;
import me.zhouzhuo810.andcode.common.entity.BaseEntity;
import me.zhouzhuo810.andcode.common.result.BaseResult;
import me.zhouzhuo810.andcode.common.service.impl.BaseServiceImpl;
import me.zhouzhuo810.andcode.common.utils.MapUtils;
import me.zhouzhuo810.andcode.project.dao.ProjectTypeGroupDao;
import me.zhouzhuo810.andcode.project.entity.ProjectTypeEntity;
import me.zhouzhuo810.andcode.project.entity.ProjectTypeGroupEntity;
import me.zhouzhuo810.andcode.project.service.ProjectTypeGroupService;
import me.zhouzhuo810.andcode.project.service.ProjectTypeService;
import me.zhouzhuo810.andcode.user.entity.UserEntity;
import me.zhouzhuo810.andcode.user.service.UserService;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by zz on 2018/6/8.
 */
@Service
public class ProjectTypeGroupServiceImpl extends BaseServiceImpl<ProjectTypeGroupEntity> implements ProjectTypeGroupService {

    @Resource(name = "userServiceImpl")
    private UserService mUserService;
    @Resource(name = "projectTypeServiceImpl")
    private ProjectTypeService mProjectTypeService;

    @Override
    @Resource(name = "projectTypeGroupDaoImpl")
    public void setBaseDao(BaseDao<ProjectTypeGroupEntity> baseDao) {
        this.baseDao = baseDao;
    }


    @Override
    public ProjectTypeGroupDao getBaseDao() {
        return (ProjectTypeGroupDao) this.baseDao;
    }

    @Override
    public BaseResult addProjectTypeGroup(String userId, String groupName, String pid) {
        UserEntity user = mUserService.get(userId);
        if (user == null) {
            return new BaseResult(0, "用户不合法！");
        }
        ProjectTypeGroupEntity entity = new ProjectTypeGroupEntity();
        entity.setGroupName(groupName);
        entity.setPid(pid);
        entity.setCreateUserID(user.getId());
        entity.setCreateUserName(user.getName());
        try {
            save(entity);
            return new BaseResult(1, "添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResult(0, "添加失败！");
        }
    }

    @Override
    public BaseResult deleteProjectTypeGroup(String userId, String id) {
        UserEntity user = mUserService.get(userId);
        if (user == null) {
            return new BaseResult(0, "用户不合法！");
        }
        ProjectTypeGroupEntity entity = get(id);
        if (entity == null) {
            return new BaseResult(0, "类型不存在或已被删除！");
        }
        entity.setModifyUserID(user.getId());
        entity.setModifyUserName(user.getName());
        entity.setModifyTime(new Date());
        entity.setDeleteFlag(BaseEntity.DELETE_FLAG_YES);
        try {
            update(entity);
            return new BaseResult(1, "删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResult(0, "删除失败！");
        }
    }

    @Override
    public BaseResult updateProjectTypeGroup(String userId, String groupId, String groupName, String pid) {
        UserEntity user = mUserService.get(userId);
        if (user == null) {
            return new BaseResult(0, "用户不合法！");
        }
        ProjectTypeGroupEntity entity = get(groupId);
        if (entity == null) {
            return new BaseResult(0, "类型不存在或已被删除！");
        }
        if (groupName != null) {
            entity.setGroupName(groupName);
        }
        if (pid != null) {
            entity.setPid(pid);
        }
        entity.setModifyUserID(user.getId());
        entity.setModifyUserName(user.getName());
        entity.setModifyTime(new Date());
        try {
            update(entity);
            return new BaseResult(1, "更新成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResult(0, "更新失败！");
        }

    }

    @Override
    public BaseResult getAllProjectTypeGroup(String pid) {
        List<Map<String, Object>> result = new ArrayList<>();

        List<ProjectTypeGroupEntity> groups = executeCriteria(new Criterion[]{
                Restrictions.eq("deleteFlag", BaseEntity.DELETE_FLAG_NO),
                Restrictions.eq("pid", pid)
        }, Order.asc("createTime"));
        if (groups != null) {
            for (ProjectTypeGroupEntity group : groups) {
                String cPid = group.getId();
                String groupName = group.getGroupName();
                Integer typeCount = group.getTypeCount();
                Integer childCount = group.getChildCount();
                MapUtils groupMap = new MapUtils();
                groupMap.put("groupId", cPid);
                groupMap.put("groupName", groupName);
                groupMap.put("pid", pid);
                List<ProjectTypeEntity> types = mProjectTypeService.executeCriteria(new Criterion[]{
                        Restrictions.eq("deleteFlag", BaseEntity.DELETE_FLAG_NO),
                        Restrictions.eq("groupId", cPid)
                }, Order.asc("createTime"));
                if (types != null) {
                    List<Map<String, Object>> typeList = new ArrayList<>();
                    for (ProjectTypeEntity type : types) {
                        MapUtils mapType = new MapUtils();
                        mapType.put("typeName", type.getTypeName());
                        mapType.put("typeId", type.getId());
                        typeList.add(mapType.build());
                    }
                    groupMap.put("types", typeList);
                }
                List<Map<String, Object>> cResult = new ArrayList<>();
                groupMap.put("childs", getChlidProjectGroupAndType(cPid, cResult));
                result.add(groupMap.build());
            }
        }
        return new BaseResult(1, "ok", result);
    }

    @Override
    public BaseResult getAllProjectTypeGroupOnly(String pid) {
        List<ProjectTypeGroupEntity> groups = executeCriteria(new Criterion[]{
                Restrictions.eq("deleteFlag", BaseEntity.DELETE_FLAG_NO),
                Restrictions.eq("pid", pid)
        }, Order.asc("createTime"));
        List<Map<String, Object>> result = new ArrayList<>();
        if (groups != null) {
            for (ProjectTypeGroupEntity group : groups) {
                MapUtils groupMap = new MapUtils();
                groupMap.put("groupId", group.getId());
                groupMap.put("groupName", group.getGroupName());
                groupMap.put("pid", group.getPid());
                result.add(groupMap.build());
            }
        }
        return new BaseResult(1, "ok", result);
    }

    private List<Map<String, Object>> getChlidProjectGroupAndType(String pid, List<Map<String, Object>> result) {
        List<ProjectTypeGroupEntity> groups = executeCriteria(new Criterion[]{
                Restrictions.eq("deleteFlag", BaseEntity.DELETE_FLAG_NO),
                Restrictions.eq("pid", pid)
        }, Order.asc("createTime"));
        if (groups != null) {
            for (ProjectTypeGroupEntity group : groups) {
                String cPid = group.getId();
                String groupName = group.getGroupName();
                Integer typeCount = group.getTypeCount();
                Integer childCount = group.getChildCount();
                MapUtils groupMap = new MapUtils();
                groupMap.put("groupId", cPid);
                groupMap.put("groupName", groupName);
                groupMap.put("pid", pid);
                List<ProjectTypeEntity> types = mProjectTypeService.executeCriteria(new Criterion[]{
                        Restrictions.eq("deleteFlag", BaseEntity.DELETE_FLAG_NO),
                        Restrictions.eq("groupId", cPid)
                }, Order.asc("createTime"));
                if (types != null) {
                    List<Map<String, Object>> typeList = new ArrayList<>();
                    for (ProjectTypeEntity type : types) {
                        MapUtils mapType = new MapUtils();
                        mapType.put("typeName", type.getTypeName());
                        mapType.put("typeId", type.getId());
                        typeList.add(mapType.build());
                    }
                    groupMap.put("types", typeList);
                }
                List<Map<String, Object>> cResult = new ArrayList<>();
                groupMap.put("childs", getChlidProjectGroupAndType(cPid, cResult));
                result.add(groupMap.build());
            }
        }
        return result;
    }


}
