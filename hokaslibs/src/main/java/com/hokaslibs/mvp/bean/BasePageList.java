package com.hokaslibs.mvp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Rich on 2020-03-03 10:04
 * @Projcet cat
 */
public class BasePageList<T> implements Serializable {
    private int total;
    private boolean isLastPage;
    private List<T> list;

    public BasePageList(){

    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
