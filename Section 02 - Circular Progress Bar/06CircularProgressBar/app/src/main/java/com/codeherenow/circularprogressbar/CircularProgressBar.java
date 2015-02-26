/*
 * Copyright (C) 2015 Code Here Now
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codeherenow.circularprogressbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Ragunath Jawahar <www.codeherenow.com>
 */
public class CircularProgressBar extends View {
    // Constants
    private static final float STROKE_THICKNESS_FRACTION = 0.075f;
    private static final int COLOR_DEFAULT_BACKGROUND = 0xffababab;
    private static final int COLOR_DEFAULT_FOREGROUND = 0xff6a8afe;
    private static final float MAX_VALUE = 100;

    // Attributes
    private float mStrokeThickness;
    private RectF mCircleBounds;
    private Paint mPaint;

    private float mValue;

    /*
     * We need to override at least one of the constructors. Here, we override the simplest of all.
     * The constructor that takes in a {@link android.content.Context} parameter.
     */
    public CircularProgressBar(Context context) {
        this(context, null);
    }

    /*
     * This constructor is required to inflate the custom View from XML. Make sure to include the
     * constructor with a `Context` parameter and an `AttributeSet` parameter. Also chaining
     * constructor calls is a best practice.
     */
    public CircularProgressBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mCircleBounds = new RectF();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(mStrokeThickness);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    /*
     * All drawing is done inside this method. The View automatically manages the Canvas and the
     * Bitmap objects for you. So, you don't have to worry about those.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Background
        mPaint.setColor(COLOR_DEFAULT_BACKGROUND);
        canvas.drawArc(mCircleBounds, 0, 360, false, mPaint);

        // Foreground
        mPaint.setColor(COLOR_DEFAULT_FOREGROUND);
        float sweepAngle = mValue / MAX_VALUE * 360;
        canvas.drawArc(mCircleBounds, 0, sweepAngle, false, mPaint);
    }

    /**
     * This is the method where we calculate the logical coordinates for our View.
     * The {@code onSizeChanged(int, int, int, int)} method is called ONLY when the size of the
     * view changes. Therefore, this is the right place to update metrics.
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // Stroke Width
        int minDimension = Math.min(w, h);
        mStrokeThickness = minDimension * STROKE_THICKNESS_FRACTION;

        // Square
        float boundingSquareSide = minDimension - mStrokeThickness;
        float halfViewWidth = w / 2;
        float halfViewHeight = h / 2;
        float halfBoundingSquareSide = boundingSquareSide / 2;

        // Circle & Arc Bounds
        mCircleBounds.left = halfViewWidth - halfBoundingSquareSide;
        mCircleBounds.top = halfViewHeight - halfBoundingSquareSide;
        mCircleBounds.right = halfViewWidth + halfBoundingSquareSide;
        mCircleBounds.bottom = halfViewHeight + halfBoundingSquareSide;
    }

    public float getValue() {
        return mValue;
    }

    /*
     * Call invalidate() to redraw the view. We do this when the value changes.
     */
    public void setValue(float value) {
        this.mValue = value;
        invalidate();
    }
}
