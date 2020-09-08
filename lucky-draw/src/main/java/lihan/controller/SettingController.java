package lihan.controller;

import lihan.model.Setting;
import lihan.model.User;
import lihan.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/setting")
public class SettingController {

    @Autowired
    private SettingService settingService;

    /**
     * Cookie中携带tomcat的session实现，也就是请求头包含Cookie: JSESSIONID=xxx
     * 比如服务器重启，或没有登录但携带JSESSIONID的信息，获取的HttpSession对象不为空
     * 此时要判断设置的key和值，判断值不为空才是登录
     * 如果不实现拦截器，需要保证以上的逻辑
     * 如果实现拦截器，一定登录才允许访问接口
     * @param session
     * @return
     */
    @GetMapping("/query")
    public Object query(HttpSession session){
        //登录以后才能访问接口，从会话中获取保存的用户信息
        User user = (User) session.getAttribute("user");
        Setting setting = settingService.query(user.getId());
        setting.setUser(user);
        return setting;
    }

    @GetMapping("/update")
    public Object update(Integer batchNumber, HttpSession session){
        User user = (User) session.getAttribute("user");
        settingService.update(user.getId(), batchNumber);
        return null;
    }
}
