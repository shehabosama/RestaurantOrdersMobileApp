package com.android.thresturent.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookTableResponse {
    @SerializedName("Book_tables")
    @Expose
    private List<BookTables> bookTables = null;

    public List<BookTables> getBookTables() {
        return bookTables;
    }

    public void setBookTables(List<BookTables> bookTables) {
        this.bookTables = bookTables;
    }
}
