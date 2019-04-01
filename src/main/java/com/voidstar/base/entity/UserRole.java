package com.voidstar.base.entity;

import lombok.Data;

//这是用户--角色的中间表
@Data
public class UserRole {
    //用户的工号
    private Integer userId;
    //该用户对应的角色的id
    private Integer roleId;
}
