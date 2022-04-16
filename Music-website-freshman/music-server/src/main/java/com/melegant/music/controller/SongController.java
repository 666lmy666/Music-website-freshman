package com.melegant.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.melegant.music.domain.Singer;
import com.melegant.music.domain.Song;
import com.melegant.music.service.impl.SongServiceImpl;
import com.melegant.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/song")
public class SongController {
    @Autowired
    private SongServiceImpl songService;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大10M,DataUnit提供5中类型B,KB,MB,GB,TB
        factory.setMaxFileSize(DataSize.of(100, DataUnit.MEGABYTES));
        /// 设置总上传数据总大小10M
        factory.setMaxRequestSize(DataSize.of(100, DataUnit.MEGABYTES));
        return factory.createMultipartConfig();
    }

    /*添加歌曲*/
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addSong(HttpServletRequest request, @RequestParam("file") MultipartFile mpFile) {
        JSONObject jsonObject = new JSONObject();
//        获取前端传来的参数
        String singerId = request.getParameter("singerId").trim();
        String name = request.getParameter("name").trim();
        String introduction = request.getParameter("introduction").trim();
        String pic = "/img/songPic/icon.jpg";
        String lyric = request.getParameter("lyric").trim();
        if (mpFile.isEmpty()) {
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"歌曲上传失败");
            return jsonObject;
        }
        /*避免同一时间上传多张文件*/
        String fileName = System.currentTimeMillis()+mpFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"song";
        /*如果文件路径不存在，新增该路径*/

        File file1 = new File(filePath);
        if (!file1.exists()){
            file1.mkdir();
        }
        /*实际的文件位置*/
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        /*存储到数据库里的相对文件地址*/
        String storeUrlPath = "/song/"+fileName;
        try {
            mpFile.transferTo(dest);
            Song song = new Song();
            song.setSingerId(Integer.parseInt(singerId));
            song.setName(name);
            song.setIntroduction(introduction);
            song.setCreateTime(new Date());
            song.setUpdateTime(new Date());
            song.setPic(pic);
            song.setLyric(lyric);
            song.setUrl(storeUrlPath);
            boolean flag = songService.insert(song);
            if (flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put("avator",storeUrlPath);
                jsonObject.put(Consts.MSG,"保存成功");
            }else {
                jsonObject.put(Consts.CODE,0);
                jsonObject.put(Consts.MSG,"保存失败");
            }
            return jsonObject;
        } catch (IOException e) {
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"保存失败"+e.getMessage());
        }finally {
            return jsonObject;
        }
    }

    /*根据歌手id查询*/
    @RequestMapping(value = "/singer/detail",method = RequestMethod.GET)
    public Object songOfSingerId(HttpServletRequest request){
        String singerId = request.getParameter("singerId");
        return songService.songOfSingerId(Integer.parseInt(singerId));
    }

    /*修改歌曲*/
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object updateSong(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();
        String name = request.getParameter("name").trim();
        String introduction = request.getParameter("introduction").trim();
        String lyric = request.getParameter("lyric").trim();

        //保存到歌曲的对象中
        Song song = new Song();
        song.setId(Integer.parseInt(id));
        song.setName(name);
        song.setIntroduction(introduction);
        song.setLyric(lyric);
        boolean flag = songService.update(song);
        if (flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"修改成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"修改失败");
        return jsonObject;
    }

    /*删除歌曲*/
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Object deleteSong(HttpServletRequest request){
        String id = request.getParameter("id").trim();
        boolean flag = songService.delete(Integer.parseInt(id));
        return flag;
    }

    /*更新歌曲图片*/
    @RequestMapping(value = "/updateSongPic",method = RequestMethod.POST)
    public Object updateSongPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id") int id){
        JSONObject jsonObject = new JSONObject();
        if (avatorFile.isEmpty()) {
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败");
            return jsonObject;
        }
        /*避免同一时间上传多张文件*/
        String fileName = System.currentTimeMillis()+avatorFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
                +System.getProperty("file.separator")+"songPic";
        /*如果文件路径不存在，新增该路径*/
        File file1 = new File(filePath);
        if (!file1.exists()){
            file1.mkdir();
        }
        /*实际的文件位置*/
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        /*存储到数据库里的相对文件地址*/
        String storeAvatorPath = "/img/songPic/"+fileName;
        try {
            avatorFile.transferTo(dest);
            Song song = new Song();
            song.setId(id);
            song.setPic(storeAvatorPath);
            songService.update(song);
            boolean flag = songService.update(song);
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

    /*更新歌曲*/
    @RequestMapping(value = "/updateSongUrl",method = RequestMethod.POST)
    public Object updateSongUrl(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id") int id){
        JSONObject jsonObject = new JSONObject();
        if (avatorFile.isEmpty()) {
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败");
            return jsonObject;
        }
        /*避免同一时间上传多张文件*/
        String fileName = System.currentTimeMillis()+avatorFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"song";
        /*如果文件路径不存在，新增该路径*/
        File file1 = new File(filePath);
        if (!file1.exists()){
            file1.mkdir();
        }
        /*实际的文件位置*/
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        /*存储到数据库里的相对文件地址*/
        String storeAvatorPath = "/song/"+fileName;
        try {
            avatorFile.transferTo(dest);
            Song song = new Song();
            song.setId(id);
            song.setUrl(storeAvatorPath);
            boolean flag = songService.update(song);
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

    /*根据歌手id查询歌曲*/
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public Object detail(HttpServletRequest request){
        String songId = request.getParameter("songId");
        return songService.selectByPrimaryKey(Integer.parseInt(songId));
    }

    /*根据歌手name精确查询歌曲*/
    @RequestMapping(value = "/songOfSongName",method = RequestMethod.GET)
    public Object songOfSongName(HttpServletRequest request){
        String songName = request.getParameter("songName");
        return songService.songOfName(songName);
    }

    /*根据歌手name模糊查询歌曲*/
    @RequestMapping(value = "/likeSongOfName",method = RequestMethod.GET)
    public Object likeSongOfName(HttpServletRequest request){
        String songName = request.getParameter("songName");
        return songService.likeSongOfName(songName);
    }

    /*查询所有歌手*/
    @RequestMapping(value = "/getAllSong",method = RequestMethod.GET)
    public Object getAllSong(){
        return songService.getAllSong();
    }


}