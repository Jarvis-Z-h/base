package com.voidstar.base.mapper;

import com.voidstar.base.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao {
    //通过名字查询一个用户
    @Select("select id,name,gender,code,telephone,createdTime,updatedTime,status from user where name=#{name}")
    public User getUserByName(String name);

    //添加一个用户
    @Insert("insert into user(id,code,name,password,status,gender,telephone,createdTime,updatedTime) values(#{id},#{code},#{name},#{password},#{status},#{gender},#{telephone},#{createdTime},#{updatedTime})")
    public int addUser(User user);

    //通过Id查询一个用户(工号，名字，角色,性别，code,联系方式，创建时间，修改时间，状态值，)
    @Select("select id,password,name,gender,code,telephone,createdTime,updatedTime,status from user where id=#{id}")
    public User getUserById(Integer id);

    //得到用户列表(工号，名字，性别，code,联系方式，创建时间，修改时间，状态值，角色)
    @Select("select id,name,gender,code,telephone,createdTime,updatedTime,status from user")
    public List<User> getUserList();

    //修改一个用户(除Id不能修改，全部信息能)
    @Update("update user set code=#{code},name=#{name},gender=#{gender},status=#{status},telephone=#{telephone},updatedTime=#{updatedTime} where id=#{id}")
    public void editUser(User mgrUser);

    //修改一个用户的密码
    @Update("update user set password=#{password},updatedTime=#{updatedTime} where id=#{id}")
    public void editUserPassword(User user);

    //删除一个用户
    @Delete("delete from user where id=#{id}")
    public void deleteUserById(Integer id);

    //删除一个用户
    @Delete("delete from user where code=#{code}")
    public void deleteUserByCode(String code);

    //搜索一个用户(通过他的code值)
    @Select("select id,name,gender,code,telephone,createdTime,updatedTime,status from user where code=#{code}")
    public User getUserByCode(String code);
}
