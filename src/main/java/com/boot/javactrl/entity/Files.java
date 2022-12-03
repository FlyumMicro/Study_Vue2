package com.boot.javactrl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: Zhang Yuyue
 * @Date: 2022/11/5 1:06
 * @Project com.javaCtrl
 **/
@Data
@TableName("sys_file")
public class Files {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String type;
    private long size;
    private String url;
    private String md5;
    private Boolean isDelete;
    private Boolean enable;


}
