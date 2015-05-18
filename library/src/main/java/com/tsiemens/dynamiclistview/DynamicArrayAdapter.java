/*
 * Copyright (C) 2015 Trevor Siemens
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tsiemens.dynamiclistview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.List;


public abstract class DynamicArrayAdapter<T> extends ArrayAdapter<T> {

    private ItemSwapListener swapListener;
    private RowHoverListener hoverListener;
    private Integer backgroundColor = null;

    public interface ItemSwapListener {
        public void onItemsSwapped(int pos1, int pos2);
    }

    public interface RowHoverListener {
        public void onHoverEventStarted(DynamicListView.HoverEvent event);
        public void onHoverEventFinished(DynamicListView.HoverEvent event);
    }

    public DynamicArrayAdapter(Context context, int textViewResourceId, List<T> objects) {
        super(context, textViewResourceId, objects);
    }

    /** When a row is mobile (being moved), often is is desirable to color the background
     * if the row background is transparent.
     * @param color The color to fill transparent parts of the row with. If null, will not color.
     **/
    public void fillTranslarentMobileRowBackground(Integer color) {
        backgroundColor = color;
    }

    public void setOnItemsSwappedListener(ItemSwapListener listener) {
        swapListener = listener;
    }

    public void setOnRowHoverListener(RowHoverListener listener) {
        hoverListener = listener;
    }

    public void swapItems(int pos1, int pos2) {
        doItemSwap(pos1, pos2);
        if (swapListener != null) {
            swapListener.onItemsSwapped(pos1, pos2);
        }
    }

    public void finishedHoverEvent(DynamicListView.HoverEvent event) {
        if (hoverListener != null) {
            hoverListener.onHoverEventFinished(event);
        }
    }

    public void startedHoverEvent(DynamicListView.HoverEvent event) {
        if (hoverListener != null) {
            hoverListener.onHoverEventStarted(event);
        }
    }

    /** During this method, the items should be re-arranged in whatever list was passed into the constructor. **/
    protected abstract void doItemSwap(int pos1, int pos2);

    public Bitmap getBitmapForMobileRow(View row) {
        Bitmap bm = getBitmapFromView(row);
        if (backgroundColor != null) {
            bm = fillTransparentRowBackground(bm, backgroundColor);
        }
        return bm;
    }

    private Bitmap getBitmapFromView(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas (bitmap);
        v.draw(canvas);
        return bitmap;
    }

    private Bitmap fillTransparentRowBackground(Bitmap bitmapOfRow, int backgroundColor) {
        Bitmap displayedMobileRow = bitmapOfRow.copy(bitmapOfRow.getConfig(), true);

        Canvas can = new Canvas(displayedMobileRow);
        Rect rect = new Rect(0, 0, bitmapOfRow.getWidth(), bitmapOfRow.getHeight());

        Paint backgroundPaint = new Paint();
        backgroundPaint.setStyle(Paint.Style.FILL);
        backgroundPaint.setColor(backgroundColor);

        // Draw the background, then our row on top.
        can.drawRect(rect, backgroundPaint);
        can.drawBitmap(bitmapOfRow, 0, 0, null);

        return displayedMobileRow;
    }
}
