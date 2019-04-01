package com.voidstar.base.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigInteger;
import java.sql.Time;
import java.time.LocalDateTime;

/**
 * @author 邹强
 * @date 19-2-27
 */
@Data
public class BaseEntity {

    @Id
    private int id;

    @TableField(value = "createdTime")
    private LocalDateTime createdTime;

    @TableField(value = "updatedTime")
    private LocalDateTime updatedTime;

    public String getCacheId() {
        return Integer.toString(id);
    }

//    public void setId(int id) {
//        this.id = new Integer(id);
//    }
//
//    public Integer getId() {
//        return this.id;
//    }
//
//    public void setCreatedTime(LocalDateTime time) {
//        this.createdTime = time;
//    }
//
//    public void setUpdatedTime(LocalDateTime time) {
//        this.updatedTime = time;
//    }
}