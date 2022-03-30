package com.shop.Shop.v1.api.Entitys;

public class Item {
    public String id;
    public String createdAt;
    public String item;
    public boolean itemStatus;

    public boolean getItemStatus() {
        return itemStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setItemStatus(boolean itemStatus) {
        this.itemStatus = itemStatus;
    }
}
