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

	/**
	 * login method on create and data setter
	 * @param savedInstanceState If the activity is being re-initialized after
	 *     previously being shut down then this Bundle contains the data it most
	 *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
	 *
	 */
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityLoginBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		setEditTextListener();
		setClickListener();
	}


	/**
	 * on click listener
	 */
	private void setClickListener(){
		if(binding == null) return;
		binding.buttonNext.setOnClickListener(view -> checkAndGo());
	}

	/**
	 * checker
	 */
	private void checkAndGo(){
		String phone = "+88"+ binding.editTextPhone.getText();
		handleOTPSend(phone);
	}

	/**
	 * toast shower
	 * @param message
	 */
	private void showSafeToast(String message){
		try {
			mToast.cancel();
			mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
			mToast.show();
		}catch (Exception ignored){}
	}

	/**
	 * edit text listener
	 */
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

	/**
	 * otp sender
	 * @param phone
	 */
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

	/**
	 * back pressed method
	 */
	@Override
	public void onBackPressed() {
		finishAffinity();
	}
}
