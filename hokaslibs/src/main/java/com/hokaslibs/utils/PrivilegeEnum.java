package com.hokaslibs.utils;

/**
 * @Author Rich on 2020-03-10 16:30
 * @Projcet cat
 */

public enum PrivilegeEnum {
    发布(0), 整单购买(1), 聊天(2), 电话(3), 免费发布(4), 批发购买(5), 零售购买(6);

    private int value;

    PrivilegeEnum(Integer value) {
        this.value = value;
    }
    public int getValue(){
        return value;
    }

    public static PrivilegeEnum getEnum(int index){
        PrivilegeEnum[] c= PrivilegeEnum.class.getEnumConstants();
        return c[index];
    }
}
