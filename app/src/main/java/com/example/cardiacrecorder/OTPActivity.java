package com.example.cardiacrecorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.unknownn.rentroom.others.CallBackUserChecker;

import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity implements CallBackUserChecker {

	private ConstraintLayout constraintLayoutLogin;
	private TextView textViewAboutOTP;
	private TextInputLayout tilOTP;
	private TextInputEditText editTextOTP;
	private Button buttonVerify;
	private String phone;
	private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
	private PhoneAuthProvider.ForceResendingToken mResendToken;
	private Dialog mainDialog;
	private String codeByGoogle;
	private DataSaver dataSaver = null;
	private boolean isFromLogin = true;
	private String category;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_otp);

		constraintLayoutLogin = findViewById(R.id.constraintLayoutLogin);
		textViewAboutOTP = findViewById(R.id.textViewAboutOTP);
		tilOTP = findViewById(R.id.tilOTP);
		editTextOTP = findViewById(R.id.editTextOTP);
		buttonVerify = findViewById(R.id.buttonVerify);

		initializeCallBack();

		phone = getIntent().getStringExtra("phone");
		isFromLogin = getIntent().getBooleanExtra("is_from_login",true);
		category = getIntent().getStringExtra("category");
		textViewAboutOTP.setText(getString(R.string.verification_message,phone));

		showProgress(false);
		sendVerificationCode(phone);

		buttonVerify.setOnClickListener(view -> {
			String enteredCode = String.valueOf(editTextOTP.getText());
			if(!enteredCode.isEmpty() && !enteredCode.equalsIgnoreCase("null")){
				buttonVerify.setEnabled(false);
				showProgress(true);
				buttonVerify.setEnabled(true);
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
		snackbar.setAction(R.string.ok, view -> snackbar.dismiss());
		snackbar.show();
	}

	private void checkUserExistence(String userId){
		DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
		ref.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				if(snapshot.exists()){
					if(snapshot.child("category").exists()){
						String val = String.valueOf(snapshot.child("category").getValue());
						isUserAvailable(val);
					}
					else{
						if(snapshot.child("phone").exists())
							isUserAvailable("4");//user available
						else
							isUserAvailable("0");
					}
				}
				else isUserAvailable("0");//user not available
			}
			@Override
			public void onCancelled(@NonNull DatabaseError error) {
				isUserAvailable("-1");//error occurred
			}
		});
	}

	private void signInUserWithCredential(PhoneAuthCredential credential){
		FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
		firebaseAuth.signInWithCredential(credential)
				.addOnSuccessListener(authResult ->
						checkUserExistence(firebaseAuth.getUid())
				).addOnFailureListener(e -> {
					dismissMainDialog();
					String errorCode = "-1";
					try {
						errorCode = ((FirebaseAuthInvalidCredentialsException)e).getErrorCode();
					}catch (Exception ignored){}

					switch (errorCode) {
						case "ERROR_INVALID_PHONE_NUMBER":
							showAlertDialog( getString(R.string.error_occurred),
									getString(R.string.about_invalid_phone_number));
							break;
						case "ERROR_INVALID_VERIFICATION_CODE":
							showAlertDialog(getString(R.string.error_occurred)
									,getString(R.string.invalid_otp_recheck_and_try_again));
							break;
						case "ERROR_SESSION_EXPIRED":
							showAlertDialog(getString(R.string.error_occurred),getString(R.string.session_expired_try_again));
							break;
						default:
							showAlertDialog(getString(R.string.error_occurred),e.getMessage());
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
			showAlertDialog(getString(R.string.error_occurred),getString(R.string.something_went_wrong));
		}
	}

	private void sendVerificationCode(String phone){
		PhoneAuthOptions options =
				PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
						.setPhoneNumber(phone)// Phone number to verify
						.setTimeout(30L, TimeUnit.SECONDS)// Timeout and unit
						.setActivity(com.unknownn.rentroom.OTPActivity.this)// Activity (for callback binding)
						.setCallbacks(mCallbacks)      // OnVerificationStateChangedCallbacks
						.build();
		PhoneAuthProvider.verifyPhoneNumber(options);
	}

	private void initializeCallBack(){
		mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
			@Override
			public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
				String code = credential.getSmsCode(); // code from sms, automatically retrieved by google,
				editTextOTP.setText(code);
				buttonVerify.setEnabled(false);//disabling button, so that user can't click
				showProgress(true);
				buttonVerify.setEnabled(true);
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
								getString(R.string.about_invalid_phone_number));
						break;
					case "ERROR_INVALID_VERIFICATION_CODE":
						showAlertDialog(getString(R.string.invalid_otp),
								getString(R.string.invalid_otp_recheck_and_try_again));
						break;
					case "ERROR_SESSION_EXPIRED":
						showAlertDialog(getString(R.string.error_occurred),getString(R.string.session_expired_try_again));
						break;
					default:
						showAlertDialog(getString(R.string.error_occurred),e.getMessage());
						break;
				}
			}

			@Override
			public void onCodeSent(@NonNull String verificationId,
			                       @NonNull PhoneAuthProvider.ForceResendingToken token) {
				codeByGoogle = verificationId; //sent by google not from sms
				mResendToken = token;
				dismissMainDialog();
			}
		};
	}

	private void showAlertDialog(String title,String message){
		new AlertDialog.Builder(com.unknownn.rentroom.OTPActivity.this)
				.setTitle(title)
				.setMessage(message)
				.setPositiveButton(R.string.ok, (dialogInterface, i) -> dialogInterface.dismiss())
				.setCancelable(false)
				.show();
	}

	private void dismissMainDialog(){
		try {
			mainDialog.dismiss();
		}catch (Exception ignored){}
	}

	private void showProgress(boolean isFromVerify) {
		mainDialog = new Dialog(com.unknownn.rentroom.OTPActivity.this);
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
	public void isUserAvailable(String type) {
		dismissMainDialog();
		if(type.equals("-1")) {
			showAlertDialog(getString(R.string.error_occurred), getString(R.string.something_went_wrong));
		}
		else if(type.equals("0")){// user not available

			if(isFromLogin){ // will show alert
				new AlertDialog.Builder(com.unknownn.rentroom.OTPActivity.this)
						.setTitle(getString(R.string.phone_number_not_found))
						.setMessage(getString(R.string.about_create_account_phone_not_found))
						.setCancelable(false)
						.setPositiveButton(R.string.create_account, (dialog, which) -> takeToProperActivity())
						.setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
							dialogInterface.dismiss();
							finish();//just closing the activity
						})
						.show();
			}
			else{ // normal flow to ServiceCreatorAccount
				takeToProperActivity();
			}
		}
		else{//user available
			if(isFromLogin){
				Integer val = Integer.parseInt(type);
				getDataSaver().saveIsLoggedIn();
				getDataSaver().saveHasLoggedInNow();
				getDataSaver().saveWhoAmI(val);
				finish();//going back to homepage; onResume will be called and fragment will be replaced by as needed
			}
			else{
				new AlertDialog.Builder(com.unknownn.rentroom.OTPActivity.this)
						.setTitle(getString(R.string.phone_number_exists))
						.setMessage(getString(R.string.about_phone_number_exists))
						.setCancelable(false)
						.setPositiveButton(R.string.ok, (dialog, which) -> {

							Integer val = Integer.parseInt(type);
							DataSaver dataSaver = new DataSaver(this);
							dataSaver.saveIsLoggedIn();
							getDataSaver().saveHasLoggedInNow();
							dataSaver.saveWhoAmI(val);

							Intent intent = new Intent(com.unknownn.rentroom.OTPActivity.this, HomePage.class);
							intent.putExtra("forceExit",true);
							startActivity(intent);
						})
						.setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
							dialogInterface.dismiss();
							finish();//just closing the activity
						})
						.show();
			}
		}
	}

	private void takeToProperActivity(){
		Intent intent;
		if(category.equalsIgnoreCase("service")){
			intent = new Intent(com.unknownn.rentroom.OTPActivity.this, ServiceCreatorActivity.class);
			intent.putExtra("category",category);
		}
		else{
			intent = new Intent(com.unknownn.rentroom.OTPActivity.this,CompleteCreateAccount.class);
		}
		intent.putExtra("phone",phone);
		startActivity(intent);
	}

	private DataSaver getDataSaver(){
		if(dataSaver == null){
			dataSaver = new DataSaver(this);
		}
		return dataSaver;
	}
}
