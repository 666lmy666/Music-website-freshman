package com.melegant.music.dao;

import org.springframework.stereotype.Repository;


/*管理员Dao*/
@Repository
public interface AdminMapper {
    /*验证密码*/
    public int verifyPassword(String username,String password);

}