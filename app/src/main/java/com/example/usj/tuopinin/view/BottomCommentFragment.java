package com.example.usj.tuopinin.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.usj.tuopinin.R;
import com.example.usj.tuopinin.model.CachePlaces;
import com.example.usj.tuopinin.model.entities.Comment;
import com.example.usj.tuopinin.presenter.BottomPlaceDetailsPresenter;
import com.example.usj.tuopinin.view.interfaces.BottomCommentView;
import com.synnapps.carouselview.CarouselView;

import java.util.List;

import static com.example.usj.tuopinin.Constants.ADD_COMMENT;
import static com.example.usj.tuopinin.Constants.ENTER_COMMENT;
import static com.example.usj.tuopinin.Constants.FRAGMENT_NAME;
import static com.example.usj.tuopinin.Constants.LATITUDE;
import static com.example.usj.tuopinin.Constants.LONGITUDE;

public class BottomCommentFragment extends BottomSheetDialogFragment implements BottomCommentView {

    Button commentButton;
    CarouselView photosCarouselView;
    BottomPlaceDetailsPresenter placeDetailsPresenter;
    RecyclerView commentsRecyclerView;
    private double latitude;
    private double longitude;
    private NestedScrollView nestedScrollView;

    public static BottomCommentFragment newInstance(double latitude, double longitude) {
        BottomCommentFragment bottomCommentFragment = new BottomCommentFragment();
        Bundle bundle = new Bundle();
        bundle.putDouble(LATITUDE, latitude);
        bundle.putDouble(LONGITUDE, longitude);
        bottomCommentFragment.setArguments(bundle);
        return bottomCommentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bottom_comment, container,
                false);
        longitude = getArguments().getDouble(LONGITUDE);
        latitude = getArguments().getDouble(LATITUDE);
        placeDetailsPresenter = new BottomPlaceDetailsPresenter(this, CachePlaces.getInstance());
        commentButton = view.findViewById(R.id.commentButton);
        photosCarouselView = view.findViewById(R.id.cvPlacePhotos);
        commentsRecyclerView = view.findViewById(R.id.commentsRecyclerView);
        nestedScrollView = view.findViewById(R.id.nestedScrollView);
        placeDetailsPresenter.displayComments(latitude, longitude);
        placeDetailsPresenter.displayImages(latitude, longitude);
        commentButton.setOnClickListener(v -> onAddCommentAndPhotoButtonClick());
        return view;
    }


    @Override
    public void onAddCommentAndPhotoButtonClick() {
        placeDetailsPresenter.comment();
    }

    @Override
    public void displayImages(List<String> images) {
        photosCarouselView.setVisibility(View.VISIBLE);
        photosCarouselView.setPageCount(images.size());
        photosCarouselView.setImageListener((position, imageView) -> imageView.setImageURI(Uri.parse(images.get(position))));
    }

    @Override
    public void displayComments(List<Comment> comments) {
        nestedScrollView.setVisibility(View.VISIBLE);
        commentsRecyclerView.setNestedScrollingEnabled(false);
        CommentsAdapter commentsAdapter = new CommentsAdapter(comments);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(commentsRecyclerView.getContext(),
                DividerItemDecoration.HORIZONTAL);
        commentsRecyclerView.addItemDecoration(dividerItemDecoration);
        commentsRecyclerView.setLayoutManager(layoutManager);
        commentsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        commentsRecyclerView.setAdapter(commentsAdapter);
    }

    @Override
    public void hideImages() {
        photosCarouselView.setVisibility(View.GONE);
    }

    @Override
    public void hideComments() {
        nestedScrollView.setVisibility(View.GONE);
    }

    @Override
    public void addComment() {
        Intent intent = new Intent(getActivity(), AddLocationDetailsActivity_.class);
        intent.putExtra(LATITUDE, latitude);
        intent.putExtra(LONGITUDE, longitude);
        intent.putExtra(FRAGMENT_NAME, ENTER_COMMENT);
        getActivity().startActivityForResult(intent, ADD_COMMENT);
        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        placeDetailsPresenter.onDestroy();
    }
}
