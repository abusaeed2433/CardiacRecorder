package com.example.cardiacrecorder;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cardiacrecorder.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

	private Toast mToast;
	private ActivityLoginBinding binding = null;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityLoginBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		setEditTextListener();
		setClickListener();
	}


	private void setClickListener(){
		if(binding == null) return;
		binding.buttonNext.setOnClickListener(view -> checkAndGo());
	}

	private void checkAndGo(){
		String phone = "+88"+ binding.editTextPhone.getText();
		handleOTPSend(phone);
	}

	private void showSafeToast(String message){
		try {
			mToast.cancel();
			mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
			mToast.show();
		}catch (Exception ignored){}
	}

	private void setEditTextListener(){
		binding.editTextPhone.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void afterTextChanged(Editable editable) {
				if(binding == null) return;
				binding.tvErrorMessage.setVisibility(View.INVISIBLE);
			}
		});
	}

	private void handleOTPSend(String phone){
		if(binding == null){
			showSafeToast(getString(R.string.something_went_wrong));
			return;
		}

		if(phone.length() != 14){// 11 + 3[+88]
			binding.tvErrorMessage.setVisibility(View.VISIBLE);
			binding.tvErrorMessage.setText(getString(R.string.invalid_phone_number));
		}
		else{
			for(int i=1; i<phone.length(); i++){
				if(phone.charAt(i) < '0' || phone.charAt(i) > '9'){
					binding.tvErrorMessage.setVisibility(View.VISIBLE);
					binding.tvErrorMessage.setText(getString(R.string.invalid_phone_number));
					return;
				}
			}

			Intent intent = new Intent(this, OTPActivity.class);
			intent.putExtra("phone", phone);
			startActivity(intent);
		}
	}

	@Override
	public void onBackPressed() {
		finishAffinity();
	}
}
