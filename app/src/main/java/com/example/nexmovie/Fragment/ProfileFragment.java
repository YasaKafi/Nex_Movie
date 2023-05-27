package com.example.nexmovie.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.nexmovie.Authentication.LoginPage;
import com.example.nexmovie.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private String displayName;
    private String photoUrl;
    private static final String ARG_DISPLAY_NAME = "display_name";
    private static final String ARG_PHOTO_URL = "photo_url";

    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance(String displayName, String photoUrl) {
        ProfileFragment fragment = new ProfileFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_DISPLAY_NAME, displayName);
//        args.putString(ARG_PHOTO_URL, photoUrl);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            displayName = getArguments().getString(ARG_DISPLAY_NAME);
//            photoUrl = getArguments().getString(ARG_PHOTO_URL);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


//        TextView firstNameTextView = view.findViewById(R.id.first_name);
//        ImageView photoUrlImageView = view.findViewById(R.id.photoUrl);
//
//        if (displayName != null) {
//            firstNameTextView.setText(displayName);
//        }
//
//        if (photoUrl != null) {
//            Glide.with(this)
//                    .load(photoUrl)
//                    .apply(new RequestOptions()
//                            .placeholder(R.drawable.postermario) // placeholder jika gambar tidak tersedia
//                            .error(R.drawable.postermario)) // gambar error jika terjadi kesalahan
//                    .into(photoUrlImageView);
//        }

        Button logoutButton = view.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear email from Shared Preferences
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("google_email");
                editor.remove("username");
                editor.remove("password");
                editor.apply();

                // Intent to LoginActivity
                Intent intent = new Intent(getActivity(), LoginPage.class);
                startActivity(intent);
                getActivity().finish(); // Finish current activity (ProfileFragment)
            }
        });

        return view;
    }
}