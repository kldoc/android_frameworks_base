/*
* Copyright (C) 2019 The OmniROM Project
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*
*/
package com.android.systemui.omni;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.FrameLayout;

import com.android.systemui.R;

public class NotificationLightsView extends FrameLayout {
    //private ImageView mBarForground;
    private View mNotificationAnimView;
    private ValueAnimator mLightAnimator;
    /*private int mMaxWidth;
    private int mCurrentWidth;
    private boolean mInitDone;
    private int mPercent;*/

    public NotificationLightsView(Context context) {
        this(context, null);
    }

    public NotificationLightsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NotificationLightsView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public NotificationLightsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private Runnable mLightUpdate = new Runnable() {
        @Override
        public void run() {
            animateNotification();
        }
    };

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        post(mLightUpdate);
    }

    public void animateNotification() {
        int color = Color.parseColor("#3980FF");
        StringBuilder sb = new StringBuilder();
        sb.append("animateNotification color ");
        sb.append(Integer.toHexString(color));
        Log.d("NotificationLightsView", sb.toString());
        ImageView leftView = (ImageView) this.mNotificationAnimView.findViewById(R.id.notification_animation_left);
        ((ImageView) this.mNotificationAnimView.findViewById(R.id.notification_animation_right)).setColorFilter(color);
        leftView.setColorFilter(color);
        this.mLightAnimator = ValueAnimator.ofFloat(new float[]{0.0f, 2.0f});
        this.mLightAnimator.setDuration(2000);
        this.mLightAnimator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = ((Float) animation.getAnimatedValue()).floatValue();
                NotificationLightsView.this.mNotificationAnimView.setScaleY(progress);
                float alpha = 1.0f;
                if (progress <= 0.3f) {
                    alpha = progress / 0.3f;
                } else if (progress >= 1.0f) {
                    alpha = 2.0f - progress;
                }
                NotificationLightsView.this.mNotificationAnimView.setAlpha(alpha);
            }
        });
        this.mLightAnimator.start();
    }
    
}
