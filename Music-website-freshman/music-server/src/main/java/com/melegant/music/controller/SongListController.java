package com.melegant.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.melegant.music.domain.SongList;
import com.melegant.music.service.SongListService;
import com.melegant.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;


@RestController
@Controller
@RequestMapping("/songList")
public class SongListController {

    @Autowired
    private SongListService songListService;

    /*增加歌单*/
    @ResponseBody
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addSongList(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
//        String id = request.getParameter("id").trim();
        String title = request.getParameter("title").trim();
        String pic = request.getParameter("pic").trim();
        String introduction = request.getParameter("introduction").trim();
        String style = request.getParameter("style").trim();

        //保存到歌单的对象中
        SongList songList = new SongList();
//        songList.setId(Integer.parseInt(id));
        songList.setTitle(title);
        songList.setPic(pic);
        songList.setIntroduction(introduction);
        songList.setStyle(style);
        boolean flag = songListService.insert(songList);
        if (flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"添加成功");
        }else {
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"添加失败");
        }
        return jsonObject;
    }

    /*修改歌单*/
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object updateSongList(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();
        String title = request.getParameter("title").trim();
        String introduction = request.getParameter("introduction").trim();
        String style = request.getParameter("style").trim();

        //保存到歌单的对象中
        SongList songList = new SongList();
        songList.setId(Integer.parseInt(id));
        songList.setTitle(title);
        songList.setIntroduction(introduction);
        songList.setStyle(style);
        boolean flag = songListService.update(songList);
        if (flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"修改成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"修改失败");
        return jsonObject;
    }

    /*删除歌单*/
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Object deleteSongList(HttpServletRequest request){
        String id = request.getParameter("id").trim();
        return songListService.delete(Integer.parseInt(id));
    }

    /*根据主键查询整个对象*/
    @RequestMapping(value = "/selectByPrimaryKey",method = RequestMethod.GET)
    public Object selectByPrimaryKey(HttpServletRequest request){
        String id = request.getParameter("id").trim();
        return songListService.selectByPrimaryKey(Integer.parseInt(id));
    }

    /*查询所有歌单*/
    @RequestMapping(value = "/allSongList",method = RequestMethod.GET)
    public Object allSongList(HttpServletRequest request){
        return songListService.allSongList();
    }

    /*根据标题精确查询歌单列表*/
    @RequestMapping(value = "/songListOfTitle",method = RequestMethod.POST)
    public Object songListOfTitle(HttpServletRequest request){
        String title = request.getParameter("title").trim();
        return songListService.songListOfTitle(title);
    }

    /*根据标题模糊查询歌单列表*/
    @RequestMapping(value = "/likeTitle",method = RequestMethod.GET)
    public Object likeTitle(HttpServletRequest request){
        String title = request.getParameter("sex").trim();
        return songListService.likeTitle("%"+title+"%");//Integer.parseInt()：类型转换，将括号内参数转换成Int类型
    }

    /*根据风格模糊查询歌单列表*/
    @RequestMapping(value = "/likeStyle",method = RequestMethod.GET)
    public Object likeStyle(HttpServletRequest request){
        String style = request.getParameter("sex").trim();
        return songListService.likeStyle(style);
    }

    /*更新歌单图片*/
    @RequestMapping(value = "/updateSongListPic",method = RequestMethod.POST)
    public Object updateSongListPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id") int id){
        JSONObject jsonObject = new JSONObject();
        if (avatorFile.isEmpty()) {
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败");
            return jsonObject;
        }
        /*避免同一时间上传多张文件*/
        String fileName = System.currentTimeMillis()+avatorFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
                +System.getProperty("file.separator")+"songListPic";
        /*如果文件路径不存在，新增该路径*/
        File file1 = new File(filePath);
        if (!file1.exists()){
            file1.mkdir();
        }
        /*实际的文件位置*/
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        /*存储到数据库里的相对文件地址*/
        String storeAvatorPath = "/img/songListPic/"+fileName;
        try {
            avatorFile.transferTo(dest);
            SongList songList = new SongList();
            songList.setId(id);
            songList.setPic(storeAvatorPath);
            songListService.update(songList);
            boolean flag = songListService.update(songList);
            if (flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"上传成功");
                jsonObject.put("pic",storeAvatorPath);
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
