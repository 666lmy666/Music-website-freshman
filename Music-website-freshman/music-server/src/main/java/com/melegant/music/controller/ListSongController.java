package com.melegant.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.melegant.music.domain.ListSong;
import com.melegant.music.service.impl.ListSongServiceImpl;
import com.melegant.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;

@RestController
@Controller
@RequestMapping("/listSong")
public class ListSongController {
    @Autowired
    private ListSongServiceImpl listSongService;

    /*给歌单添加歌曲*/
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addListSong(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
//        获取前端传来的参数
        String songId = request.getParameter("songId").trim();
        String songListId = request.getParameter("songListId").trim();

            ListSong listSong = new ListSong();
            listSong.setSongId(Integer.parseInt(songId));
            listSong.setSongListId(Integer.parseInt(songListId));
            boolean flag = listSongService.insert(listSong);
            if (flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"保存成功");
            }else {
                jsonObject.put(Consts.CODE,0);
                jsonObject.put(Consts.MSG,"保存失败");
            }
            return jsonObject;
    }

    /*根据歌单id查询*/
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public Object listSongOfSingerId(HttpServletRequest request){
        String songListId = request.getParameter("songListId");
        return listSongService.listSongOfSongListId(Integer.parseInt(songListId));
    }

    /*删除歌单*/
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Object deleteListSong(HttpServletRequest request){
        String songId = request.getParameter("songId").trim();      //歌曲的id
        String songListId = request.getParameter("songListId").trim();      //歌单的id
        return listSongService.deleteBySongIdAndSongListId(Integer.parseInt(songId),Integer.parseInt(songListId));
    }
}
