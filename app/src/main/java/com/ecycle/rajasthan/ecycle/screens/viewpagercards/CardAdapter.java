package com.ecycle.rajasthan.ecycle.screens.viewpagercards;

import android.support.v7.widget.CardView;

/**
 * Created by vaghela on 3/20/18.
 */

public interface CardAdapter {
    int MAX_ELEVATION_FACTOR = 8;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();
}
