package com.example.camelliayang.quicklist.Model;

import com.example.camelliayang.quicklist.QListDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by yanglu on 8/19/17.
 */

@Table(database = QListDatabase.class)
public class QListItem extends BaseModel {
    @Column
    @PrimaryKey
    public long id;

    @Column
    public String listDetail;

    public QListItem() {

    }

    public QListItem(long id, String listDetail) {
        this.id = id;
        this.listDetail = listDetail;
    }

    public String toString() {
        return this.listDetail;
    }

    public void setDetail(String listDetail) {
        this.listDetail = listDetail;
    }
}

