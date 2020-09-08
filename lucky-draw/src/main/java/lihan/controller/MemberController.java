package lihan.controller;

import lihan.model.Member;
import lihan.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/add")
    public Object add(@RequestBody Member member){
        memberService.add(member);
        return null;
    }

    @PostMapping("/update")
    public Object update(@RequestBody Member member){
        memberService.update(member);
        return null;
    }

    @GetMapping("/delete/{id}")
    public Object delete(@PathVariable Integer id){
        memberService.delete(id);
        return null;
    }
}
