package com.voidstar.base.mapper;

import com.voidstar.base.entity.Role;
import com.voidstar.base.entity.UserRole;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserRoleDao {
    //添加一个员工对应一种角色
    @Insert("insert into user_role(userId,roleId) values(#{userId},#{roleId})")
    public int addUserRole(UserRole userRole);

    //通过Id查询一个用户对应的角色名称
    @Select("select r.roleName,ur.roleId from role r join user_role ur on r.userId=ur.userId where ur.userId=#{id}")
    public List<Role> getUserRoleById(Integer id);

    //修改一个用户的角色
    @Update("update user_role set roleId=#{roleId} where userId=#{userId}")
    public void editUserRole(UserRole userRole);

    //删除一个用户，也对应他的角色
    @Delete("delete from user_role where userId=#{userId}")
    public void deleteUserRole(Integer userId);

}
