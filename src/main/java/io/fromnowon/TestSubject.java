package io.fromnowon;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 实验对象
 *
 * @author hansai
 */
@Data
@NoArgsConstructor
public class TestSubject {

    /**
     * id
     */
    @ToString.Exclude
    private String id;

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
     * 数据
     */
    private Integer data;

}
