package me.zhouzhuo810.andcode.project.service.impl;

import me.zhouzhuo810.andcode.common.dao.BaseDao;
import me.zhouzhuo810.andcode.common.entity.BaseEntity;
import me.zhouzhuo810.andcode.common.result.BaseResult;
import me.zhouzhuo810.andcode.common.service.impl.BaseServiceImpl;
import me.zhouzhuo810.andcode.common.utils.DataUtils;
import me.zhouzhuo810.andcode.common.utils.FileUtils;
import me.zhouzhuo810.andcode.common.utils.MapUtils;
import me.zhouzhuo810.andcode.project.dao.ProjectDao;
import me.zhouzhuo810.andcode.project.entity.ProjectEntity;
import me.zhouzhuo810.andcode.project.service.ProjectService;
import me.zhouzhuo810.andcode.user.entity.UserEntity;
import me.zhouzhuo810.andcode.user.service.UserService;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by zz on 2018/6/7.
 */
@Service
public class ProjectServiceImpl extends BaseServiceImpl<ProjectEntity> implements ProjectService{

    @Resource(name = "userServiceImpl")
    private UserService mUserService;

    @Override
    @Resource(name = "projectDaoImpl")
    public void setBaseDao(BaseDao<ProjectEntity> baseDao) {
        super.setBaseDao(baseDao);
    }

    public ProjectDao getDAO() {
        return (ProjectDao) this.baseDao;
    }

    @Override
    public BaseResult addProject(String userId, String projectName, String projectSize, String projectTypeId, String developTools, String price, MultipartFile apkPath, String note) {
        UserEntity user = mUserService.get(userId);
        if (user == null) {
            return new BaseResult(0, "用户不合法！");
        }
        ProjectEntity entity = new ProjectEntity();
        entity.setProjectName(projectName);
        entity.setProjectSize(projectSize);
        entity.setProjectTypeId(projectTypeId);
        entity.setDevelopTools(developTools);
        entity.setPrice(price);
        if (apkPath != null) {
            try {
                String path = FileUtils.saveFile(apkPath.getBytes(), "apk", apkPath.getOriginalFilename());
                entity.setApkPath(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        entity.setNote(note);
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
    public BaseResult deleteProject(String userId, String id) {
        UserEntity user = mUserService.get(userId);
        if (user == null) {
            return new BaseResult(0, "用户不合法！");
        }
        ProjectEntity entity = get(id);
        if (entity == null) {
            return new BaseResult(0, "项目不存在或已被删除！");
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
    public BaseResult updateProject(String userId, String projectId, String projectName, String projectSize, String projectTypeId, String developTools, String price, MultipartFile apkPath, String note) {
        UserEntity user = mUserService.get(userId);
        if (user == null) {
            return new BaseResult(0, "用户不合法！");
        }
        ProjectEntity entity = get(projectId);
        if (entity == null) {
            return new BaseResult(0, "项目不存在或已被删除！");
        }
        if (projectName != null) {
            entity.setProjectName(projectName);
        }
        if (projectSize != null) {
            entity.setProjectSize(projectSize);
        }
        if (projectTypeId != null) {
            entity.setProjectTypeId(projectTypeId);
        }
        if (developTools != null) {
            entity.setDevelopTools(developTools);
        }
        if (price != null) {
            entity.setPrice(price);
        }
        if (apkPath != null) {
            try {
                String path = FileUtils.saveFile(apkPath.getBytes(), "apk", apkPath.getOriginalFilename());
                entity.setApkPath(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
    public BaseResult getAllProject() {
        List<ProjectEntity> actions = executeCriteria(new Criterion[]{
                Restrictions.eq("deleteFlag", BaseEntity.DELETE_FLAG_NO)
        }, Order.asc("createTime"));
        if (actions != null && actions.size() > 0) {
            List<Map<String, Object>> result = new ArrayList<>();
            for (ProjectEntity action : actions) {
                MapUtils map = new MapUtils();
                map.put("id", action.getId());
                map.put("apkPath", action.getApkPath());
                map.put("developTools", action.getDevelopTools());
                map.put("projectTypeName", action.getProjectTypeName());
                map.put("note", action.getNote());
                map.put("createTime", DataUtils.formatDate(action.getCreateTime()));
                map.put("createPerson", action.getCreateUserName());
                map.put("projectName", action.getProjectName());
                map.put("projectSize", action.getProjectSize());
                map.put("price", action.getPrice());
                map.put("projectTypeId", action.getProjectTypeId());
                result.add(map.build());
            }
            return new BaseResult(1, "ok", result);
        } else {
            return new BaseResult(1, "ok");
        }
    }

    @Override
    public BaseResult getAllProjectByType(String typeId) {
        if (typeId != null) {
            List<ProjectEntity> actions = executeCriteria(new Criterion[]{
                    Restrictions.eq("deleteFlag", BaseEntity.DELETE_FLAG_NO),
                    Restrictions.eq("projectTypeId", typeId)
            }, Order.asc("createTime"));
            if (actions != null && actions.size() > 0) {
                List<Map<String, Object>> result = new ArrayList<>();
                for (ProjectEntity action : actions) {
                    MapUtils map = new MapUtils();
                    map.put("id", action.getId());
                    map.put("apkPath", action.getApkPath());
                    map.put("developTools", action.getDevelopTools());
                    map.put("projectTypeName", action.getProjectTypeName());
                    map.put("note", action.getNote());
                    map.put("createTime", DataUtils.formatDate(action.getCreateTime()));
                    map.put("createPerson", action.getCreateUserName());
                    map.put("projectName", action.getProjectName());
                    map.put("projectSize", action.getProjectSize());
                    map.put("price", action.getPrice());
                    map.put("projectTypeId", action.getProjectTypeId());
                    result.add(map.build());
                }
                return new BaseResult(1, "ok", result);
            } else {
                return new BaseResult(1, "ok");
            }
        } else {
            List<ProjectEntity> actions = executeCriteria(new Criterion[]{
                    Restrictions.eq("deleteFlag", BaseEntity.DELETE_FLAG_NO)
            }, Order.asc("createTime"));
            if (actions != null && actions.size() > 0) {
                List<Map<String, Object>> result = new ArrayList<>();
                for (ProjectEntity action : actions) {
                    MapUtils map = new MapUtils();
                    map.put("id", action.getId());
                    map.put("apkPath", action.getApkPath());
                    map.put("developTools", action.getDevelopTools());
                    map.put("projectTypeName", action.getProjectTypeName());
                    map.put("note", action.getNote());
                    map.put("createTime", DataUtils.formatDate(action.getCreateTime()));
                    map.put("createPerson", action.getCreateUserName());
                    map.put("projectName", action.getProjectName());
                    map.put("projectSize", action.getProjectSize());
                    map.put("price", action.getPrice());
                    map.put("projectTypeId", action.getProjectTypeId());
                    result.add(map.build());
                }
                return new BaseResult(1, "ok", result);
            } else {
                return new BaseResult(1, "ok");
            }
        }
    }
}
