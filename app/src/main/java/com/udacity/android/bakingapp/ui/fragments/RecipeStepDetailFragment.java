package com.udacity.android.bakingapp.ui.fragments;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.udacity.android.bakingapp.R;
import com.udacity.android.bakingapp.data.model.StepModel;
import com.udacity.android.bakingapp.presenter.RecipeStepDetailPresenter;
import com.udacity.android.bakingapp.ui.views.RecipeStepDetailView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeStepDetailFragment extends Fragment implements RecipeStepDetailView {
    public static final String STEP_ARGS = "com.udacity.android.bakingapp.step_args";
    public static final String IS_READY_PLAYED_KEY = "com.udacity.android.bakingapp.is_ready_played";
    public static final String CURRENT_WINDOW_INDEX_KEY =
            "com.udacity.android.bakingapp.current_window_index";
    public static final String CURRENT_POSITION_KEY =
            "com.udacity.android.bakingapp.current_position_key";

    @Inject RecipeStepDetailPresenter mPresenter;
    @BindView(R.id.video_container) SimpleExoPlayerView mExoPlayerView;
    @BindView(R.id.step_description) TextView mTVStepDescrion;

    private boolean isReadyPlayed = true;
    private int currentWindowIndex;
    private long currentPosition;

    private Unbinder unbinder;

    private SimpleExoPlayer mExoPlayer;

    public RecipeStepDetailFragment() {
        // Required empty public constructor
    }

    public static RecipeStepDetailFragment newInstance(StepModel step){
        RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(STEP_ARGS, step);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(STEP_ARGS)){
            mPresenter.setStep(getArguments().getParcelable(STEP_ARGS));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        if (savedInstanceState != null){
            isReadyPlayed = savedInstanceState.getBoolean(IS_READY_PLAYED_KEY, false);
            currentWindowIndex = savedInstanceState.getInt(CURRENT_WINDOW_INDEX_KEY, 0);
            currentPosition = savedInstanceState.getLong(CURRENT_POSITION_KEY, 0);
        }
        mPresenter.initData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23){
            mPresenter.initPlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || mExoPlayer == null) {
            mPresenter.initPlayer();
        }
    }

    @Override
    public void onDestroyView() {
        releaseExoPlayer();
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        backupState();
        if (Util.SDK_INT > 23)
            releaseExoPlayer();
        super.onStop();
    }

    @Override
    public void onPause() {
        backupState();
        if (Util.SDK_INT <= 23)
            releaseExoPlayer();
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_WINDOW_INDEX_KEY, currentWindowIndex);
        outState.putLong(CURRENT_POSITION_KEY, currentPosition);
        outState.putBoolean(IS_READY_PLAYED_KEY, isReadyPlayed);
    }

    @Override
    public void initData(StepModel step) {
        mTVStepDescrion.setText(step.getDescription());
        String videoUrl = step.getValidVideoUrl();
        if (TextUtils.isEmpty(videoUrl))
            mExoPlayerView.setVisibility(View.GONE);
        else {
            mExoPlayerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initPlayer(StepModel step) {
        if (step == null) return;
        String videoUrl = step.getValidVideoUrl();
        if (TextUtils.isEmpty(videoUrl)) return;
        initVideo(videoUrl);
    }

    private void initVideo(String videoUrl) {
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl();

        mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
        mExoPlayerView.setPlayer(mExoPlayer);
        mExoPlayer.setPlayWhenReady(isReadyPlayed);
        mExoPlayer.seekTo(currentWindowIndex, currentPosition);

        Uri videoUri = Uri.parse(videoUrl);

        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(), "BakingApp",
                defaultBandwidthMeter);
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource mediaSource = new ExtractorMediaSource(videoUri, dataSourceFactory,
                extractorsFactory, null, null);
        mExoPlayer.prepare(mediaSource);
    }

    @Override
    public void onDestroy() {
        releaseExoPlayer();
        super.onDestroy();
    }

    private void releaseExoPlayer() {
        if (mExoPlayer != null){
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    private void backupState(){
        if (mExoPlayer != null){
            isReadyPlayed = mExoPlayer.getPlayWhenReady();
            currentPosition = mExoPlayer.getCurrentPosition();
            currentWindowIndex = mExoPlayer.getCurrentWindowIndex();
        }
    }
}
