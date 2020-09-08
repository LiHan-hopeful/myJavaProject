package lihan.mapper;

import lihan.base.BaseMapper;
import lihan.model.Setting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SettingMapper extends BaseMapper<Setting> {

    //mybatis传入参数有多个的时候，需要使用@Param并指定名称。xml中引用@Param中的值为变量值
    int updateByUserId(@Param("userId") Integer id, @Param("batchNumber") Integer batchNumber);
}