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
    int mColorFilter;
    int mColorFilterPressed;
    int mColorFilterProgressPrimary;
    int mColorFilterProgressSecond;

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
        mColorFilter = a.getColor(R.styleable.ActionButton_colorFilter, 0);
        mColorFilterPressed = a.getColor(R.styleable.ActionButton_colorFilterPressed, 0);
        mColorFilterProgressPrimary = a.getColor(R.styleable.ActionButton_colorFilterProgressPrimary, 0);
        mColorFilterProgressSecond = a.getColor(R.styleable.ActionButton_colorFilterProgressSecond,0);
        a.recycle();
    }

    private void setDrawableOnPressed(Drawable drawable){
        if(mColorFilterPressed!=0){
            drawable.setColorFilter(mColorFilterPressed, PorterDuff.Mode.SRC_ATOP);
        }else{
            drawable.setColorFilter(Color.argb(100, 0, 0, 0), PorterDuff.Mode.SRC_ATOP);
        }
    }

    private void setDrawableOnReleased(Drawable drawable){
        if(mColorFilter!=0){
            drawable.setColorFilter(mColorFilter, PorterDuff.Mode.SRC_ATOP);
        }else{
            drawable.clearColorFilter();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isHandled = super.onTouchEvent(event);
        if(isHandled){
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                Drawable[] drawables = getCompoundDrawables();
                for(int i =0 ;i < 4; i++){
                    if(drawables[i]!=null) {
                        setDrawableOnPressed(drawables[i]);
                    }
                }
                if(getBackground()!=null){
                    setDrawableOnPressed(getBackground());
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(getForeground()!=null){
                        setDrawableOnPressed(getForeground());
                    }
                }
                //setAlpha(0.5f);
            }else if(event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL){
                Drawable[] drawables = getCompoundDrawables();
                for(int i =0 ;i < 4; i++){
                    if(drawables[i]!=null) {
                        setDrawableOnReleased(drawables[i]);
                    }
                }
                if(getBackground()!=null){
                    setDrawableOnReleased(getBackground());
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(getForeground()!=null){
                        setDrawableOnReleased(getForeground());
                    }
                }
                //setAlpha(1.0f);
            }else if(event.getAction() == MotionEvent.ACTION_MOVE){
                if(event.getX()<0 ||event.getX() > getWidth() || event.getY() <0 ||event.getY()>getHeight()){
                    Drawable[] drawables = getCompoundDrawables();
                    for(int i =0 ;i < 4; i++){
                        if(drawables[i]!=null) {
                            setDrawableOnReleased(drawables[i]);
                        }
                    }
                    if(getBackground()!=null){
                        setDrawableOnReleased(getBackground());
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if(getForeground()!=null){
                            setDrawableOnReleased(getForeground());
                        }
                    }
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