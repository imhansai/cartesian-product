package io.fromnowon;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 为了判断值
 *
 * @author hansai
 */
@Data
@NoArgsConstructor
public class Type {

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

    // /**
    //  * 级别
    //  */
    // private String level;

}
