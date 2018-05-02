
package com.wisekiddo.redmart.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AtpLot {

    @SerializedName("from_date")
    @Expose
    private String fromDate;
    @SerializedName("to_date")
    @Expose
    private String toDate;
    @SerializedName("stock_status")
    @Expose
    private Double stockStatus;
    @SerializedName("qty_in_stock")
    @Expose
    private Double qtyInStock;
    @SerializedName("qty_in_carts")
    @Expose
    private Double qtyInCarts;

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public Double getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(Double stockStatus) {
        this.stockStatus = stockStatus;
    }

    public Double getQtyInStock() {
        return qtyInStock;
    }

    public void setQtyInStock(Double qtyInStock) {
        this.qtyInStock = qtyInStock;
    }

    public Double getQtyInCarts() {
        return qtyInCarts;
    }

    public void setQtyInCarts(Double qtyInCarts) {
        this.qtyInCarts = qtyInCarts;
    }


}
