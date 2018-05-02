
package com.wisekiddo.redmart.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Measure {

    @SerializedName("wt_or_vol")
    @Expose
    private String wtOrVol;
    @SerializedName("size")
    @Expose
    private Integer size;

    public String getWtOrVol() {
        return wtOrVol;
    }

    public void setWtOrVol(String wtOrVol) {
        this.wtOrVol = wtOrVol;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }


}
