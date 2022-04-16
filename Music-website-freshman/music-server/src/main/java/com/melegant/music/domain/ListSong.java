package com.melegant.music.domain;

import lombok.Data;

import java.io.Serializable;

/*
* 歌单中的歌曲
* 这其实是中间表，顾名思义既联系了歌单又联系了歌曲，所以要连接两张表
* */
@Data
public class ListSong implements Serializable {

    private Integer id;

    private Integer songId;

    private Integer songListId;
}
