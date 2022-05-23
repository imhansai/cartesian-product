package io.fromnowon;

import java.util.function.Function;

/**
 * 类型
 *
 * @author hansai
 */
public enum TypeEnum {

    /**
     * 按品牌区分
     */
    brandNameFunction(TestSubject::getBrandName),

    /**
     * 按国家区分
     */
    countryCodeFunction(TestSubject::getCountry),

    /**
     * 按状态区分
     */
    stateFunction(TestSubject::getState),

    /**
     * 按级别区分
     */
    levelFunction(TestSubject::getLevel),
    ;

    private final Function<? super TestSubject, ? extends String> classifier;

    TypeEnum(Function<? super TestSubject, ? extends String> classifier) {
        this.classifier = classifier;
    }

    public Function<? super TestSubject, ? extends String> getClassifier() {
        return classifier;
    }

}
