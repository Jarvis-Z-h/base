package com.voidstar.base.service;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.voidstar.base.annotation.Cache;
import com.voidstar.base.entity.BaseEntity;
import com.voidstar.base.entity.StarSession;
import com.voidstar.base.util.RedisUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author 邹强
 * @date 19-2-27
 */
public class BaseService <T extends BaseEntity>{

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    BaseMapper<T> baseMapper;
    @Autowired
    SqlSession session;

    /**
     * get the redis key for every entity
     *
     * @param entity
     * @return
     */
    protected String getCacheKey(T entity) {
        TableName annotation = entity.getClass().getAnnotation(TableName.class);
        String tableName = annotation.value();

        return tableName + ":" + entity.getCacheId();
    }

    /**
     * whether the entity's cache is enabled
     *
     * @param entity
     * @return
     */
    private boolean isCached(T entity) {
        Cache cache = entity.getClass().getAnnotation(Cache.class);
        return cache.value();
    }

    /**
     * save an entity
     * save the redis cache when its cache is on
     *
     * @param entity
     * @return
     */
    public T insert(T entity) {
        try {
            entity.setCreatedTime(LocalDateTime.now());
            entity.setUpdatedTime(LocalDateTime.now());
            baseMapper.insert(entity);

            if (isCached(entity)) {
                redisUtil.set(getCacheKey(entity), entity, 65536);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            if (!isCached(entity)) {
                redisUtil.del(getCacheKey(entity));
            }
        }

        return entity;
    }

    /**
     * delete an entity by id
     * delete redis cache
     *
     * @param id
     * @return
     */
    public Integer delete(StarSession session, Integer id) {
        Integer res = baseMapper.deleteById(id);
        T entity = getById(id);
        if (isCached(entity)) {
            redisUtil.del(getCacheKey(entity));
        }

        return res;
    }

    /**
     * delete by conditions
     *
     * @param conditionValues
     * @return
     */
    public Integer delete(Map<String, Object> conditionValues) {
        return baseMapper.deleteByMap(conditionValues);
    }

    /**
     * update an entity
     *
     * @param entity
     * @return
     */
    public T update(T entity) {
        entity.setUpdatedTime(LocalDateTime.now());
        baseMapper.updateAllColumnById(entity);
        return entity;
    }

    /**
     * get entity by id
     *
     * @param id
     * @return
     */
    public T getById(Integer id) {
        return baseMapper.selectById(id);
    }

    /**
     *  get entity by condition
     *
     * @param conditions
     * @return
     */
    public T getByMap(Map<String, Object> conditions) {
        List<T> entities = baseMapper.selectByMap(conditions);
        return entities.get(0);
    }

    /**
     * find entities by conditions
     *
     * @param conditions
     * @return
     */
    public List<T> findByConditions(Map<String, Object> conditions) {
        return baseMapper.selectByMap(conditions);
    }
}
