package support.api.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import support.api.R;

public class ViewEmpty extends LinearLayout {

    ImageView mImage;
    TextView mTitle;
    TextView mSubTitle;

    public ViewEmpty(Context context) {
        super(context);
    }

    public ViewEmpty(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.init(attrs);
    }

    public ViewEmpty(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void init(AttributeSet attrs) {
        Drawable drawable;
        String title;
        String subTitle;
        int color;
        int colorDefault = ContextCompat.getColor(getContext(), android.R.color.darker_gray);

        View.inflate(getContext(), R.layout.view_empty, this);

        mImage = (ImageView) findViewById(R.id.image);
        mTitle = (TextView) findViewById(R.id.title);
        mSubTitle = (TextView) findViewById(R.id.subtitle);

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs,
                    R.styleable.ViewEmpty, 0, 0);

            drawable = a.getDrawable(R.styleable.ViewEmpty_image);
            title = a.getString(R.styleable.ViewEmpty_message);
            subTitle = a.getString(R.styleable.ViewEmpty_subMessage);
            color = a.getColor(R.styleable.ViewEmpty_textColor, colorDefault);

            if (drawable != null)
                mImage.setImageDrawable(drawable);

            if (title != null)
                mTitle.setText(title);

            if (subTitle != null)
                mSubTitle.setText(subTitle);

            mImage.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
            mTitle.setTextColor(color);
            mSubTitle.setTextColor(color);

            a.recycle();
        }
    }
}
