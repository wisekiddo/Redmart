
package com.wisekiddo.redmart.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class Pricing {

    @SerializedName("on_sale")
    @Expose
    private Double onSale;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("promo_price")
    @Expose
    private Double promoPrice;
    @SerializedName("savings")
    @Expose
    private Double savings;

    public Double getOnSale() {
        return onSale;
    }

    public void setOnSale(Double onSale) {
        this.onSale = onSale;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPromoPrice() {
        return promoPrice;
    }

    public void setPromoPrice(Double promoPrice) {
        this.promoPrice = promoPrice;
    }

    public Double getSavings() {
        return savings;
    }

    public void setSavings(Double savings) {
        this.savings = savings;
    }


}
