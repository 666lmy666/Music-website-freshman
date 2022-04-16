package com.melegant.music.domain;

import lombok.Data;

import java.io.Serializable;


/*歌单*/
@Data
public class SongList implements Serializable {
    /*主键*/
    private Integer id;
    /*歌单标题*/
    private String title;
    /*歌单图片*/
    private String pic;
    /*歌单简介*/
    private String introduction;
    /*歌单风格*/
    private String style;
}
