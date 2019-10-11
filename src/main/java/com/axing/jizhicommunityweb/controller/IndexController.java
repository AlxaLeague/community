package com.axing.jizhicommunityweb.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.axing.entity.*;
import com.axing.jizhicommunityweb.common.ResultBuilder;
import com.axing.jizhicommunityweb.common.ResultType;
import com.axing.jizhicommunityweb.webService.*;
import com.axing.jizhicommunityweb.webService.impl.WebUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    WebUserServiceImpl webUserService;
    @Autowired
    WebChannelService webChannelService;
    @Autowired
    WebAdvertisingService webAdvertisingService;
    @Autowired
    WebThesisService webThesisService;
    @Autowired
    WebCommentService webCommentService;
    @Autowired
    WebKissService webKissService;

    @Autowired
    WebUserService userService;
    @Autowired
    WebSolrService solrService;

    @RequestMapping(value = {"/index","/"})
    public String index(Model model, @RequestParam(value="name", required=false, defaultValue="World") String name, HttpServletRequest request) {
        Kiss kiss;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        List<Channel> channelList = webChannelService.getChannel(6);
//        System.out.println(channelList.toString());
        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        if (null == userInfo){
           kiss=new Kiss();
        }else {
            kiss = webKissService.getKissByKuid(userInfo.getUid());
            if (null==kiss){
                Kiss kiss1= new Kiss();
                kiss1.setKlasttime(""+System.currentTimeMillis()/1000);
                kiss1.setKuid(userInfo.getUid());
                kiss1.setKiss(5);
                kiss=kiss1;
                webKissService.insertKiss(kiss1);
            }

        }
        model.addAttribute("kiss",kiss);

//        温馨通道
        List<Channel> channelList = webChannelService.getChannel(6);
        model.addAttribute("channelList", channelList);

        //本周热议
        List<Thesis> thesisList = webThesisService.getHotThesis();
        for(Thesis thesis1 : thesisList){
            int  size = webCommentService.getComments(thesis1.getThesisid()).size();
            thesis1.setCommentcount(size);
            System.out.println(thesis1.getCommentcount());
        }
        model.addAttribute("thesisList",thesisList);

        //广告
        List<Advertising> advertisings = webAdvertisingService.getAdvertising();
        model.addAttribute("advertisings",advertisings);

        //置顶
        List<Topic> topicTopList = new ArrayList<Topic>();
        List<Thesis> thTopList= webThesisService.getTopThesis(6);
        for (Thesis th : thTopList){
            UserInfo u = userService.getUserByUid(th.getUid());

            Date date = new Date(Long.parseLong(th.getTimestamp())*1000);
            String thesistimestamp = simpleDateFormat.format(date);
            int commentcount = webCommentService.getComments(th.getThesisid()).size();
            Topic t = new Topic(th.getThesisid(),th.getClazz(),th.getTitle(),th.getExperionce(),thesistimestamp,th.getLook(),commentcount,u.getUid(),u.getUsername(),u.getAvatar(),u.getVip());
            topicTopList.add(t);
        }


        //论题
        List<Topic> topicList = new ArrayList<Topic>();
        List<Thesis> thList= webThesisService.getThesis(1,null);
        for (Thesis th : thList){
            UserInfo u = userService.getUserByUid(th.getUid());
            Date date = new Date(Long.parseLong(th.getTimestamp())*1000);
            String thesistimestamp = simpleDateFormat.format(date);
            int commentcount = webCommentService.getComments(th.getThesisid()).size();
            Topic topic = new Topic(th.getThesisid(),th.getClazz(),th.getTitle(),th.getExperionce(),thesistimestamp,th.getLook(),commentcount,u.getUid(),u.getUsername(),u.getAvatar(),u.getVip());
            topicList.add(topic);
        }

        model.addAttribute("topicList",topicList);
        model.addAttribute("topicTopList",topicTopList);

        return "/index";
    }

    //登出
    @RequestMapping(value="/user/logout", method = RequestMethod.GET)
    public String loginpage(Model model,HttpServletRequest request) {
        HttpSession session=request.getSession();//获取session并将userName存入session对象
        session.setAttribute("userInfo", null);
        Kiss kiss;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        List<Channel> channelList = webChannelService.getChannel(6);
//        System.out.println(channelList.toString());
//        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        if (null == userInfo){
           kiss=new Kiss();
        }else {
            kiss = webKissService.getKissByKuid(userInfo.getUid());
            if (null==kiss){
                Kiss kiss1= new Kiss();
                kiss1.setKlasttime(""+System.currentTimeMillis()/1000);
                kiss1.setKuid(userInfo.getUid());
                kiss1.setKiss(5);
                kiss=kiss1;
                webKissService.insertKiss(kiss1);
            }

        }
        model.addAttribute("kiss",kiss);

//        温馨通道
        List<Channel> channelList = webChannelService.getChannel(6);
        model.addAttribute("channelList", channelList);

        //本周热议
        List<Thesis> thesisList = webThesisService.getHotThesis();
        for(Thesis thesis1 : thesisList){
            int  size = webCommentService.getComments(thesis1.getThesisid()).size();
            thesis1.setCommentcount(size);
            System.out.println(thesis1.getCommentcount());
        }
        model.addAttribute("thesisList",thesisList);

        //广告
        List<Advertising> advertisings = webAdvertisingService.getAdvertising();
        model.addAttribute("advertisings",advertisings);

        //置顶
        List<Topic> topicTopList = new ArrayList<Topic>();
        List<Thesis> thTopList= webThesisService.getTopThesis(6);
        for (Thesis th : thTopList){
            UserInfo u = userService.getUserByUid(th.getUid());

            Date date = new Date(Long.parseLong(th.getTimestamp())*1000);
            String thesistimestamp = simpleDateFormat.format(date);
            int commentcount = webCommentService.getComments(th.getThesisid()).size();
            Topic t = new Topic(th.getThesisid(),th.getClazz(),th.getTitle(),th.getExperionce(),thesistimestamp,th.getLook(),commentcount,u.getUid(),u.getUsername(),u.getAvatar(),u.getVip());
            topicTopList.add(t);
        }


        //论题
        List<Topic> topicList = new ArrayList<Topic>();
        List<Thesis> thList= webThesisService.getThesis(1,null);
        for (Thesis th : thList){
            UserInfo u = userService.getUserByUid(th.getUid());
            Date date = new Date(Long.parseLong(th.getTimestamp())*1000);
            String thesistimestamp = simpleDateFormat.format(date);
            int commentcount = webCommentService.getComments(th.getThesisid()).size();
            Topic topic = new Topic(th.getThesisid(),th.getClazz(),th.getTitle(),th.getExperionce(),thesistimestamp,th.getLook(),commentcount,u.getUid(),u.getUsername(),u.getAvatar(),u.getVip());
            topicList.add(topic);
        }

        model.addAttribute("topicList",topicList);
        model.addAttribute("topicTopList",topicTopList);

        return "/index";
    }




    @RequestMapping(value = {"/json/signin"})
    @ResponseBody
    public ResultBuilder tplSigninTop(Model model) {
        System.out.println("------------------------------------------------------------111");
        return ResultBuilder.state(ResultType.NORMAL_RETURNED).build();
    }

    @RequestMapping(value = {"/search"})
//    @ResponseBody
    public String tplSigninTop(Model model,@RequestParam("q") String q,
                                      HttpServletRequest request, HttpServletResponse response){
        System.out.println(q);

        List<Topic> topicList = solrService.selectByTitle(q);
        for (Topic topic : topicList){
            UserInfo u = userService.getUserByUid(topic.getUid());
            Date date = new Date(Long.parseLong(topic.getTimestamp())*1000);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String thesistimestamp = simpleDateFormat.format(date);
            int commentcount = webCommentService.getComments(topic.getThesisid()).size();
            topic.setAvatar(u.getAvatar());
            topic.setCommentcount(commentcount);
            topic.setUsername(u.getUsername());
            topic.setVip(u.getVip());
            topic.setTimestamp(thesistimestamp);
        }
        model.addAttribute("topicList",topicList);



        //        温馨通道
        List<Channel> channelList = webChannelService.getChannel(6);
        model.addAttribute("channelList", channelList);

        //本周热议
        List<Thesis> thesisList = webThesisService.getHotThesis();
        for(Thesis thesis1 : thesisList){
            int  size = webCommentService.getComments(thesis1.getThesisid()).size();
            thesis1.setCommentcount(size);
            System.out.println(thesis1.getCommentcount());
        }
        model.addAttribute("thesisList",thesisList);

        //广告
        List<Advertising> advertisings = webAdvertisingService.getAdvertising();
        model.addAttribute("advertisings",advertisings);

        model.addAttribute("clazz",0);
        model.addAttribute("page",1);
        model.addAttribute("search",1);
//        if (n<20)
//            model.addAttribute("tail",1);



        return "/jie/index";
    }





}
