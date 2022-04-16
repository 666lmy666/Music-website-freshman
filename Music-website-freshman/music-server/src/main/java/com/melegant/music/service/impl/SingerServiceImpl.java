package com.melegant.music.service.impl;

import com.melegant.music.dao.SingerMapper;
import com.melegant.music.domain.Singer;
import com.melegant.music.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*歌手service实现类*/
@Service
public class SingerServiceImpl implements SingerService {

    @Autowired
    private SingerMapper singerMapper;

    /*增加*/
    @Override
    public boolean insert(Singer singer) {
        return singerMapper.insert(singer)>0;
    }
    /*修改*/
    @Override
    public boolean update(Singer singer) {
        return singerMapper.update(singer)>0;
    }
    /*删除*/
    @Override
    public boolean delete(Integer id) {
        return singerMapper.delete(id)>0;
    }
    /*根据主键查询整个对象*/
    @Override
    public Singer selectByPrimaryKey(Integer id) {
        return singerMapper.selectByPrimaryKey(id);
    }
    /*查询所有歌手*/
    @Override
    public List<Singer> allSinger() {
        return singerMapper.allSinger();
    }
    /*根据歌手名字模糊查询列表*/
    @Override
    public List<Singer> singerOfName(String name) {
        return singerMapper.singerOfName(name);
    }
    /*根据性别查询歌手*/
    @Override
    public List<Singer> singerOfSex(Integer sex) {
        return singerMapper.singerOfSex(sex);
    }
}
