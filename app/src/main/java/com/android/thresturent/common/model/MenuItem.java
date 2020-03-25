package com.android.thresturent.common.model;

import com.google.gson.annotations.SerializedName;

public class MenuItem
{

    @SerializedName("id")
    private int id;
    @SerializedName("item_name")
    private String item_name;
    @SerializedName("item_description")
    private String item_description;
    @SerializedName("item_image")
    private String  item_image;
    @SerializedName("price")
    private Double price;
    @SerializedName("evaluation")
    private int evaluation;

    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }

    public String getItem_image() {
        return item_image;
    }

    public void setItem_image(String item_image) {
        this.item_image = item_image;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
