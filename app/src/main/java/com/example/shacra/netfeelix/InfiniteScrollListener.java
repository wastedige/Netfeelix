package com.example.shacra.netfeelix;

import android.util.Log;
import android.widget.AbsListView;

/**
 * Created by shacra on 1/23/2016.
 */
public abstract class InfiniteScrollListener implements AbsListView.OnScrollListener {
    private int bufferItemCount = 10;
    private int currentPage = 0;
    private int itemCount = 0;
    private boolean isLoading = true;

    public InfiniteScrollListener(int bufferItemCount) {
        this.bufferItemCount = bufferItemCount;
    }

    public abstract void loadMore(int page, int totalItemsCount);

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // Do Nothing
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
    {
        if (totalItemCount < itemCount) {
            this.itemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.isLoading = true;
                Log.v("Infinite-isLoading", "P" + (currentPage + 1) + " total " + totalItemCount);
            }
        }

        if (isLoading && (totalItemCount > itemCount)) {
            isLoading = false;
            itemCount = totalItemCount;
            currentPage++;
            Log.v("Infinite-Set Page", "Page is now " + (currentPage) + " total " + totalItemCount);
        }

        if (!isLoading && (totalItemCount - visibleItemCount)<=(firstVisibleItem + bufferItemCount)) {
            Log.v("Infinite-LoadMore", "total" + totalItemCount + " visible" + visibleItemCount );
            Log.v("Infinite-LoadMore", "firstVis" + firstVisibleItem + " buffer" + bufferItemCount );
            loadMore(currentPage + 1, totalItemCount);
            isLoading = true;

        }
    }
}