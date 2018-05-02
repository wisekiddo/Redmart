
package com.wisekiddo.redmart.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class Category {

    @SerializedName("id")
    @Expose
    private Double id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("count")
    @Expose
    private Double count;
    @SerializedName("image")
    @Expose
    private String image;

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
