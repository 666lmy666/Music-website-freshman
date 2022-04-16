package com.melegant.music.service.impl;

import com.melegant.music.dao.ConsumerMapper;
import com.melegant.music.domain.Consumer;
import com.melegant.music.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumerServiceImpl implements ConsumerService {
    @Autowired
    private ConsumerMapper consumerMapper;

    @Override
    public boolean insert(Consumer consumer) {
        return consumerMapper.insert(consumer)>0;
    }

    @Override
    public boolean update(Consumer consumer) {
        return consumerMapper.update(consumer)>0;
    }

    @Override
    public boolean delete(Integer consumer) {
        return consumerMapper.delete(consumer)>0;
    }

    @Override
    public Consumer selectByPrimaryKey(Integer id) {
        return consumerMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Consumer> allConsumer() {
        return consumerMapper.allConsumer();
    }

    @Override
    public boolean verifyPassword(String username, String password) {
        return consumerMapper.verifyPassword(username,password)>0;
    }

    @Override
    public Consumer getByUsername(String username) {
        return consumerMapper.getByUsername(username);
    }
}
