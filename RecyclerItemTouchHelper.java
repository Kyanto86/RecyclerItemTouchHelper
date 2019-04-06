/*
 * Developed by Peter Brüesch on 21/08/18 20:09.
 * Last modified 31/07/18 01:14.
 * Copyright (c) 2018. All Rights Reserved.
 *
 */

package petertest.myapplication;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import my.package.Dialog1;
import my.package.Dialog2;


/*The goal of the RecyclerItemTouchHelper is to only move the foreground view within a framelayout. The backgroundView will remain static.
As we move the foregroundView, the backgroundView will thus gradually become visibile.

This is useful when deleting list items. As we swipe the list item, the backgroundView will show "Delete" and make it clear to users that swiping
will delete an item.*/

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private RecyclerItemTouchHelperListener listener;
    private String mDialog;

    public RecyclerItemTouchHelper(int dragDirs, int swipeDirs, RecyclerItemTouchHelperListener listener, String dialog) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
        //The Dialog String is used for reference to the dialog/fragment/activity where this helper was initiated from.
        //It is used at the bottom in getForegroundView to gain the reference to the correct foreground view that needs to be swiped.
        this.mDialog = dialog;

    }


    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

        return false;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {

            final View foregroundView = getForegroundView(viewHolder);

            getDefaultUIUtil().onSelected(foregroundView);
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        final View foregroundView = getForegroundView(viewHolder);

        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final View foregroundView = getForegroundView(viewHolder);
        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        final View foregroundView = getForegroundView(viewHolder);
        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        listener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());

    }

    public interface RecyclerItemTouchHelperListener {

        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }

    //We get the correct foregroundView depending on the reference/attribute we initiated this helper with.
    //Helper can thus be applied to all views within an app.
    private View getForegroundView(RecyclerView.ViewHolder viewHolder) {

        switch (mDialog) {

            case "DIALOG1":
                return ((Dialog1.RecyclerAdapter.ViewHolder) viewHolder).foreground;

            case "DIALOG2":
                return ((Dialog2.RecyclerAdapter.ViewHolder) viewHolder).foreground;

            default:
                return null;
        }
    }
}
