package com.example.cardiacrecorder;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
		String phone = String.valueOf(binding.editTextPhone.getText());
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

				String text = editable.toString();
				if(!text.startsWith("+88")){
					binding.editTextPhone.setText(getString(R.string._88));
					binding.editTextPhone.setSelection(3);
				}
				else{
					binding.tilPhone.setErrorEnabled(false);
					binding.tilPhone.setError(null);
				}

			}
		});

	}

	private void handleOTPSend(String phone){
		if(binding == null){
			showSafeToast(getString(R.string.something_went_wrong));
			return;
		}

		if(phone.isEmpty() || phone.equalsIgnoreCase("null")){
			binding.tilPhone.setErrorEnabled(true);
			binding.tilPhone.setError(getString(R.string.can_t_be_empty));
		}
		else if(phone.length() != 14){// 11 + 3[+88]
			binding.tilPhone.setErrorEnabled(true);
			binding.tilPhone.setError(getString(R.string.invalid_phone_number));
		}
		else{
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
