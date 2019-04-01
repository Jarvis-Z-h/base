package com.voidstar.base.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigInteger;

/**
 * @author 邹强
 * @date 19-3-13
 */
@Data
public class Permission extends BaseEntity {
//权限名字
    private String permission;
//角色id
    private Integer roleId;
}