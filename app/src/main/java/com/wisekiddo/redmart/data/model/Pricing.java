
package com.wisekiddo.redmart.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pricing {

    @SerializedName("on_sale")
    @Expose
    private Integer onSale;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("promo_price")
    @Expose
    private Integer promoPrice;
    @SerializedName("savings")
    @Expose
    private Integer savings;

    public Integer getOnSale() {
        return onSale;
    }

    public void setOnSale(Integer onSale) {
        this.onSale = onSale;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getPromoPrice() {
        return promoPrice;
    }

    public void setPromoPrice(Integer promoPrice) {
        this.promoPrice = promoPrice;
    }

    public Integer getSavings() {
        return savings;
    }

    public void setSavings(Integer savings) {
        this.savings = savings;
    }



}
