package com.wisekiddo.redmart.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.UUID;

/**
 * Created by ronald on 28/4/18.
 *
 * Immutable model class for a Item.
 */

@Entity(tableName = "Item")
public class Item {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private Integer id;

    @SerializedName("title")
    @Expose
    @Nullable
    @ColumnInfo(name = "title")
    private String title;

    @SerializedName("desc")
    @Expose
    @Nullable
    @ColumnInfo(name = "desc")
    private String desc;

    @Ignore
    @SerializedName("category_tags")
    @Expose
    private List<String> categoryTags = null;

    @Ignore
    @SerializedName("sku")
    @Expose
    private String sku;

    @Ignore
    @SerializedName("categories")
    @Expose
    private List<Integer> categories = null;

    @Ignore
    @SerializedName("types")
    @Expose
    private List<Integer> types = null;

    @Ignore
    @SerializedName("details")
    @Expose
    private Details details;

    @Ignore
    @SerializedName("product_life")
    @Expose
    private ProductLife productLife;

    @Ignore
    @SerializedName("filters")
    @Expose
    private Filters filters;

    @Ignore
    @SerializedName("img")
    @Expose
    private Img img;

    @Ignore
    @SerializedName("images")
    @Expose
    private List<Image> images = null;

    @Ignore
    @SerializedName("measure")
    @Expose
    private Measure measure;

    @Ignore
    @SerializedName("pricing")
    @Expose
    private Pricing pricing;

    @Ignore
    @SerializedName("warehouse")
    @Expose
    private Warehouse warehouse;

    @Ignore
    @SerializedName("attributes")
    @Expose
    private Attributes attributes;

    @Ignore
    @SerializedName("description_fields")
    @Expose
    private DescriptionFields descriptionFields;

    @Ignore
    @SerializedName("max_days_on_shelf")
    @Expose
    private Integer maxDaysOnShelf;

    @Ignore
    @SerializedName("inventory")
    @Expose
    private Inventory inventory;

    @Ignore
    @SerializedName("pr")
    @Expose
    private Integer pr;



    public List<String> getCategoryTags() {
        return categoryTags;
    }

    public void setCategoryTags(List<String> categoryTags) {
        this.categoryTags = categoryTags;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public List<Integer> getCategories() {
        return categories;
    }

    public void setCategories(List<Integer> categories) {
        this.categories = categories;
    }

    public List<Integer> getTypes() {
        return types;
    }

    public void setTypes(List<Integer> types) {
        this.types = types;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public ProductLife getProductLife() {
        return productLife;
    }

    public void setProductLife(ProductLife productLife) {
        this.productLife = productLife;
    }

    public Filters getFilters() {
        return filters;
    }

    public void setFilters(Filters filters) {
        this.filters = filters;
    }

    public Img getImg() {
        return img;
    }

    public void setImg(Img img) {
        this.img = img;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

    public Pricing getPricing() {
        return pricing;
    }

    public void setPricing(Pricing pricing) {
        this.pricing = pricing;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public DescriptionFields getDescriptionFields() {
        return descriptionFields;
    }

    public void setDescriptionFields(DescriptionFields descriptionFields) {
        this.descriptionFields = descriptionFields;
    }

    public Integer getMaxDaysOnShelf() {
        return maxDaysOnShelf;
    }

    public void setMaxDaysOnShelf(Integer maxDaysOnShelf) {
        this.maxDaysOnShelf = maxDaysOnShelf;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Integer getPr() {
        return pr;
    }

    public void setPr(Integer pr) {
        this.pr = pr;
    }


    public Item( String title,  String desc, Integer id) {
        this.id = id;
        this.title = title;
        this.desc = desc;
    }

}
