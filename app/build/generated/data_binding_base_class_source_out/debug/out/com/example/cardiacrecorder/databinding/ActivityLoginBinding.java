// Generated by view binder compiler. Do not edit!
package com.example.cardiacrecorder.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.airbnb.lottie.LottieAnimationView;
import com.example.cardiacrecorder.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityLoginBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button buttonNext;

  @NonNull
  public final TextInputEditText editTextPhone;

  @NonNull
  public final LottieAnimationView lottieView;

  @NonNull
  public final TextView textView4;

  @NonNull
  public final TextInputLayout tilPhone;

  @NonNull
  public final TextView tvErrorMessage;

  @NonNull
  public final View view;

  private ActivityLoginBinding(@NonNull ConstraintLayout rootView, @NonNull Button buttonNext,
      @NonNull TextInputEditText editTextPhone, @NonNull LottieAnimationView lottieView,
      @NonNull TextView textView4, @NonNull TextInputLayout tilPhone,
      @NonNull TextView tvErrorMessage, @NonNull View view) {
    this.rootView = rootView;
    this.buttonNext = buttonNext;
    this.editTextPhone = editTextPhone;
    this.lottieView = lottieView;
    this.textView4 = textView4;
    this.tilPhone = tilPhone;
    this.tvErrorMessage = tvErrorMessage;
    this.view = view;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_login, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityLoginBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.buttonNext;
      Button buttonNext = ViewBindings.findChildViewById(rootView, id);
      if (buttonNext == null) {
        break missingId;
      }

      id = R.id.editTextPhone;
      TextInputEditText editTextPhone = ViewBindings.findChildViewById(rootView, id);
      if (editTextPhone == null) {
        break missingId;
      }

      id = R.id.lottie_view;
      LottieAnimationView lottieView = ViewBindings.findChildViewById(rootView, id);
      if (lottieView == null) {
        break missingId;
      }

      id = R.id.textView4;
      TextView textView4 = ViewBindings.findChildViewById(rootView, id);
      if (textView4 == null) {
        break missingId;
      }

      id = R.id.tilPhone;
      TextInputLayout tilPhone = ViewBindings.findChildViewById(rootView, id);
      if (tilPhone == null) {
        break missingId;
      }

      id = R.id.tvErrorMessage;
      TextView tvErrorMessage = ViewBindings.findChildViewById(rootView, id);
      if (tvErrorMessage == null) {
        break missingId;
      }

      id = R.id.view;
      View view = ViewBindings.findChildViewById(rootView, id);
      if (view == null) {
        break missingId;
      }

      return new ActivityLoginBinding((ConstraintLayout) rootView, buttonNext, editTextPhone,
          lottieView, textView4, tilPhone, tvErrorMessage, view);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}