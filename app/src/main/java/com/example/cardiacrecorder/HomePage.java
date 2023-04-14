package com.example.cardiacrecorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.cardiacrecorder.adapter.RvAdapter;
import com.example.cardiacrecorder.classes.EachData;
import com.example.cardiacrecorder.databinding.ActivityHomepageBinding;
import com.example.cardiacrecorder.others.PopUpListener;
import com.example.cardiacrecorder.roomDb.BoardViewModel;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {

    private ActivityHomepageBinding binding = null;
    private RvAdapter adapter = null;

    private BoardViewModel viewModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomepageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeRoomRv();
        setClickListener();

        //insertDummy();
    }

    /**
     * set click listener to buttons
     */
    private void setClickListener(){
        binding.fabAdd.setOnClickListener(view -> {
            startActivity(new Intent(this,AdderActivity.class));
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        });
    }


    /**
     * initializes adapter, view-model and observe all data from room
     */
    private void initializeRoomRv(){
        adapter = new RvAdapter(this);
        binding.rvList.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(BoardViewModel.class);

        viewModel.getAllData().observe(this, allData -> {
            if(allData == null || allData.isEmpty()){
                allData = new ArrayList<>();
            }
            adapter.submitList(allData);

            if(binding == null) return;
            if(allData.isEmpty()){
                binding.tvNoData.setVisibility(View.VISIBLE);
            }
            else{
                binding.tvNoData.setVisibility(View.GONE);
            }

        });

        adapter.setPopUpListener(new PopUpListener() {
            @Override
            public void onDeleteRequest(EachData data) {
                if(viewModel != null){
                    viewModel.delete(data);
                }
            }

            @Override
            public void onEditRequest(EachData data) {
                Intent intent = new Intent(HomePage.this,AdderActivity.class);
                intent.putExtra("data",data);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

}
