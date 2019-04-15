package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

public class ActivityFragment extends Fragment {
    GridLayout mainGrid;
    FloatingActionButton fab;

    public static ActivityFragment newInstance() {
        return new ActivityFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_activity, container, false);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Main Grid
        mainGrid = view.findViewById(R.id.mainGrid);

        // Floating Action Button
        fab = view.findViewById(R.id.fab);
        setSingleEvent(mainGrid);

        // Floating Action Button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Create new workout", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setSingleEvent(GridLayout mainGrid) {
        //Log.d("GRID LAYOUT", "value " + mainGrid.getChildCount());
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            /*if (mainGrid.getChildAt(2) == null) {
                Log.d("getChildAt: ", "returns null");
            } else {
                Log.d("getChildAt: ", "NOT null");
            }*/
            CardView cardView = (CardView)mainGrid.getChildAt(i);
            final int selected = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selected == 0) {
                        Intent intent = new Intent(getActivity(), WorkoutListFragment.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getActivity(), "Has not been implemented", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

}
