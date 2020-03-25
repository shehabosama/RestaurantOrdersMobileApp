package com.android.thresturent.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyOrder
{
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("request_id")
    @Expose
    private String request_id;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("item_description")
    @Expose
    private String itemDescription;
    @SerializedName("item_image")
    @Expose
    private String itemImage;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("confirmation")
    @Expose
    private String confirmation;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("user_type")
    @Expose
    private String user_type;

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
