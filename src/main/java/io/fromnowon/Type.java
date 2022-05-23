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

    @Override
    public Type clone() {
        try {
            return (Type) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("克隆 Type 异常", e);
        }
    }

}
