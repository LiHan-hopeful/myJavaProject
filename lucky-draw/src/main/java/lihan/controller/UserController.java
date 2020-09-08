package lihan.controller;

import lihan.model.Setting;
import lihan.model.User;
import lihan.service.SettingService;
import lihan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;//注意一定要在类型上加上@Service，注册到容器中

    @Autowired
    private SettingService settingService;

    @PostMapping("/login")
    public Object login(@RequestBody User user, HttpServletRequest req){
        //如果用户名密码校验失败，在service中抛异常，这里exist一定不为null
        User exist = userService.login(user);
        //偷懒的做法，可以提供一个方法，根据用户id，获取setting_id
        Setting setting = settingService.query(exist.getId());//自行实现
        exist.setSettingId(setting.getId());
        HttpSession session = req.getSession();
        session.setAttribute("user", exist);
        return null;
    }

    @PostMapping("/register")
    public Object register(User user,
                           //上传的头像：1.保存在本地文件夹（web服务器需要加载到）2.url存放在注册用户的head字段
                           @RequestPart(value = "headFile", required = false) MultipartFile headFile){
        //没有做服务器请求数据的校验，实现方式：1.手动校验2.使用validation框架校验（很多注解）
        //TODO
        userService.register(user, headFile);
        return null;
    }

    /**
     * TODO
     * 注销功能：get api/user/logout
     */
    @GetMapping("/logout")
    public Object logout(HttpSession session){
        if(session != null)
            session.removeAttribute("user");
        return null;
    }
}
