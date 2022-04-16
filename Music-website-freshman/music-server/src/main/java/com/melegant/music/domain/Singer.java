package com.melegant.music.domain;

import lombok.Data;

import java.util.Date;

import java.io.Serializable;

/*管理员*/
@Data
public class Singer implements Serializable {
    /*主键*/
    private Integer id;
    private String name;
    private Byte sex;
    private String pic;
    private Date birth;
    private String location;
    private String introduction;

}
