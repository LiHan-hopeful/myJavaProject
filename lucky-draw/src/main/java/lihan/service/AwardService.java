package lihan.service;

import lihan.mapper.AwardMapper;
import lihan.model.Award;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AwardService {

    @Autowired
    private AwardMapper awardMapper;

    public List<Award> query(Award award) {
        return awardMapper.query(award);
    }

    //1.session获取user，通过userid查询setting id，再设置到插入的award对象
    //2.登录时，setting信息设置到会话
    @Transactional
    public void add(Award award) {
        awardMapper.insertSelective(award);
    }

    @Transactional
    public void update(Award award) {
        awardMapper.updateByPrimaryKeySelective(award);
    }

    @Transactional
    public void delete(Integer id) {
        awardMapper.deleteByPrimaryKey(id);
    }
}
