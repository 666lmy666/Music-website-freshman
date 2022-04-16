package com.melegant.music.dao;

import com.melegant.music.domain.Consumer;
import org.springframework.stereotype.Repository;

import java.util.List;

/*用户Dao持久层*/
@Repository
public interface ConsumerMapper {
    /*增加*/
    public int insert(Consumer consumer);

    /*修改*/
    public int update(Consumer consumer);

    /*删除*/
    public int delete(Integer consumer);

    /*根据主键查询整个对象*/
    public Consumer selectByPrimaryKey(Integer id);

    /*查询所有用户*/
    public List<Consumer> allConsumer();

    /*验证密码*/
    public int verifyPassword(String username, String password);

    /*根据账号查询用户*/
    public Consumer getByUsername(String username);

}
