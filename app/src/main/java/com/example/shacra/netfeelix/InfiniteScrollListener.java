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
    long time;
    private boolean isLoading = true;

    public InfiniteScrollListener(int bufferItemCount) {
        time = System.currentTimeMillis();
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
                Log.d("Infinite-isLoading", "P" + (currentPage + 1) + " total " + totalItemCount);
            }
        }

        if (isLoading && (totalItemCount > itemCount)) {
            isLoading = false;
            itemCount = totalItemCount;
            currentPage++;
            Log.d("Infinite-Set Page", "Page is now " + (currentPage) + " total " + totalItemCount);
        }

        if ( (!isLoading && (totalItemCount - visibleItemCount)<=(firstVisibleItem + bufferItemCount)) && (System.currentTimeMillis() - time > 500)) {
            Log.d("Infinite-LoadMore", "total" + totalItemCount + " visible" + visibleItemCount );
            Log.d("Infinite-LoadMore", "firstVis" + firstVisibleItem + " buffer" + bufferItemCount );
            time = System.currentTimeMillis();
            loadMore(currentPage + 1, totalItemCount);
            isLoading = true;

        }
    }
}