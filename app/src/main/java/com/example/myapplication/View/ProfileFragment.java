package com.example.myapplication.View;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import java.text.DecimalFormat;
import android.widget.ImageView;
import android.widget.Button;



import android.widget.TextView;

import com.example.myapplication.R;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    private ProgressBar progressBar;
    double weight;
    double goal;
    double percentage;
    private static final int PICK_IMAGE = 100;
    Uri imageURI;
    ImageView imageViewProfile;
    Button imageProfileButton;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private double computeGoal(double weight, double goal) {
        double percentage;

        if(weight == 0) {
            percentage = 0;
        } else if (goal == 0) {
            percentage = 0;
        } else if(weight > goal) { //wants to lose weight
            percentage = goal/weight;
        } else  {
            percentage = weight/goal; //wants to gain weight
        }

        return percentage * 100;

    }


        public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageViewProfile = view.findViewById(R.id.imageViewPicture);
        imageProfileButton = view.findViewById(R.id.imageButton);

        imageProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        final EditText userWeightt =  view.findViewById(R.id.editWeight);
        final EditText userGoalWeightt =  view.findViewById(R.id.editGoalWeight);
        progressBar =  view.findViewById(R.id.progressBar);
        final TextView percentageText = view.findViewById(R.id.percentageText);

        userWeightt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // userWeightt.setText("0");

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String userWeighttt = userWeightt.getText().toString();
                weight = Integer.parseInt(userWeighttt);

                percentage = computeGoal(weight, goal);
                progressBar.setProgress((int)percentage);
                percentageText.setText(new DecimalFormat("##.##").format(percentage) + "%");



            }
        });

        userGoalWeightt.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //  userGoalWeightt.setText("0");
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                //String userGoalWeighttt = userGoalWeightt.getText().toString();
                goal = Integer.parseInt(s.toString());


                percentage = computeGoal(weight, goal);
                Log.d("Editable: ", "value is : " + percentage);
                progressBar.setProgress((int)percentage);
                percentageText.setText(new DecimalFormat("##.##").format(percentage));

                percentageText.setText(new DecimalFormat("##.##").format(percentage) + "%");


            }
        });

        //percentage = computeGoal(weight, goal);
        //progressBar.setProgress(50);


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageURI = data.getData();
            imageViewProfile.setImageURI(imageURI);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


}
