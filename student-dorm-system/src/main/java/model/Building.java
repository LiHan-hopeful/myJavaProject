package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 宿舍楼
 */
@Getter
@Setter
@ToString
public class Building {
    
    private Integer id;

    /**
     * 名称
     */
    private String buildingName;

    /**
     * 描述
     */
    private String buildingDesc;

    private int dormCount;

    /**
     * 创建时间
     */
    private Date createTime;

}