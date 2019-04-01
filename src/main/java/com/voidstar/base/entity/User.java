package com.voidstar.base.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.voidstar.base.annotation.Cache;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 邹强
 * @date 19-2-27
 */
@Data
@Cache(true)
@TableName("user")
public class User extends BaseEntity {
    //用户code
    private String code;
    //用户姓名
    private String name;
    //用户密码
    private String password;
    //用户角色，一个用户可能对应多种角色    @TableField(exist = false) 表明User这个实体类对应数据库的表字段不存在
    @TableField(exist = false)
    private List<Role> roles;
    //用户性别    0是女    1是男
    private String gender;
    //用户的联系方式
    private String telephone;
    //用户状态值
    private Integer status;

}
