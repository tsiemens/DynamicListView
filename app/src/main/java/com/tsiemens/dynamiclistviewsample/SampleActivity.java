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
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.tsiemens.dynamiclistview.DynamicListView;

import java.util.Arrays;
import java.util.List;

public class SampleActivity extends ActionBarActivity {

    private DynamicListView listView;

    private List<String> rowData = Arrays.asList("1", "2", "3", "4", "5");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        listView = (DynamicListView) findViewById(R.id.dynamic_list_view);
        listView.setDynamicAdapter(new CustomListAdapter(this, rowData));
    }

    private class CustomListAdapter extends MyListAdapter {

        public CustomListAdapter(Context context, List<String> objects) {
            super(context, objects);
            // Colors the background of the rows while being moved, so the cell looks opaque
            fillTranslarentMobileRowBackground(android.R.color.white);
        }

        @Override
        protected boolean onRowDragItemClick(int position) {
            if (listView.canHoverRows()) {
                listView.hoverRow(position);
                return true;
            }
            return false;
        }

        @Override
        protected void doItemSwap(int pos1, int pos2) {
            String o1 = rowData.get(pos1);
            String o2 = rowData.get(pos2);
            rowData.set(pos1, o2);
            rowData.set(pos2, o1);
        }
    }
}
