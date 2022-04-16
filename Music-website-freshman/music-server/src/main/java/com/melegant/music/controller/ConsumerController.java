package com.melegant.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.melegant.music.domain.Consumer;
import com.melegant.music.service.ConsumerService;
import com.melegant.music.service.impl.ConsumerServiceImpl;
import com.melegant.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
@Controller
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired
    private ConsumerService consumerService;
    /*增加前端用户*/
    @ResponseBody
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addConsumer(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        String sex = request.getParameter("sex").trim();
        String phoneNum = request.getParameter("phoneNum").trim();
        String email = request.getParameter("email").trim();
        String birth = request.getParameter("birth").trim();
        String introduction = request.getParameter("introduction").trim();
        String location = request.getParameter("location").trim();
        String avator = request.getParameter("avator").trim();

        if (username == null || username.equals("")){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"用户名不能为空");
            return jsonObject;
        }
        if (password == null || password.equals("")){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"密码不能为空");
            return jsonObject;
        }
        /*把生日转换成Date格式*/
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = new Date();
        try {
            birthDate = dateFormat.parse(birth);
        }catch (ParseException e){
            e.printStackTrace();
        }
        //保存到前端用户的对象中
        Consumer consumer = new Consumer();
        consumer.setUsername(username);
        consumer.setPassword(password);
        consumer.setSex(new Byte(sex));
        consumer.setPhoneNum(phoneNum);
        consumer.setEmail(email);
        consumer.setBirth(birthDate);
        consumer.setIntroduction(introduction);
        consumer.setLocation(location);
        consumer.setAvator(avator);
        boolean flag = consumerService.insert(consumer);
        if (flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"添加成功");
        }else {
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"添加失败");
        }
        return jsonObject;
    }

    /*修改前端用户*/
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object updateConsumer(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        String sex = request.getParameter("sex").trim();
        String phoneNum = request.getParameter("phoneNum").trim();
        String email = request.getParameter("email").trim();
        String birth = request.getParameter("birth").trim();
        String introduction = request.getParameter("introduction").trim();
        String location = request.getParameter("location").trim();
        /*把生日转换成Date格式*/
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = new Date();
        try {
            birthDate = dateFormat.parse(birth);
        }catch (ParseException e){
            e.printStackTrace();
        }
        //保存到前端用户的对象中
        Consumer consumer = new Consumer();
        consumer.setId(Integer.parseInt(id));
        consumer.setUsername(username);
        consumer.setPassword(password);
        consumer.setSex(new Byte(sex));
        consumer.setPhoneNum(phoneNum);
        consumer.setEmail(email);
        consumer.setBirth(birthDate);
        consumer.setIntroduction(introduction);
        consumer.setLocation(location);
        boolean flag = consumerService.update(consumer);
        if (flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"修改成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"修改失败");
        return jsonObject;
    }

    /*删除前端用户*/
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Object deleteConsumer(HttpServletRequest request){
        String id = request.getParameter("id").trim();
        return consumerService.delete(Integer.parseInt(id));
    }

    /*根据主键查询整个对象*/
    @RequestMapping(value = "/selectByPrimaryKey",method = RequestMethod.GET)
    public Object selectByPrimaryKey(HttpServletRequest request){
        String id = request.getParameter("id").trim();
        return consumerService.selectByPrimaryKey(Integer.parseInt(id));
    }

    /*查询所有前端用户*/
    @RequestMapping(value = "/allConsumer",method = RequestMethod.GET)
    public Object allConsumer(HttpServletRequest request){
        return consumerService.allConsumer();
    }

    /*更新前端用户图片*/
    @RequestMapping(value = "/updateConsumerPic",method = RequestMethod.POST)
    public Object updateConsumerPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id") int id){
        JSONObject jsonObject = new JSONObject();
        if (avatorFile.isEmpty()) {
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败");
            return jsonObject;
        }
        /*避免同一时间上传多张文件*/
        String fileName = System.currentTimeMillis()+avatorFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"avatorImages";
        /*如果文件路径不存在，新增该路径*/
        File file1 = new File(filePath);
        if (!file1.exists()){
            file1.mkdir();
        }
        /*实际的文件位置*/
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        /*存储到数据库里的相对文件地址*/
        String storeAvatorPath = "/avatorImages/"+fileName;
        try {
            avatorFile.transferTo(dest);
            Consumer consumer = new Consumer();
            consumer.setId(id);
            consumer.setAvator(storeAvatorPath);
            consumerService.update(consumer);
            boolean flag = consumerService.update(consumer);
            if (flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"上传成功");
                jsonObject.put("avator",storeAvatorPath);
                return jsonObject;
            }else {
                jsonObject.put(Consts.CODE,0);
                jsonObject.put(Consts.MSG,"上传失败");
                return jsonObject;
            }
        } catch (IOException e) {
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败"+e.getMessage());
        }finally {
            return jsonObject;
        }
    }
}
