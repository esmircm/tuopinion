package com.example.usj.tuopinin.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.usj.tuopinin.R;

import static com.example.usj.tuopinin.Constants.FRAGMENT_NAME;
import static com.example.usj.tuopinin.Constants.LATITUDE;
import static com.example.usj.tuopinin.Constants.LONGITUDE;
import static com.example.usj.tuopinin.Constants.REGISTER_LOCATION;
import static com.example.usj.tuopinin.Constants.SAVE_PLACE;

public class BottomRegisterFragment extends BottomSheetDialogFragment {

    private double latitude;
    private double longitude;

    public static BottomRegisterFragment newInstance(double latitude, double longitude) {
        BottomRegisterFragment bottomRegisterFragment = new BottomRegisterFragment();
        Bundle bundle = new Bundle();
        bundle.putDouble(LATITUDE, latitude);
        bundle.putDouble(LONGITUDE, longitude);
        bottomRegisterFragment.setArguments(bundle);
        return bottomRegisterFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bottom_register, container,
                false);
        latitude = getArguments().getDouble(LATITUDE);
        longitude = getArguments().getDouble(LONGITUDE);

        Button registerButton = view.findViewById(R.id.register_button);
        registerButton.setOnClickListener(buttonView -> {
            Intent intent = new Intent(getActivity(), AddLocationDetailsActivity_.class);
            intent.putExtra(LATITUDE, latitude);
            intent.putExtra(LONGITUDE, longitude);
            intent.putExtra(FRAGMENT_NAME, REGISTER_LOCATION);
            getActivity().startActivityForResult(intent, SAVE_PLACE);
            getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            dismiss();
        });

        return view;

    }
}


