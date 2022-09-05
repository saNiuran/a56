package com.niule.a56.calculator.utils.update.fileload;

/**
 * 作者： Hokas
 * 时间： 2016/7/15
 * 类别：
 */

public class FileLoadingBean {
    /**
     * 文件大小
     */
    long total;
    /**
     * 已下载大小
     */
    long progress;

    public long getProgress() {
        return progress;
    }

    public long getTotal() {
        return total;
    }

    public FileLoadingBean(long total, long progress) {
        this.total = total;
        this.progress = progress;
    }
}
