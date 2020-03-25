package com.android.thresturent.common.model;

import com.android.thresturent.common.model.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class AllItemRsponse {

    List<MenuItem> menuItems = new ArrayList<>();

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }
}
