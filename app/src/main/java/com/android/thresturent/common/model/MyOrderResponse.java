package com.android.thresturent.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyOrderResponse
{
    @SerializedName("MyOrder")
    @Expose
    private List<MyOrder> item = null;

    public List<MyOrder> getItem() {
        return item;
    }

    public void setItem(List<MyOrder> item) {
        this.item = item;
    }
}
