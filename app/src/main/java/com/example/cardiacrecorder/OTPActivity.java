package com.example.cardiacrecorder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.cardiacrecorder.classes.DataModel;
import com.example.cardiacrecorder.classes.EachData;
import com.example.cardiacrecorder.others.CallBackUserChecker;
import com.example.cardiacrecorder.roomDb.BoardViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity implements CallBackUserChecker {
	private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
	//private PhoneAuthProvider.ForceResendingToken mResendToken;
	private Dialog mainDialog;
	private String codeByGoogle;

	private com.example.cardiacrecorder.databinding.ActivityOtpBinding binding = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = com.example.cardiacrecorder.databinding.ActivityOtpBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		ActionBar actionBar = getSupportActionBar();

		if(actionBar != null){
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		initializeCallBack();

		String phone = getIntent().getStringExtra("phone");
		binding.textViewAboutOTP.setText(getString(R.string.verification_message,phone));

		showProgress(false);
		sendVerificationCode(phone);

		binding.buttonVerify.setOnClickListener(view -> {
			String enteredCode = String.valueOf(binding.editTextOTP.getText());
			if(!enteredCode.isEmpty() && !enteredCode.equalsIgnoreCase("null")){
				binding.buttonVerify.setEnabled(false);
				showProgress(true);
				binding.buttonVerify.setEnabled(true);
				verifyCode(enteredCode);
			}
			else{
				showSnackBar(getString(R.string.enter_otp_first));
			}
		});
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			super.onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void showSnackBar(String message){
		Snackbar snackbar = Snackbar.make(findViewById(R.id.constraintLayoutLogin),message,Snackbar.LENGTH_SHORT);
		snackbar.setAction(android.R.string.ok, view -> snackbar.dismiss());
		snackbar.show();
	}

	private void checkUserExistence(String userId){
		DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("data").child(userId);
		ref.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				isUserAvailable(snapshot.exists());
			}
			@Override
			public void onCancelled(@NonNull DatabaseError error) {
				isUserAvailable(null);
			}
		});
	}

	private void signInUserWithCredential(PhoneAuthCredential credential){
		FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
		firebaseAuth.signInWithCredential(credential)
				.addOnSuccessListener(authResult -> {
					FirebaseUser user = authResult.getUser();
					if(user == null){
						showAlertDialog( getString(R.string.error_occurred), getString(R.string.something_went_wrong),false);
						return;
					}
					checkUserExistence(user.getUid());
				}).addOnFailureListener(e -> {
					dismissMainDialog();
					String errorCode = "-1";
					try {
						errorCode = ((FirebaseAuthInvalidCredentialsException)e).getErrorCode();
					}catch (Exception ignored){}

					switch (errorCode) {
						case "ERROR_INVALID_PHONE_NUMBER":
							showAlertDialog( getString(R.string.error_occurred),
									getString(R.string.about_invalid_phone_number),true);
							break;
						case "ERROR_INVALID_VERIFICATION_CODE":
							showAlertDialog(getString(R.string.error_occurred)
									,getString(R.string.invalid_otp_recheck_and_try_again),false);
							break;
						case "ERROR_SESSION_EXPIRED":
							showAlertDialog(getString(R.string.error_occurred),getString(R.string.session_expired_try_again),true);
							break;
						default:
							showAlertDialog(getString(R.string.error_occurred),e.getMessage(),true);
			}
		});
	}

	private void verifyCode(String code){
		try{
			PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeByGoogle,code);
			signInUserWithCredential(credential);
		}
		catch (Exception e){
			dismissMainDialog();
			showAlertDialog(getString(R.string.error_occurred),getString(R.string.something_went_wrong),true);
		}
	}

	private void sendVerificationCode(String phone){
		PhoneAuthOptions options =
				PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
						.setPhoneNumber(phone)// Phone number to verify
						.setTimeout(30L, TimeUnit.SECONDS)// Timeout and unit
						.setActivity(this)// Activity (for callback binding)
						.setCallbacks(mCallbacks) // OnVerificationStateChangedCallbacks
						.build();
		PhoneAuthProvider.verifyPhoneNumber(options);
	}

	private void initializeCallBack(){
		mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
			@Override
			public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
				String code = credential.getSmsCode(); // code from sms, automatically retrieved by google,
				binding.editTextOTP.setText(code);
				binding.buttonVerify.setEnabled(false);//disabling button, so that user can't click
				showProgress(true);
				binding.buttonVerify.setEnabled(true);
				verifyCode(code);
			}

			@Override
			public void onVerificationFailed(@NonNull FirebaseException e) {
				dismissMainDialog();
				String errorCode = "-1";
				try {
					errorCode = ((FirebaseAuthInvalidCredentialsException)e).getErrorCode();
				}catch (Exception ignored){}

				switch (errorCode) {
					case "ERROR_INVALID_PHONE_NUMBER":
						showAlertDialog(getString(R.string.error_occurred),
								getString(R.string.about_invalid_phone_number),false);
						break;
					case "ERROR_INVALID_VERIFICATION_CODE":
						showAlertDialog(getString(R.string.invalid_otp),
								getString(R.string.invalid_otp_recheck_and_try_again),false);
						break;
					case "ERROR_SESSION_EXPIRED":
						showAlertDialog(getString(R.string.error_occurred),getString(R.string.session_expired_try_again),false);
						break;
					default:
						showAlertDialog(getString(R.string.error_occurred),e.getMessage(),true);
						break;
				}
			}

			@Override
			public void onCodeSent(@NonNull String verificationId,
			                       @NonNull PhoneAuthProvider.ForceResendingToken token) {
				codeByGoogle = verificationId; //sent by google not from sms
				//mResendToken = token;
				dismissMainDialog();
			}
		};
	}

	private void showAlertDialog(String title,String message, boolean shouldExit){
		new AlertDialog.Builder(this)
				.setTitle(title)
				.setMessage(message)
				.setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
					dialogInterface.dismiss();
					if(shouldExit) finish();
				})
				.setCancelable(false)
				.show();
	}

	private void dismissMainDialog(){
		try {
			mainDialog.dismiss();
		}catch (Exception ignored){}
	}

	private void showProgress(boolean isFromVerify) {
		mainDialog = new Dialog(this);
		mainDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mainDialog.setContentView(R.layout.custom_progress_bar);

		TextView textViewPercent = mainDialog.findViewById(R.id.textViewPercent);
		textViewPercent.setVisibility(View.INVISIBLE);
		TextView textViewAboutUpload = mainDialog.findViewById(R.id.textViewAboutUpload);
		if(isFromVerify) textViewAboutUpload.setText(R.string.verifying_otp);
		else textViewAboutUpload.setText(getString(R.string.sending_otp));

		Window window = mainDialog.getWindow();
		if(window!=null){
			window.setBackgroundDrawableResource(android.R.color.transparent);
			window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		mainDialog.setCanceledOnTouchOutside(false);
		mainDialog.setCancelable(false);
		mainDialog.show();
	}

	@Override
	public void isUserAvailable(Boolean isUserAvailable) {
		if(isUserAvailable == null) {
			dismissMainDialog();
			showAlertDialog(getString(R.string.error_occurred), getString(R.string.something_went_wrong),true);
		}
		else{
			downloadData((error, allData) -> {

				dismissMainDialog();

				if(error != null){
					showAlertDialog("Error",error,false);
					return;
				}

				BoardViewModel viewModel = new ViewModelProvider(this).get(BoardViewModel.class);
				for(EachData data : allData){
					viewModel.insert(data);
				}

				SharedPreferences sp = getSharedPreferences("sp",MODE_PRIVATE);
				SharedPreferences.Editor editor = sp.edit();
				editor.putBoolean("amILoggedIn",true);
				editor.apply();

				startActivity(new Intent(OTPActivity.this,HomePage.class));
				overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
			});
		}
	}

	private void downloadData(DataListener listener){

		FirebaseAuth auth = FirebaseAuth.getInstance();
		String userId = auth.getUid();

		if(userId == null){
			listener.onDataDownloaded(getString(R.string.failed_to_authenticate),null);
			return;
		}

		DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("data").child(userId);

		ref.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				List<EachData> list = new ArrayList<>();
				if(snapshot.exists()) {
					for (DataSnapshot ds : snapshot.getChildren()) {
						try {
							DataModel model = ds.getValue(DataModel.class);
							if(model != null){
								list.add(new EachData(model));
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				listener.onDataDownloaded(null,list);
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {
				listener.onDataDownloaded(error.getMessage(),null);
			}
		});
	}

	private interface DataListener{
		void onDataDownloaded(String error, List<EachData> allData);
	}

}
