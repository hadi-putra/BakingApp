package com.udacity.android.bakingapp.glide;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.data.DataFetcher;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by hadi on 17/09/17.
 */

public class ThumbnailFetcher implements DataFetcher<InputStream> {
    private ThumbnailUrl thumbnailUrl;

    public ThumbnailFetcher(ThumbnailUrl thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public void loadData(Priority priority, DataCallback<? super InputStream> callback) {
        
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = null;
        try {
            retriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14){
                retriever.setDataSource(thumbnailUrl.getVideoUrl(), new HashMap<>());
            } else {
                retriever.setDataSource(thumbnailUrl.getVideoUrl());
            }
            bitmap = retriever.getFrameAtTime();
        } finally {
            if (retriever != null){
                retriever.release();
            }
        }

        if (bitmap == null){
            callback.onLoadFailed(new Exception("Bitmap is null"));
        } else {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

            callback.onDataReady(inputStream);
        }
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void cancel() {

    }

    @NonNull
    @Override
    public Class<InputStream> getDataClass() {
        return InputStream.class;
    }

    @NonNull
    @Override
    public DataSource getDataSource() {
        return DataSource.REMOTE;
    }
}
