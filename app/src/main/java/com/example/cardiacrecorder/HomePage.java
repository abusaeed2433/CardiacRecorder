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
import java.util.List;

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

    /**
     * initializes dummy values
     */
    private void insertDummy(){

        List<EachData> list = new ArrayList<>();
        list.add(new EachData(1L,"02/02/03","06:04AM",50,80,10,"c"));
        list.add(new EachData(2L,"04/02/03","05:04AM",60,80,20,null));
        list.add(new EachData(4L,"01/02/03","04:04AM",70,20,30,""));
        list.add(new EachData(3L,"05/02/03","03:04AM",80,30,40,"nai"));
        list.add(new EachData(9L,"12/05/03","02:04AM",90,10,50,"c"));
        list.add(new EachData(7L,"02/02/2023","01:04AM",230,10,70,"c"));

        for(EachData data : list){
            viewModel.insert(data);
        }
    }

}
