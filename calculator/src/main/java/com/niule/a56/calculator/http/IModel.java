package com.niule.a56.calculator.http;

/**
 * Created by wanglei on 2016/12/26.
 */

public interface IModel {
    boolean isNull();       //空数据

    boolean isAuthError();  //验证错误

    boolean isBizError();   //义务错误
}
