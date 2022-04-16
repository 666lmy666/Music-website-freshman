package com.melegant.music.domain;

/*管理员*/

import lombok.Data;

//public class Admin implements Serializable
@Data
public class Admin {
    /*主键*/
    private Integer id;
    private String name;
    private String password;

}
