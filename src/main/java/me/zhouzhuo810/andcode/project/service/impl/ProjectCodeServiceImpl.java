package me.zhouzhuo810.andcode.project.service.impl;

import me.zhouzhuo810.andcode.common.dao.BaseDao;
import me.zhouzhuo810.andcode.common.entity.BaseEntity;
import me.zhouzhuo810.andcode.common.result.BaseResult;
import me.zhouzhuo810.andcode.common.service.impl.BaseServiceImpl;
import me.zhouzhuo810.andcode.common.utils.DataUtils;
import me.zhouzhuo810.andcode.common.utils.MapUtils;
import me.zhouzhuo810.andcode.project.dao.ProjectCodeDao;
import me.zhouzhuo810.andcode.project.dao.ProjectDao;
import me.zhouzhuo810.andcode.project.entity.ProjectCodeEntity;
import me.zhouzhuo810.andcode.project.entity.ProjectEntity;
import me.zhouzhuo810.andcode.project.entity.ProjectCodeEntity;
import me.zhouzhuo810.andcode.project.entity.ProjectTypeEntity;
import me.zhouzhuo810.andcode.project.service.ProjectCodeService;
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
 * Created by zz on 2018/8/16.
 */
@Service
public class ProjectCodeServiceImpl extends BaseServiceImpl<ProjectCodeEntity> implements ProjectCodeService {
    @Resource(name = "userServiceImpl")
    private UserService mUserService;
    @Override
    @Resource(name = "projectCodeDaoImpl")
    public void setBaseDao(BaseDao<ProjectCodeEntity> baseDao) {
        super.setBaseDao(baseDao);
    }

    public ProjectCodeDao getDAO() {
        return (ProjectCodeDao) this.baseDao;
    }

    @Override
    public BaseResult addProjectCode(String userId, String projectId, String name, String path) {
        UserEntity user = mUserService.get(userId);
        if (user == null) {
            return new BaseResult(0, "用户不合法！");
        }
        ProjectCodeEntity entity = new ProjectCodeEntity();
        entity.setProjectId(projectId);
        entity.setName(name);
        entity.setPath(path);
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
    public BaseResult deleteProjectCode(String userId, String id) {
        UserEntity user = mUserService.get(userId);
        if (user == null) {
            return new BaseResult(0, "用户不合法！");
        }
        ProjectCodeEntity entity = get(id);
        if (entity == null) {
            return new BaseResult(0, "代码不存在或已被删除！");
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
    public BaseResult updateProjectCode(String userId, String codeId, String name, String path) {
        UserEntity user = mUserService.get(userId);
        if (user == null) {
            return new BaseResult(0, "用户不合法！");
        }
        ProjectCodeEntity entity = get(codeId);
        if (entity == null) {
            return new BaseResult(0, "代码不存在或已被删除！");
        }
        if (name != null) {
            entity.setName(name);
        }
        if (path != null) {
            entity.setPath(path);
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
    public BaseResult getAllCodeByProjectId(String userId, String projectId) {
        List<ProjectCodeEntity> actions = executeCriteria(new Criterion[]{
                Restrictions.eq("deleteFlag", BaseEntity.DELETE_FLAG_NO),
                Restrictions.eq("projectId", projectId)
        }, Order.asc("createTime"));
        if (actions != null && actions.size() > 0) {
            List<Map<String, Object>> result = new ArrayList<>();
            for (ProjectCodeEntity action : actions) {
                MapUtils map = new MapUtils();
                map.put("codeId", action.getId());
                map.put("name", action.getName());
                map.put("path", action.getPath());
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
