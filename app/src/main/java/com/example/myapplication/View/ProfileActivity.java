package com.example.myapplication.View;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.Utilities.OnSwipeTouchListener;

import java.text.DecimalFormat;

public class ProfileActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    double weight;
    double goal;
    double percentage;
    private static final int PICK_IMAGE = 100;
    Uri imageURI;
    ImageView imageViewProfile;
    Button imageProfileButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);
        Toolbar toolbar = findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);
        imageViewProfile = findViewById(R.id.imageViewPicture);
        imageProfileButton = findViewById(R.id.imageButton);

        imageProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        final EditText userWeightt =  findViewById(R.id.editWeight);
        final EditText userGoalWeightt =  findViewById(R.id.editGoalWeight);
        progressBar =  findViewById(R.id.progressBar);
        final TextView percentageText = findViewById(R.id.percentageText);

        // Swipe to exit code (in progress)
        /*findViewById(R.id.profile_layout).setOnTouchListener(new OnSwipeTouchListener(ProfileActivity.this) {
            public void onSwipeTop() {
                //Toast.makeText(ProfileActivity.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                onBackPressed();
                //Toast.makeText(ProfileActivity.this, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                //Toast.makeText(ProfileActivity.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                //Toast.makeText(ProfileActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }
        });*/

        // Top bar with back button
        getSupportActionBar().setTitle("Profile");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

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
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageURI = data.getData();
            imageViewProfile.setImageURI(imageURI);
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
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

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

}



