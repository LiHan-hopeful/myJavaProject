package lihan.mapper;

import lihan.base.BaseMapper;
import lihan.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}