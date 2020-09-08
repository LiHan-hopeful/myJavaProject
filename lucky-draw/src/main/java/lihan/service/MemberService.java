package lihan.service;

import lihan.mapper.MemberMapper;
import lihan.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberMapper memberMapper;

    public List<Member> query(Member member) {
        return memberMapper.selectByCondition(member);
    }

    @Transactional
    public void add(Member member) {
        memberMapper.insertSelective(member);
    }
    @Transactional
    public void update(Member member) {
        memberMapper.updateByPrimaryKeySelective(member);
    }
    @Transactional
    public void delete(Integer id) {
        memberMapper.deleteByPrimaryKey(id);
    }
}
