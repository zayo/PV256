package cz.muni.fi.pv256.movio.uco_374524.superprojekt.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * Created by prasniatko on 27/10/15.
 */
public class HeadersGridLayoutManager extends GridLayoutManager {

    public HeadersGridLayoutManager(Context context, AttributeSet attrs,
        int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public HeadersGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public HeadersGridLayoutManager(Context context, int spanCount, int orientation,
        boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }
}
