package me.zhouzhuo810.andcode.project.service.impl;

import me.zhouzhuo810.andcode.common.dao.BaseDao;
import me.zhouzhuo810.andcode.common.entity.BaseEntity;
import me.zhouzhuo810.andcode.common.result.BaseResult;
import me.zhouzhuo810.andcode.common.service.impl.BaseServiceImpl;
import me.zhouzhuo810.andcode.common.utils.DataUtils;
import me.zhouzhuo810.andcode.common.utils.MapUtils;
import me.zhouzhuo810.andcode.project.dao.ProjectTypeDao;
import me.zhouzhuo810.andcode.project.entity.ProjectTypeEntity;
import me.zhouzhuo810.andcode.project.entity.ProjectTypeEntity;
import me.zhouzhuo810.andcode.project.service.ProjectTypeService;
import me.zhouzhuo810.andcode.user.entity.UserEntity;
import me.zhouzhuo810.andcode.user.service.UserService;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zz on 2018/6/7.
 */
@Service
public class ProjectTypeServiceImpl extends BaseServiceImpl<ProjectTypeEntity> implements ProjectTypeService {

    @Resource(name = "userServiceImpl")
    private UserService mUserService;

    @Override
    @Resource(name = "projectTypeDaoImpl")
    public void setBaseDao(BaseDao<ProjectTypeEntity> baseDao) {
        this.baseDao = baseDao;
    }

    public ProjectTypeDao getDao() {
        return (ProjectTypeDao) this.baseDao;
    }

    @Override
    public BaseResult addProjectType(String userId, String typeName, String groupId) {
        UserEntity user = mUserService.get(userId);
        if (user == null) {
            return new BaseResult(0, "用户不合法！");
        }
        ProjectTypeEntity entity = new ProjectTypeEntity();
        entity.setTypeName(typeName);
        entity.setGroupId(groupId);
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
    public BaseResult deleteProjectType(String userId, String id) {
        UserEntity user = mUserService.get(userId);
        if (user == null) {
            return new BaseResult(0, "用户不合法！");
        }
        ProjectTypeEntity entity = get(id);
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
    public BaseResult updateProjectType(String userId, String typeId, String typeName, String groupId) {
        UserEntity user = mUserService.get(userId);
        if (user == null) {
            return new BaseResult(0, "用户不合法！");
        }
        ProjectTypeEntity entity = get(typeId);
        if (entity == null) {
            return new BaseResult(0, "类型不存在或已被删除！");
        }
        if (typeName != null) {
            entity.setTypeName(typeName);
        }
        if (groupId != null) {
            entity.setGroupId(groupId);
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
    public BaseResult getAllProjectType(String groupId) {
        List<ProjectTypeEntity> actions = executeCriteria(new Criterion[]{
                Restrictions.eq("deleteFlag", BaseEntity.DELETE_FLAG_NO),
                Restrictions.eq("groupId", groupId)
        }, Order.asc("createTime"));
        if (actions != null && actions.size() > 0) {
            List<Map<String, Object>> result = new ArrayList<>();
            for (ProjectTypeEntity action : actions) {
                MapUtils map = new MapUtils();
                map.put("id", action.getId());
                map.put("typeName", action.getTypeName());
                map.put("groupId", action.getGroupId());
                map.put("createTime", DataUtils.formatDate(action.getCreateTime()));
                map.put("createPerson", action.getCreateUserName());
                result.add(map.build());
            }
            return new BaseResult(1, "ok", result);
        } else {
            return new BaseResult(1, "ok");
        }
    }
}
