package com.brunogcpinheiro.igrejadaunidade.helper;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTitleText extends TextView {

    public MyTitleText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyTitleText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTitleText(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "ArchitectsDaughter.ttf");
            setTypeface(tf);
        }
    }

}
