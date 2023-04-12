package com.example.cardiacrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cardiacrecorder.adapter.RvAdapter;
import com.example.cardiacrecorder.classes.EachData;
import com.example.cardiacrecorder.databinding.ActivityHomepageBinding;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {

    private ActivityHomepageBinding binding = null;
    private RvAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomepageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        List<EachData> list = new ArrayList<>();
        list.add(new EachData(1L,"02/02/03","06:04AM",50,80,10,"c"));
        list.add(new EachData(1L,"04/02/03","05:04AM",60,80,20,null));
        list.add(new EachData(1L,"01/02/03","04:04AM",70,20,30,""));
        list.add(new EachData(1L,"05/02/03","03:04AM",80,30,40,"nai"));
        list.add(new EachData(1L,"12/05/03","02:04AM",90,10,50,"c"));
        list.add(new EachData(1L,"02/02/2023","01:04AM",230,10,70,"c"));

        adapter = new RvAdapter(this);
        binding.rvList.setAdapter(adapter);

        adapter.submitList(list);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

}
