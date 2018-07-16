package com.lucky.sweet.entity;

public class CollectStoreEntitiy  extends StatueCheckBaseEntitiy {
    private boolean isCollect;

    public CollectStoreEntitiy(String info, boolean isCollect) {
        super(info);
        this.isCollect=isCollect;
    }

    public boolean isCollect() {
        return isCollect;
    }

    public void setCollect(boolean collect) {
        isCollect = collect;
    }
}
