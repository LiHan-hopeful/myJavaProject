package lihan.service;

import lihan.mapper.RecordMapper;
import lihan.model.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecordService {

    @Autowired
    private RecordMapper recordMapper;

    @Transactional
    public void add(Integer awardId, List<Integer> memberIds) {
        /**
         * 批量更新：
         * 1.循环遍历中更新====>简单暴力，不推荐。
         * 2.mybatis批量操作（扩展）===>推荐做法，效率更高，麻烦一点
         * 提供mapper自定义方法：两个参数的注意事项@Param
         * list遍历参考deleteByIds生成方法
         * xml中insert .... <foreach>
         */
//        for(Integer memberId : memberIds){
//            Record record = new Record();
//            record.setMemberId(memberId);
//            record.setAwardId(awardId);
//            recordMapper.insertSelective(record);
//        }
        recordMapper.batchAdd(awardId, memberIds);
    }

    @Transactional
    public void deleteByMemberId(Integer id) {
        Record r = new Record();
        r.setMemberId(id);
        recordMapper.deleteByCondition(r);
    }

    @Transactional
    public void deleteByAwardId(Integer id) {
        Record r = new Record();
        r.setAwardId(id);
        recordMapper.deleteByCondition(r);
    }

    @Transactional
    public void deleteBySettingId(Integer id) {
        recordMapper.deleteBySettingId(id);
    }
}
