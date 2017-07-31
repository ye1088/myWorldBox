package com.mojang.minecraftpe.store;

public class Store {
    private StoreListener listener;

    public Store(StoreListener paramStoreListener) {
        this.listener = paramStoreListener;
    }

    public void acknowledgePurchase(String paramString1, String paramString2) {
        System.out.println("Acknowledge purchase: " + paramString1 + ":" + paramString2);
    }

    public void destructor() {
    }

    public String getStoreId() {
        return "Placeholder store ID";
    }

    public void purchase(String paramString) {
    }

    public void purchase(String paramString1, boolean paramBoolean, String paramString2) {
        System.out.println("Store: purchase " + paramString1 + ": " + paramBoolean + ": " + paramString2);
    }

    public void queryProducts(String[] paramArrayOfString) {
    }

    public void queryPurchases() {
    }
}
