package io.fromnowon;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 实验对象
 *
 * @author hansai
 */
@Data
@NoArgsConstructor
public class TestSubject {

    /**
     * 品牌
     */
    private String brandName;

    /**
     * 国家
     */
    private String country;

    /**
     * 状态
     */
    private String state;

    /**
     * 级别
     */
    private String level;

    /**
     * 事业部
     */
    private String team;

    /**
     * 数据
     */
    private Integer data;

}
