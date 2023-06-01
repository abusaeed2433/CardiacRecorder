package com.example.cardiacrecorder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.unknownn.rentroom.R;

public class LoginActivity extends Fragment {

	private RelativeLayout rlMess, rlService;
	private TextInputLayout tilPhone;
	private TextInputEditText editTextPhone;
	private Button buttonNext,buttonCreateAccount;
	private Toast mToast;
	private Boolean isMessSelected = null;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
	                         @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_login,container,false);
	}

	@Override
	public void onViewCreated(@NonNull View mView, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(mView, savedInstanceState);

		rlMess = mView.findViewById(R.id.rl_mess);
		rlService = mView.findViewById(R.id.rl_service);
		tilPhone = mView.findViewById(R.id.tilPhone);
		editTextPhone = mView.findViewById(R.id.editTextPhone);
		editTextPhone.setSelection(3);//moving cursor after +88|
		buttonNext = mView.findViewById(R.id.buttonNext);
		buttonCreateAccount = mView.findViewById(R.id.buttonCreateAccount);

		if(savedInstanceState != null){
			isMessSelected = savedInstanceState.getBoolean("is_mess_selected",false);
			if(isMessSelected){
				rlMess.setBackgroundResource(R.drawable.shadow_down_ripple);
				rlService.setBackgroundResource(R.drawable.shadow_up_ripple);
			}
			else{
				rlService.setBackgroundResource(R.drawable.shadow_down_ripple);
				rlMess.setBackgroundResource(R.drawable.shadow_up_ripple);
			}
		}

		setEditTextListener();
		setClickListener();

	}


	private void setClickListener(){

		rlMess.setOnClickListener(view -> {
			if(isMessSelected != null && !isMessSelected){// if service is selected
				rlService.setBackgroundResource(R.drawable.shadow_up_ripple);
			}
			rlMess.setBackgroundResource(R.drawable.shadow_down_ripple);
			isMessSelected = true;
		});

		rlService.setOnClickListener(view -> {
			if(isMessSelected != null && isMessSelected){// if mess is selected
				rlMess.setBackgroundResource(R.drawable.shadow_up_ripple);
			}
			rlService.setBackgroundResource(R.drawable.shadow_down_ripple);
			isMessSelected = false;
		});

		buttonNext.setOnClickListener(view -> checkAndGo(true));

		buttonCreateAccount.setOnClickListener(view -> checkAndGo(false));
	}


	private void checkAndGo(boolean isFromLogin){
		if(isMessSelected == null){
			showSafeToast(getString(R.string.select_your_field));
			return;
		}

		String phone = String.valueOf(editTextPhone.getText());
		String category = isMessSelected ? "mess" : "service";
		handleOTPSend(phone,isFromLogin,category);
	}

	private void showSafeToast(String message){
		try {
			mToast.cancel();
		}catch (Exception ignored){}
		try {
			Activity activity = getActivity();
			if(activity!=null) {
				mToast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
				mToast.show();
			}
		}catch (Exception ignored){}
	}

	private void setEditTextListener(){
		editTextPhone.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void afterTextChanged(Editable editable) {
				String text = editable.toString();
				if(!text.startsWith("+88")){
					editTextPhone.setText(getString(R.string._88));
					editTextPhone.setSelection(3);
				}
				else{
					tilPhone.setErrorEnabled(false);
					tilPhone.setError(null);
				}
			}
		});
	}

	private void handleOTPSend(String phone, boolean isFromLogin, String category){
		if(phone.isEmpty() || phone.equalsIgnoreCase("null")){
			tilPhone.setErrorEnabled(true);
			tilPhone.setError(getString(R.string.can_t_be_empty));
		}
		else if(phone.length()!=14){// 11 + 3[+88]
			tilPhone.setErrorEnabled(true);
			tilPhone.setError(getString(R.string.invalid_phone_number));
		}
		else{
			Activity activity = getActivity();
			if(activity!=null) {
				Intent intent = new Intent(activity, com.unknownn.rentroom.OTPActivity.class);
				intent.putExtra("phone", phone);
				intent.putExtra("is_from_login",isFromLogin);
				intent.putExtra("category",category);
				startActivity(intent);
			}
			else{
				showSafeToast(getString(R.string.something_went_wrong_restart_app));
			}
		}
	}

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		if(isMessSelected != null) {
			outState.putBoolean("is_mess_selected",isMessSelected);
		}
	}

}
