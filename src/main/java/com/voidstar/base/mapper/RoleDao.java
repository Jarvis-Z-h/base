package com.voidstar.base.mapper;

import com.voidstar.base.entity.Role;
import com.voidstar.base.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/*
* 查询角色列表的Dao层
*
* */
@Mapper
public interface RoleDao {

    //添加一种角色
    @Insert("insert into role(id,roleName) values (#{id},#{roleName})")
    public int addRole(Role role);

    //通过Id查询一种角色
    @Select("select roleName from role where id=#{id}")
    public Role getRoleById(Integer id);

    //得到角色列表
    @Select("select id,roleName from role")
    public List<Role> getRoleList();

    //删除一种角色
    @Delete("delete from role where id={id}")
    public void deleteRoleById(Integer id);

}
