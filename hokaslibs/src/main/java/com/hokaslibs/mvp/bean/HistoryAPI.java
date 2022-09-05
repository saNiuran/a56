package com.hokaslibs.mvp.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * 作者： Hokas
 * 时间： 2016/10/24
 * 类别：
 */
@DatabaseTable(tableName = "HistoryAPI")
public class HistoryAPI implements Serializable {

    // 记得导入ormlite库
    @DatabaseField(generatedId = true)
    private int RecordId;
    @DatabaseField
    public String name;
    @DatabaseField
    public long time;
    @DatabaseField
    public int type;

    public HistoryAPI() {
        name = "";
        time = 0;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getRecordId() {
        return RecordId;
    }

    public void setRecordId(int recordId) {
        RecordId = recordId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
