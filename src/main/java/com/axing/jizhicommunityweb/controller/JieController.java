package com.axing.jizhicommunityweb.controller;

import com.axing.entity.*;
import com.axing.jizhicommunityweb.common.ResultBuilder;
import com.axing.jizhicommunityweb.common.ResultType;
import com.axing.jizhicommunityweb.entity.Pinglun;
import com.axing.jizhicommunityweb.entity.Problem;
import com.axing.jizhicommunityweb.webService.*;
import com.axing.jizhicommunityweb.webService.impl.WebCollectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class JieController {

    @Autowired
    WebThesisService webThesisService;
    @Autowired
    WebUserService userService;
    @Autowired
    WebThesisStateService webThesisStateService;
    @Autowired
    WebCommentService webCommentService;
    @Autowired
    WebCommentZanService webCommentZanService;
    @Autowired
    WebAdvertisingService webAdvertisingService;
    @Autowired
    WebCollectServiceImpl webCollectService;

    @Autowired
    WebChannelService webChannelService;

    @Autowired
    WebSolrService solrService;

    @RequestMapping("/jie/index")
    public String activate(Model model) {
        List problemList = new ArrayList<Problem>();
        problemList.add(new Problem("123"));
        problemList.add(new Problem("456"));
//        model.addAllAttributes(problemList);
        model.addAttribute("problemtitle","qer");
        model.addAttribute("problemList",problemList);
        System.out.println("123");
        return "/jie/index";
    }
    @RequestMapping("/jie/add")
//    @ResponseBody
    public String add(Model model, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        if (null == userInfo){
//            return "/user/login";
            try {
                response.sendRedirect("/user/login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "/jie/add";
    }
    @RequestMapping(value = "/jie/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultBuilder add(@RequestBody Map<String,String> reqMap,
                             HttpServletRequest request) {
        Iterator<Map.Entry<String, String>> it = reqMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, String> entry = it.next();
            String key = entry.getKey();
            String value=entry.getValue();
            System.out.println(key+"  "+value);
        }
        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        if (null == userInfo){
            return ResultBuilder.state(ResultType.NORMAL_RETURNED,"请登录").action("/user/login").build();
        }
        String thesisid = webThesisService.addThesis(reqMap,userInfo.getUid());
        return ResultBuilder.state(ResultType.NORMAL_RETURNED).action("/jie/detail/"+thesisid).build();
    }
//    @RequestMapping("/jie/detail")
//    public String detail(Model model) {
//        List problemList = new ArrayList<Problem>();
//        problemList.add(new Problem("123"));
//        problemList.add(new Problem("456"));
//        model.addAllAttributes(problemList);
//        model.addAttribute("problemtitle","qer");
//        model.addAttribute("problemList",problemList);
//        System.out.println("123");
//        return "/jie/detail";
//    }

    @RequestMapping(value="/jie/detail/{thesisid}")
    public String detail(Model model,@PathVariable("thesisid") String thesisid,HttpServletRequest request, HttpServletResponse response){
        //浏览+1
        int edit = 0;
        webThesisService.updateLook(thesisid);
        Thesis thesis = webThesisService.getThesisByTid(thesisid);
        UserInfo detailuserInfo = userService.getUserByUid(thesis.getUid());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(Long.parseLong(thesis.getTimestamp())*1000);
        String thesistimestamp = simpleDateFormat.format(date);
        thesis.setTimestamp(thesistimestamp);

        Thesisstate thesisstate = new Thesisstate();
                thesisstate= webThesisStateService.getThesisstateBytid(thesisid);

        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        if (null == userInfo){
            try {
                response.sendRedirect("/user/login");
                return "/user/login";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        model.addAttribute("thesis",thesis);
        if(null != thesisstate){
            System.out.println(thesisstate.toString());
            model.addAttribute("thesisstatelable",1);
            model.addAttribute("thesisstate",thesisstate);
        }


        model.addAttribute("detailuserInfo",detailuserInfo);
        if (null!=userInfo && thesis.getUid().equals(userInfo.getUid())){
            edit = 1;
        }
        model.addAttribute("edit",edit);



        List<Comment> comments = webCommentService.getComments(thesisid);
        //评论
        List<Pinglun> pinglunList = new ArrayList<Pinglun>();
        for(Comment comment:comments){
            Date date1 = new Date(Long.parseLong(comment.getTimestamp())*1000);
            String pltimestamp = simpleDateFormat.format(date1);
            UserInfo userInfo1= userService.getUserByUid(comment.getCommentuid());
            Integer praise = webCommentZanService.selectCountByCid(""+comment.getCommentid());
            int zan = 0;
//            System.out.println(comment.getCommentid()+"+===================================="+userInfo.getUid());
            CommentZan commemtZan = webCommentZanService.selectByCidAndZuid(""+comment.getCommentid(),userInfo.getUid());
            if(null == commemtZan){
                zan = 0;
            }else {
                zan = 1;
            }
            int del = 0;
            if (userInfo.getUid().equals(comment.getCommentuid())){
                del = 1;
            }
            pinglunList.add(new Pinglun(0,""+comment.getCommentid(),pltimestamp,comment.getCommentcontenth(),comment.getCommentuid(),userInfo1.getUsername(),userInfo1.getAvatar(),userInfo1.getVip(),praise,zan,del));
            System.out.println(comment.toString());
        }
        model.addAttribute("commentnum",comments.size());

        //本周热议
        List<Thesis> thesisList = webThesisService.getHotThesis();
        for(Thesis thesis1 : thesisList){
            int  size = webCommentService.getComments(thesis1.getThesisid()).size();
            thesis1.setCommentcount(size);
            System.out.println(thesis1.getCommentcount());
        }
        model.addAttribute("thesisList",thesisList);

        List<Advertising> advertisings = webAdvertisingService.getAdvertising();

        Collect collect = webCollectService.selectCollect(userInfo.getUid(),thesisid);
        if (null != collect){
            model.addAttribute("collectId",collect.getCollectid());
        }

        model.addAttribute("advertisings",advertisings);
        model.addAttribute("pinglunList",pinglunList);

        return "/jie/detail";
    }
    @RequestMapping(value="/jie/edit/{thesisid}")
    public String edit(Model model,@PathVariable("thesisid") String thesisid,HttpServletRequest request ,HttpServletResponse response){

        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        if (null == userInfo){
//            return "/user/login";
            try {
                response.sendRedirect("/user/login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Thesis thesis = webThesisService.getThesisByTid(thesisid);


        model.addAttribute("thesis",thesis);
        return "/jie/edit";
    }

    @RequestMapping(value = "/jie/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultBuilder edit(Model model,
                             @RequestBody Map<String,String> reqMap,
                             HttpServletRequest request) {
        Iterator<Map.Entry<String, String>> it = reqMap.entrySet().iterator();
//        while(it.hasNext()){
//            Map.Entry<String, String> entry = it.next();
//            String key = entry.getKey();
//            String value=entry.getValue();
//            System.out.println(key+"  "+value);
//        }
        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        if (null == userInfo){
            return ResultBuilder.state(ResultType.NORMAL_RETURNED,"请登录").action("/user/login").build();
        }
        String thesisid = webThesisService.updateThesis(reqMap);
        return ResultBuilder.state(ResultType.NORMAL_RETURNED).action("/jie/detail/"+thesisid).build();
    }

    @RequestMapping(value="/jie/del/{thesisid}")
    public void del(Model model,@PathVariable("thesisid") String thesisid,HttpServletRequest request ,HttpServletResponse response){
        try {
        webThesisService.delThesisByTid(thesisid);
        solrService.deleteByThesisId(thesisid);

//        return "/index";

            response.sendRedirect("/index");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value="/jie/pinglun/zan")
    public String zan(Model model,@RequestParam("plid") String plid,@RequestParam("thesisid") String thesisid,HttpServletRequest request ,HttpServletResponse response){

        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        if (null == userInfo){
            try {
                response.sendRedirect("/user/login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            //zan加一或者减一
            System.out.println("zan加一或者减一");
            CommentZan commemtZan = webCommentZanService.selectByCidAndZuid(plid,userInfo.getUid());
            if(null == commemtZan){
                webCommentZanService.insertCommentZan(plid,userInfo.getUid());
            }else {
                webCommentZanService.deleteByid(commemtZan.getId());
            }
        }

        return "/user/login";
    }

    @RequestMapping(value = "/jie/reply", method = RequestMethod.POST)
    @ResponseBody
    public ResultBuilder reply(@RequestBody Map<String,String> reqMap,
                             HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        if (null == userInfo){
            return ResultBuilder.state(ResultType.NORMAL_RETURNED,"请登录").action("/user/login").build();
        }
        String thesisid = reqMap.get("thesisid");
        webCommentService.addComment(reqMap,userInfo.getUid());

        return ResultBuilder.state(ResultType.NORMAL_RETURNED).action("/jie/detail/"+thesisid).build();
    }

    @RequestMapping(value="/jie/pinglun/del")
    public String zan(Model model,@RequestParam("plid") String plid,HttpServletRequest request ,HttpServletResponse response){
        System.out.println(plid);
        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        if (null == userInfo){
            try {
                response.sendRedirect("/user/login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            webCommentService.delComment(Integer.parseInt(plid));
        }

        return "/user/login";
    }


    @RequestMapping(value="/jie/index/{page}/{clazz}")
    public String jieindex(Model model,@PathVariable("clazz") String clazz,
                           @PathVariable("page") Integer page,HttpServletRequest request, HttpServletResponse response){

        List<Topic> topicList = new ArrayList<Topic>();
        List<Thesis> thList;
        if ("0".equals(clazz)){
            thList= webThesisService.getThesis(page,null);
        }else {
            thList= webThesisService.getThesis(page,clazz);
        }
        for (Thesis th : thList){
            UserInfo u = userService.getUserByUid(th.getUid());
            Date date = new Date(Long.parseLong(th.getTimestamp())*1000);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String thesistimestamp = simpleDateFormat.format(date);
            int commentcount = webCommentService.getComments(th.getThesisid()).size();
            Topic topic = new Topic(th.getThesisid(),th.getClazz(),th.getTitle(),th.getExperionce(),thesistimestamp,th.getLook(),commentcount,u.getUid(),u.getUsername(),u.getAvatar(),u.getVip());
            topicList.add(topic);
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

//        List<String> currlist = new ArrayList<String>();
//        int size = topicList.size();
//        int j = 1;
//        for (int i = page; i <size-1 && j<=5  ; i++) {
//            currlist.add(""+i);
//            j++;
//        }
        model.addAttribute("clazz",clazz);
        model.addAttribute("page",page);
        if (thList.size()<15)
        model.addAttribute("tail",1);



        return "/jie/index";
    }

    @RequestMapping(value="/jie/collect/{thesisid}")
    public void collect(Model model,@PathVariable("thesisid") String thesisid,HttpServletRequest request ,HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        if (null == userInfo){
//            return "/user/login";
            try {
                response.sendRedirect("/user/login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Thesis thesis = webThesisService.getThesisByTid(thesisid);
        webCollectService.insertCollect(new Collect(userInfo.getUid(),thesisid,thesis.getTitle(),""+System.currentTimeMillis()/1000));

//        return "/jie/detail/"+thesisid;
//        return ResultBuilder.state(ResultType.NORMAL_RETURNED).action("/jie/detail/"+thesisid).build();
        response.sendRedirect("/jie/detail/"+thesisid);
    }
    @RequestMapping(value="/jie/cancelCollect/{thesisid}/{collectId}")
    public void cancelCollect(Model model,@PathVariable("collectId") String collectId,
                                       @PathVariable("thesisid") String thesisid,HttpServletRequest request ,HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        if (null == userInfo){
//            return "/user/login";
            try {
                response.sendRedirect("/user/login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        webCollectService.deleteCollect(Integer.parseInt(collectId),userInfo.getUid());

//        return "/jie/detail/"+thesisid;
//        return ResultBuilder.state(ResultType.NORMAL_RETURNED).action("/jie/detail/"+thesisid).build();
        response.sendRedirect("/jie/detail/"+thesisid);
    }

}
