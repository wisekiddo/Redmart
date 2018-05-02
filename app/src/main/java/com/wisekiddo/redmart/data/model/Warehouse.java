
package com.wisekiddo.redmart.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Warehouse {

    @SerializedName("measure")
    @Expose
    private Measure_ measure;

    public Measure_ getMeasure() {
        return measure;
    }

    public void setMeasure(Measure_ measure) {
        this.measure = measure;
    }



}
