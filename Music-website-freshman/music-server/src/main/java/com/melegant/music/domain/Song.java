package com.melegant.music.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/*歌曲*/
@Data
public class Song implements Serializable {
    /*主键*/
    private Integer id;
    private Integer singerId;
    private String name;
    private String introduction;
    private Date createTime;
    private Date updateTime;
    private String pic;
    private String lyric;
    private String url;
}
