package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 数据字典
 */
@Getter
@Setter
@ToString
public class Dictionary {
    
    private Integer id;

    /**
     * 键
     */
    private String dictionaryKey;

    /**
     * 值
     */
    private String dictionaryValue;

    /**
     * 描述
     */
    private String dictionaryDesc;

    /**
     * 创建时间
     */
    private Date createTime;
}