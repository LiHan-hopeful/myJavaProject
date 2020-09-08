package lihan.controller;

import lihan.model.Award;
import lihan.model.User;
import lihan.service.AwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/award")
public class AwardController {

    @Autowired
    private AwardService awardService;

    @PostMapping("/add")
    public Object add(@RequestBody Award award, HttpSession session){//插入时，请求数据中不带id
        User user = (User) session.getAttribute("user");
        award.setSettingId(user.getSettingId());
        awardService.add(award);
        return award.getId();//数据库插入时，自动设置id为自增主键：返回前端，否则有bug
    }

    @PostMapping("/update")
    public Object update(@RequestBody Award award){//修改是id修改
        awardService.update(award);
        return null;
    }
    //@PathVariable中的值绑定路径变量，如果@PathVariable没有值，绑定为变量名
    @GetMapping("/delete/{id}")
    public Object delete(@PathVariable Integer id){
        awardService.delete(id);
        return null;
    }
}
