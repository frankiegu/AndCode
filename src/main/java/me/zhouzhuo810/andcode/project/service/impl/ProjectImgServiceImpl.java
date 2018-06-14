package me.zhouzhuo810.andcode.project.service.impl;

import me.zhouzhuo810.andcode.common.dao.BaseDao;
import me.zhouzhuo810.andcode.common.dao.impl.BaseDaoImpl;
import me.zhouzhuo810.andcode.common.entity.BaseEntity;
import me.zhouzhuo810.andcode.common.result.BaseResult;
import me.zhouzhuo810.andcode.common.service.impl.BaseServiceImpl;
import me.zhouzhuo810.andcode.common.utils.DataUtils;
import me.zhouzhuo810.andcode.common.utils.FileUtils;
import me.zhouzhuo810.andcode.common.utils.MapUtils;
import me.zhouzhuo810.andcode.project.dao.ProjectImgDao;
import me.zhouzhuo810.andcode.project.entity.ProjectImageEntity;
import me.zhouzhuo810.andcode.project.entity.ProjectImageEntity;
import me.zhouzhuo810.andcode.project.entity.ProjectImageEntity;
import me.zhouzhuo810.andcode.project.service.ProjectImgService;
import me.zhouzhuo810.andcode.user.entity.UserEntity;
import me.zhouzhuo810.andcode.user.service.UserService;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zz on 2018/6/7.
 */
@Service
public class ProjectImgServiceImpl extends BaseServiceImpl<ProjectImageEntity> implements ProjectImgService {

    @Resource(name = "userServiceImpl")
    private UserService mUserService;

    @Override
    public ProjectImgDao getBaseDao() {
        return (ProjectImgDao) this.baseDao;
    }

    @Override
    @Resource(name = "projectImgDaoImpl")
    public void setBaseDao(BaseDao<ProjectImageEntity> dao) {
        this.baseDao = dao;
    }

    @Override
    public BaseResult addProjectImg(String userId, String projectId, String title, String content, Integer position, MultipartFile imgFile) {
        UserEntity user = mUserService.get(userId);
        if (user == null) {
            return new BaseResult(0, "用户不合法！");
        }
        ProjectImageEntity entity = new ProjectImageEntity();
        if (projectId != null) {
            entity.setProjectId(projectId);
        }
        if (title != null) {
            entity.setTitle(title);
        }
        if (content != null) {
            entity.setContent(content);
        }
        if (position != null) {
            entity.setPosition(position);
        }
        if (imgFile != null) {
            try {
                String path = FileUtils.saveFile(imgFile.getBytes(), "img", imgFile.getOriginalFilename());
                entity.setImgPath("img"+ File.separator+imgFile.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
    public BaseResult updateProjectImg(String userId, String imgId, String projectId, String title, String content, Integer position, MultipartFile imgFile) {
        UserEntity user = mUserService.get(userId);
        if (user == null) {
            return new BaseResult(0, "用户不合法！");
        }
        ProjectImageEntity entity = get(imgId);
        if (entity == null) {
            return new BaseResult(0, "类型不存在或已被删除！");
        }
        if (projectId != null) {
            entity.setProjectId(projectId);
        }
        if (title != null) {
            entity.setTitle(title);
        }
        if (content != null) {
            entity.setContent(content);
        }
        if (position != null) {
            entity.setPosition(position);
        }
        if (imgFile != null) {
            try {
                String path = FileUtils.saveFile(imgFile.getBytes(), "img", imgFile.getOriginalFilename());
                entity.setImgPath("img"+File.separator+imgFile.getOriginalFilename());
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
    public BaseResult deleteProjectImg(String userId, String id) {
        UserEntity user = mUserService.get(userId);
        if (user == null) {
            return new BaseResult(0, "用户不合法！");
        }
        ProjectImageEntity entity = get(id);
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
    public BaseResult getAllProjectImg(String projectId) {
        List<ProjectImageEntity> actions = executeCriteria(new Criterion[]{
                Restrictions.eq("deleteFlag", BaseEntity.DELETE_FLAG_NO),
                Restrictions.eq("projectId", projectId)
        }, Order.asc("createTime"));
        if (actions != null && actions.size() > 0) {
            List<Map<String, Object>> result = new ArrayList<>();
            for (ProjectImageEntity action : actions) {
                MapUtils map = new MapUtils();
                map.put("id", action.getId());
                map.put("imgPath", action.getImgPath());
                map.put("imgSize", action.getImgSize());
                map.put("title", action.getTitle());
                map.put("content", action.getContent());
                map.put("position", action.getPosition());
                map.put("projectId", action.getProjectId());
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
