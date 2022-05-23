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
public class Type implements Cloneable {

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

    @Override
    public Type clone() {
        try {
            Type clone = (Type) super.clone();
            // TODO: 复制此处的可变状态，这样此克隆就不能更改初始克隆的内部
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
