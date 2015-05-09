# DynamicListView
An Android ListView which allows for configurable Drag and Drop functionality


## Installation
Currently not on maven (yet?). See our releases for a .aar in the mean time.


## Using DynamicListView
The two classes that are required to use are `DynamicListView` (extends `ListView`), and `DynamicArrayAdapter` (extends `ArrayAdapter`)

To begin, you will need to add a `DynamicListView` to your layout, like any other `ListView`.
```
    <com.tsiemens.dynamiclistview.DynamicListView
        android:id="@+id/dynamic_list_view"
        android:layout_width="fill_parent"
        android:layout_height="fill_parent" />
```

Create a subclass of `DynamicArrayAdapter`, and fill out `getView` as you would for any other ArrayAdapter, with one addition: Somewhere (usually done in getView), you must specify an event that will trigger a call of `DynamicListView.hoverRow(int position)`. This will cause the row at the position to hover. It should be triggered while a touch is in progress, such as during an `onTouch` event. This allows you to specify a particular view within the row, or the row as a whole, as the drag-and-drop grip point.

Note that `DynamicListView.canHoverRows()` should be called prior, to check the state of the list.

Additionally, your `DynamicArrayAdapter` must implement `doItemSwap`, which should somehow swap your data at the two positions. This is left to be implemented for configurability, since adapter implementations are not always the same, and your data may need to be modified (saving the positions) after the swap.

## Visual Tweaks
`DynamicArrayAdapter.fillTranslarentMobileRowBackground(Integer color)` can be called to set a color which will replace any transparent areas of a row being dragged. This is particularly useful if you don't want text to overlap, and want the rows to have a more solid feel.

## Samples
Check our the super simple sample in the `/app` directory of this repo.


