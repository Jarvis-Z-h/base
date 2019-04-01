package com.voidstar.base.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.data.annotation.Id;

import java.math.BigInteger;
import java.util.List;

/**
 * @author 邹强
 * @date 19-3-13
 */
@Data
public class Role extends BaseEntity {
    //角色名字
    private String roleName;

    @TableField(exist = false)
    private List<Permission> permissions;
}
