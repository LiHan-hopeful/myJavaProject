package lihan.mapper;

import lihan.base.BaseMapper;
import lihan.model.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper extends BaseMapper<Member> {
}