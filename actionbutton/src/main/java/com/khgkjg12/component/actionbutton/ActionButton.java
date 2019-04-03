package com.khgkjg12.component.actionbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

/**
 * TODO: document your custom view class.
 */
public class ActionButton extends Button {

    FrameLayout mProgressContainer;
    int mProgressBarStyle;

    public ActionButton(Context context) {
        super(context);

        init(null, 0);
    }

    public ActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ActionButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }
    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ActionButton, defStyle, 0);

        mProgressBarStyle = a.getResourceId(
                R.styleable.ActionButton_progressBarStyle, 0);

        a.recycle();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isHandled = super.onTouchEvent(event);
        if(isHandled){
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                Drawable[] drawables = getCompoundDrawables();
                for(int i =0 ;i < 4; i++){
                    if(drawables[i]!=null) {
                        drawables[i].setColorFilter(Color.argb(100, 0, 0, 0), PorterDuff.Mode.SRC_ATOP);
                    }
                }
                if(getBackground()!=null){
                    getBackground().setColorFilter(Color.argb(100,0,0,0), PorterDuff.Mode.SRC_ATOP);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(getForeground()!=null){
                        getForeground().setColorFilter(Color.argb(100,0,0,0), PorterDuff.Mode.SRC_ATOP);
                    }
                }
                //setAlpha(0.5f);
            }else if(event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL){
                getBackground().clearColorFilter();
                //setAlpha(1.0f);
            }else if(event.getAction() == MotionEvent.ACTION_MOVE){
                if(event.getX()<0 ||event.getX() > getWidth() || event.getY() <0 ||event.getY()>getHeight()){
                    getBackground().clearColorFilter();
                    //setAlpha(1.0f);
                }
            }
        }
        return isHandled;
    }

    @Override
    public void setClickable(boolean clickable) {
        setAlpha(clickable?1.0f:0.6f);
        super.setClickable(clickable);
    }

    public void setProgressBarStyle(int style){
        mProgressBarStyle = style;
    }


    public void setProgress(boolean progress){
        setClickable(!progress);
        ViewGroup parent = (ViewGroup)getParent();
        if(parent != null) {
            if(!progress){
                if(mProgressContainer!=null) {
                    parent.removeView(mProgressContainer);
                    mProgressContainer = null;
                }
            }else{
                if(mProgressContainer==null) {
                    mProgressContainer = new FrameLayout(getContext());
                    mProgressContainer.setLayoutParams(getLayoutParams());
                    mProgressContainer.getLayoutParams().width = getWidth();
                    mProgressContainer.getLayoutParams().height = getHeight();
                    ProgressBar progressBar;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && mProgressBarStyle!=0) {
                        progressBar = new ProgressBar(getContext(), null, 0, mProgressBarStyle);
                    }else{
                        progressBar = new ProgressBar(getContext());
                    }
                    progressBar.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    mProgressContainer.addView(progressBar);
                    parent.addView(mProgressContainer);
                }
            }
        }
    }
}
