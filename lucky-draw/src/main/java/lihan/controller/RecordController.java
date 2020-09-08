package lihan.controller;

import lihan.model.User;
import lihan.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @PostMapping("/add/{awardId}")
    public Object add(@PathVariable Integer awardId, @RequestBody List<Integer> memberIds){
        recordService.add(awardId, memberIds);
        return null;
    }

    //业务上，一个人只能抽一个奖
    //如果业务允许一个人抽多个奖====>通过memberid+awardid删
    @GetMapping("/delete/member")
    public Object deleteByMemberId(Integer id){
        recordService.deleteByMemberId(id);
        return null;
    }

    @GetMapping("/delete/award")
    public Object deleteByAwardId(Integer id){
        recordService.deleteByAwardId(id);
        return null;
    }

    /**
     * TODO
     * 需要实现：根据setting_id删除中奖记录：前端可以自己锻炼一下
     */
    @GetMapping("/delete/setting")
    public Object deleteBySettingId(HttpSession session){
        User user = (User) session.getAttribute("user");
        recordService.deleteBySettingId(user.getSettingId());
        return null;
    }
}
