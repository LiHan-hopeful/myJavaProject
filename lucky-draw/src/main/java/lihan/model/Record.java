package lihan.model;

import lihan.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 学生表
 */
@Getter
@Setter
@ToString
public class Record extends BaseEntity {
    
    private Integer id;

    private Integer memberId;

    private Integer awardId;

    /**
     * 创建时间
     */
    private Date createTime;
}