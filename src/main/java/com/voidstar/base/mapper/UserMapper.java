package com.voidstar.base.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.voidstar.base.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 邹强
 * @date 19-2-28
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
