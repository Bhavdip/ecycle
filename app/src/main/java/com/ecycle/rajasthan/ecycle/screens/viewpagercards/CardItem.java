package com.ecycle.rajasthan.ecycle.screens.viewpagercards;

/**
 * Created by vaghela on 3/20/18.
 */

public class CardItem {

    private int mTextResource;
    private int mTitleResource;

    public CardItem(int title, int text) {
        mTitleResource = title;
        mTextResource = text;
    }

    public int getText() {
        return mTextResource;
    }

    public int getTitle() {
        return mTitleResource;
    }
}
