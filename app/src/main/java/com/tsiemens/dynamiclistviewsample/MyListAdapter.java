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

package com.tsiemens.dynamiclistviewsample;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tsiemens.dynamiclistview.DynamicArrayAdapter;
import com.tsiemens.dynamiclistview.DynamicListView;

import java.util.List;

public class MyListAdapter extends DynamicArrayAdapter<String> {

    private static final int LAYOUT = R.layout.row_layout;

    List<String> objects;
    DynamicListView listView;

    public MyListAdapter(Context context, List<String> objects) {
        super(context, LAYOUT, objects);
        this.objects = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        Holder holder;

        Resources r = getContext().getResources();
        if(row == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());

            row = inflater.inflate(LAYOUT, parent, false);
            holder = new Holder();

            holder.text = (TextView)row.findViewById(R.id.text1);
            holder.dragView = row.findViewById(R.id.drag_view);

            row.setTag(holder);
        } else {
            holder = (Holder)row.getTag();
        }

        holder.text.setText(getItem(position));
        holder.dragView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return onRowDragViewTouch(position);
            }
        });

        return row;
    }

    public void bindToListView(DynamicListView lv) {
        listView = lv;
        listView.setDynamicAdapter(this);
    }

    @Override
    protected void doItemSwap(int pos1, int pos2) {
        String o1 = objects.get(pos1);
        String o2 = objects.get(pos2);
        objects.set(pos1, o2);
        objects.set(pos2, o1);
    }

    private boolean onRowDragViewTouch(int position) {
        if (listView.canHoverRows()) {
            // The row clicked is now placed above the other rows, and follows the touch position
            listView.hoverRow(position);
            return true;
        }
        return false;
    }

    private static class Holder {
        TextView text;
        View dragView;
    }


}
