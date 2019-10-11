package com.axing.jizhicommunityweb.controller;



import com.axing.entity.*;
import com.axing.jizhicommunityweb.common.ResultBuilder;
import com.axing.jizhicommunityweb.common.ResultType;
import com.axing.jizhicommunityweb.entity.HomeComment;
import com.axing.jizhicommunityweb.util.CookiesUtil;
import com.axing.jizhicommunityweb.webService.*;
import com.axing.jizhicommunityweb.webService.impl.WebCollectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class UserController {
    @Autowired
    WebUserService userService;
    @Autowired
    VercodeService vercodeService;
    @Autowired
    WebKissService webKissService;
    @Autowired
    WebThesisStateService webThesisStateService;
    @Autowired
    WebThesisService webThesisService;
    @Autowired
    WebCollectServiceImpl webCollectService;

    @Autowired
    WebCommentService webCommentService;

    @Value("${static.custom.avatar}")
    private String customAvatar;
//    Map userMap = new HashMap<String,Object>();

//    //登出
//    @RequestMapping(value="/user/logout", method = RequestMethod.GET)
//    public String loginpage(Model model,HttpServletRequest request) {
//        String vercode = vercodeService.getLoginCode();
//        HttpSession session=request.getSession();//获取session并将userName存入session对象
//        session.setAttribute("userInfo", null);
//        model.addAttribute("vercode", vercode);
//        return "/index";
//    }

    //登录页面
    @RequestMapping(value="/user/login", method = RequestMethod.GET)
    public String loginpage(Model model) {
        String vercode = vercodeService.getLoginCode();
        model.addAttribute("vercode", vercode);
        return "/user/login";
    }

    //登录
    @RequestMapping(value="/user/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultBuilder login(Model model,
                               @RequestBody Map<String,String> reqMap,
                               HttpServletRequest request, HttpServletResponse response) {
            String email = reqMap.get("email");
            String pass = reqMap.get("pass");
            String vercode = reqMap.get("vercode");
            if (!vercodeService.CheckLoginCode(vercode))
                return ResultBuilder.state(ResultType.NORMAL_RETURNED,"验证码错误").operation("1").action("/user/login").build();
            UserInfo userInfo = userService.loginByEmailAndPass(email,pass);
            if(null == userInfo ){
                return ResultBuilder.state(ResultType.NORMAL_RETURNED,"用户名或密码错误").operation("1").action("/user/login").build();
            }
            model.addAttribute("email", userInfo.getEmail());

            HttpSession session=request.getSession();//获取session并将userName存入session对象
            userInfo.setPass("");
            //
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(Long.parseLong(userInfo.getRegistertimestamp())*1000);
            String registertimestamp = simpleDateFormat.format(date);
            userInfo.setRegistertimestamp(registertimestamp);
            session.setAttribute("userInfo", userInfo);
            //测试token
            response.addCookie(CookiesUtil.getCookie("token", "token", 5*60));
            System.out.println("-------------------login--------------------------");
            return ResultBuilder.state(ResultType.NORMAL_RETURNED,"登录成功").action("/index").build();
            }
    //页面注册
    @RequestMapping(value = "/user/reg", method = RequestMethod.GET)
    public String reg(Model model) {
        String vercode = vercodeService.getRegCode();
        model.addAttribute("vercode", vercode);
        return "/user/reg";
    }

    @RequestMapping(value = "/user/reg", method = RequestMethod.POST)
    @ResponseBody
    public ResultBuilder userReg(
                                 @RequestBody Map<String,String> reqMap) {
        String email = reqMap.get("email");
        String pass = reqMap.get("pass");
        String username = reqMap.get("username");
        String repass = reqMap.get("repass");
        String vercode = reqMap.get("vercode");
        System.out.println("注册");
        if(StringUtils.isBlank(pass) || StringUtils.isBlank(repass) || !pass.equals(repass)){
            return ResultBuilder.state(ResultType.NORMAL_RETURNED,"密码不一致，请确认").operation("1").build();
        }
        if (!vercodeService.CheckRegCode(vercode))
            return ResultBuilder.state(ResultType.NORMAL_RETURNED,"验证码错误").operation("1").build();
        int i = userService.register(email,username,pass);
        if (i==0){
            return ResultBuilder.state(ResultType.NORMAL_RETURNED,"注册失败").operation("1").build();
        }
        return ResultBuilder.state(ResultType.NORMAL_RETURNED).action("/index").build();
    }
    //忘记密码
    @RequestMapping("/user/forget")
    public String forget(Model model,HttpServletRequest request) {
        HttpSession session=request.getSession();
        String email = (String) session.getAttribute("repass");
        if (StringUtils.isNotBlank(email)){
            UserInfo userInfo = userService.getUserByEmail(email);
            model.addAttribute("username", userInfo.getUsername());
        }

        return "user/forget";
    }
    //发送验证码
    @RequestMapping("/user/forgetCode")
    @ResponseBody
    public ResultBuilder forgetCode(@RequestBody Map<String,String> reqMap,HttpServletRequest request) {
        String email = reqMap.get("email");
//        HttpSession session=request.getSession();//获取session并将userName存入session对象
        String s = vercodeService.sendForgetCode(email);
        System.out.println(s);
//        session.setAttribute("repass", email);
        return ResultBuilder.state(ResultType.NORMAL_RETURNED,"验证码发送成功").action("/user/forget").operation("1").build();
    }
    @RequestMapping(value = "/user/forget", method = RequestMethod.POST)
    @ResponseBody
    public ResultBuilder userForget(
            Model model,
            @RequestBody Map<String,String> reqMap,HttpServletRequest request) {
        String vercode = reqMap.get("vercode");
        String email = reqMap.get("email");

        HttpSession session=request.getSession();
//        String email = (String) session.getAttribute("repass");
        if (StringUtils.isNotBlank(email) && vercodeService.CheckForgetCode(email,vercode)){
            UserInfo userInfo = userService.getUserByEmail(email);
            model.addAttribute("username", userInfo.getUsername());
            session.setAttribute("repass", email);
            System.out.println("---------------------forget------------------------");
            return ResultBuilder.state(ResultType.NORMAL_RETURNED,"请重置密码").action("/user/forget").build();
        }else {
            return ResultBuilder.state(ResultType.NORMAL_RETURNED,"验证码或邮箱错误").action("/user/forget").build();
        }
    }
    //重置密码
    @RequestMapping(value="/user/repass", method = RequestMethod.POST)
    @ResponseBody
    public ResultBuilder repass(
            Model model,
            @RequestBody Map<String,String> reqMap,
            HttpServletRequest request, HttpServletResponse response) {
        HttpSession session=request.getSession();
        String email = (String) session.getAttribute("repass");
        String pass = reqMap.get("pass");
        String repass = reqMap.get("repass");
        System.out.println(pass + "  "+ repass);
        if(StringUtils.isNotBlank(pass)&&StringUtils.isNotBlank(repass)&&!pass.equals(repass)){
            return ResultBuilder.state(ResultType.NORMAL_RETURNED,"密码不一致").action("/user/forget").build();
        }
        UserInfo userInfo = userService.getUserByEmail(email);
        System.out.println(userInfo.getId()+ " "+userInfo.getPass());
        if (0==userService.changePass(userInfo.getId(),pass)){
            return ResultBuilder.state(ResultType.NORMAL_RETURNED,"修改密码失败").action("/user/forget").build();
        }
        session.setAttribute("repass",null);
        System.out.println("-------------------repass--------------------------");
        return ResultBuilder.state(ResultType.NORMAL_RETURNED,"重置密码成功").action("/index").build();

    }

    @RequestMapping("/user/set")
    public String set(Model model) {
        return "/user/set";
    }

    @RequestMapping(value="/user/upload")
    @ResponseBody
    public ResultBuilder fileUpload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {
        if (file.isEmpty()) {
            System.out.println("文件为空空");
        }
        HttpSession session = request.getSession();
        UserInfo userInfo1 = (UserInfo) session.getAttribute("userInfo");
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
//        String filePath = "E://temp-rainy//"; // 上传后的路径
        String filePath = customAvatar; // 上传后的路径
//        System.out.println(request.getServletContext().getRealPath("/static/upload"));
        System.out.println(fileName);
//        fileName = UUID.randomUUID() + suffixName; // 新文件名
        fileName = userInfo1.getUid()+ suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        String filename = "http://localhost:8080/images/avatar/" + fileName;
        String filename = "http://localhost:8080/images/avatar/" + fileName;

        userService.updateUserInfoAvatar(userInfo1.getId(),filename);
        userInfo1.setAvatar(filename);
        session.setAttribute("userInfo", userInfo1);
//        model.addAttribute("filename", filename);
        return ResultBuilder.state(ResultType.NORMAL_RETURNED,"重置头像成功").action("/user/set").build();
    }

    @RequestMapping(value = "/user/set", method = RequestMethod.POST)
    @ResponseBody
    public ResultBuilder userSet(
            @RequestBody Map<String,String> reqMap,HttpServletRequest request) {
        String username = reqMap.get("username");
        String sex = reqMap.get("sex");
        String city = reqMap.get("city");
        String sign = reqMap.get("sign");

        System.out.println(sign);
        HttpSession session=request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        userService.updateUserInfoSet(userInfo.getId(),username,Integer.parseInt(sex),city,sign);
        userInfo.setUsername(username);
        userInfo.setSex(Integer.parseInt(sex));
        userInfo.setCity(city);
        userInfo.setSignature(sign);
        session.setAttribute("userInfo", userInfo);
        return ResultBuilder.state(ResultType.NORMAL_RETURNED,"修改成功").action("/user/set").build();
    }
    @RequestMapping(value = "/user/changePass", method = RequestMethod.POST)
    @ResponseBody
    public ResultBuilder changePass(
            @RequestBody Map<String,String> reqMap,HttpServletRequest request) {
        String nowpass = reqMap.get("nowpass");
        String pass = reqMap.get("pass");
        String repass = reqMap.get("repss");
        HttpSession session=request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        UserInfo u = userService.getUserByUid(userInfo.getUid());
        if(StringUtils.isNotBlank(pass)&&StringUtils.isNotBlank(repass)&&!pass.equals(repass)){
            return ResultBuilder.state(ResultType.NORMAL_RETURNED,"密码不一致").action("/user/set#pass").build();
        }
        if(StringUtils.isNotBlank(nowpass)&&!nowpass.equals(u.getPass())){
            return ResultBuilder.state(ResultType.NORMAL_RETURNED,"旧密码错误").action("/user/set#pass").build();
        }
        if (0==userService.changePass(u.getId(),pass)){
            return ResultBuilder.state(ResultType.NORMAL_RETURNED,"修改密码失败").action("/user/set#pass").build();
        }
        return ResultBuilder.state(ResultType.NORMAL_RETURNED,"重置密码成功").action("/index").build();
    }




    @RequestMapping("/user/activate")
    public String activate(Model model) {
        return "/user/activate";
    }
    @RequestMapping("/user/home")
    public String home(Model model) {
        return "/user/home";
    }
//    @RequestMapping("/user/index")
//    public String index(Model model) {
//        return "user/index";
//    }
    //    @RequestMapping("/user/set")
//    public String set(Model model) {
//        return "user/set";
//    }
    @RequestMapping("/user/message")
    public String message(Model model) {
        return "/user/message";
    }
//    @RequestMapping("/user/dubootest")
//    public String dubootest(Model model) {
//        System.out.println(webUserService.getUserByUid("2").toString());
//        return "index";
//    }

    @RequestMapping(value = "/user/signin", method = RequestMethod.POST)
    @ResponseBody
    public ResultBuilder signin(HttpServletRequest request) {
        HttpSession session=request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        Kiss kiss = webKissService.getKissByKuid(userInfo.getUid());
        if (null==kiss){
            Kiss kiss1= new Kiss();
            kiss1.setKlasttime(""+System.currentTimeMillis()/1000);
            kiss1.setKuid(userInfo.getUid());
            kiss1.setKiss(5);
            webKissService.insertKiss(kiss1);
        }else {
            if(Long.parseLong(kiss.getKlasttime())/(3600*24)==(System.currentTimeMillis()/1000/(3600*24))){
                return ResultBuilder.state(ResultType.NORMAL_RETURNED,"今天已经签到过了").action("/index").build();
            }
            kiss.setKiss(kiss.getKiss()+5);
            kiss.setKlasttime(""+System.currentTimeMillis()/1000);
            webKissService.updateKiss(kiss);
        }
        return ResultBuilder.state(ResultType.NORMAL_RETURNED,"签到成功").action("/index").build();
    }
    @RequestMapping(value="/user/home/{uid}")
    public String detail(Model model,@PathVariable("uid") String uid){

        UserInfo userInfo = userService.getUserByUid(uid);
        userInfo.setPass("");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(Long.parseLong(userInfo.getRegistertimestamp())*1000);
        String registertimestamp = simpleDateFormat.format(date);
        userInfo.setRegistertimestamp(registertimestamp);
        model.addAttribute("userInfo",userInfo);

        List<Thesis> thesisList = webThesisService.getThesisByUid(uid);
        System.out.println("++++++++");
        System.out.println(thesisList.size());
        model.addAttribute("thesisList",thesisList);

        List<HomeComment> homeComments =webCommentService.getCommentsByUid(uid);

        model.addAttribute("homeComments",homeComments);

        return "/user/home";
    }
    @RequestMapping(value="/user/index")
    public String index(Model model,HttpServletRequest request, HttpServletResponse response){

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
        List<Thesis> thesisList = webThesisService.getThesisByUid(userInfo.getUid());
        for (Thesis thesis : thesisList){
            thesis.setCommentcount(webCommentService.getComments(thesis.getThesisid()).size());
        }
        model.addAttribute("thesisList",thesisList);

        List<Collect> collects =webCollectService.getCollectByKuid(userInfo.getUid());
        for (Collect collect :collects){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(Long.parseLong(collect.getTimestamp())*1000);
            String thesistimestamp = simpleDateFormat.format(date);
            collect.setTimestamp(thesistimestamp);
        }
        model.addAttribute("collects",collects);




        return "/user/index";
    }
}
