package com.example.usj.tuopinin.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.usj.tuopinin.Constants;
import com.example.usj.tuopinin.R;
import com.example.usj.tuopinin.model.CachePlaces;
import com.example.usj.tuopinin.presenter.EnterCommentPresenter;
import com.example.usj.tuopinin.view.interfaces.CommentView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static com.example.usj.tuopinin.Constants.LATITUDE;
import static com.example.usj.tuopinin.Constants.LONGITUDE;
import static com.example.usj.tuopinin.Constants.PICK_IMAGE;
import static com.example.usj.tuopinin.Constants.REQUEST_TAKE_PHOTO;

@EFragment(R.layout.fragment_comment)
public class CommentFragment extends Fragment implements CommentView {

    EnterCommentPresenter enterCommentPresenter;
    Uri photoURI;

    @ViewById
    RatingBar ratingBar;

    @ViewById
    EditText commentEditText;

    @ViewById
    ImageView photoImageView;

    private double latitude;

    private double longitude;

    public static CommentFragment newInstance(double latitude, double longitude) {
        CommentFragment commentFragment = new CommentFragment_();
        Bundle bundle = new Bundle();
        bundle.putDouble(LATITUDE, latitude);
        bundle.putDouble(LONGITUDE, longitude);
        commentFragment.setArguments(bundle);
        return commentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        longitude = getArguments().getDouble(LONGITUDE);
        latitude = getArguments().getDouble(LATITUDE);
        enterCommentPresenter = new EnterCommentPresenter(this, CachePlaces.getInstance());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Click(R.id.saveButton)
    @Override
    public void onSaveButtonClicked() {
        enterCommentPresenter.saveComment(commentEditText.getText().toString(), ratingBar.getRating(), photoURI, latitude, longitude);
    }

    @OnActivityResult(Constants.REQUEST_TAKE_PHOTO)
    void getPhotoDataFromCamera(int resultCode) {
        if (resultCode == RESULT_OK) {
            photoImageView.setImageURI(photoURI);
        }
    }

    @OnActivityResult(Constants.PICK_IMAGE)
    void getPhotoDataFromGallery(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (data != null) {
                photoURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoURI);
                    photoImageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    @Click(R.id.galleryImageButton)
    public void choosePhotoFromGallery() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
        startActivityForResult(chooserIntent, PICK_IMAGE);
    }

    @Override
    @Click(R.id.cameraImageButton)
    public void takePhoto() {
        dispatchTakePictureIntent();
    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(getActivity(), "Comment can not be empty!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeFragment() {
        getActivity().setResult(RESULT_OK);
        getActivity().finish();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.d("DetailsActivity", "Unable to create file");
            }

            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.example.usj.tuopinin.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        enterCommentPresenter.onDestroy();
    }
}

