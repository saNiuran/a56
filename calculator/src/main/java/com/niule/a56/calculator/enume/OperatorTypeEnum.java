package com.niule.a56.calculator.enume;

/**
 * @Author Rich on 2020-01-10 17:57
 * @Projcet mall
 */
public enum OperatorTypeEnum {
    买家(0), 卖家(1), 平台(2);
    private int value;

    OperatorTypeEnum(Integer value) {
        this.value = value;
    }
    public int getValue(){
        return value;
    }

    public static OperatorTypeEnum getEnum(int index){
        OperatorTypeEnum[] c= OperatorTypeEnum.class.getEnumConstants();
        return c[index];
    }
}
