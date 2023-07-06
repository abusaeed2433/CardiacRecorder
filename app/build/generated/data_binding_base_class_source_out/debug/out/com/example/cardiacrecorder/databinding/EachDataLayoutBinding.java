// Generated by view binder compiler. Do not edit!
package com.example.cardiacrecorder.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.cardiacrecorder.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class EachDataLayoutBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final ImageView ivDysUnusual;

  @NonNull
  public final ImageView ivMore;

  @NonNull
  public final ImageView ivSysUnusual;

  @NonNull
  public final TextView tvDate;

  @NonNull
  public final TextView tvDysPressure;

  @NonNull
  public final TextView tvHeartRate;

  @NonNull
  public final TextView tvSysPressure;

  private EachDataLayoutBinding(@NonNull CardView rootView, @NonNull ImageView ivDysUnusual,
      @NonNull ImageView ivMore, @NonNull ImageView ivSysUnusual, @NonNull TextView tvDate,
      @NonNull TextView tvDysPressure, @NonNull TextView tvHeartRate,
      @NonNull TextView tvSysPressure) {
    this.rootView = rootView;
    this.ivDysUnusual = ivDysUnusual;
    this.ivMore = ivMore;
    this.ivSysUnusual = ivSysUnusual;
    this.tvDate = tvDate;
    this.tvDysPressure = tvDysPressure;
    this.tvHeartRate = tvHeartRate;
    this.tvSysPressure = tvSysPressure;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static EachDataLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static EachDataLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.each_data_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static EachDataLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.ivDysUnusual;
      ImageView ivDysUnusual = ViewBindings.findChildViewById(rootView, id);
      if (ivDysUnusual == null) {
        break missingId;
      }

      id = R.id.ivMore;
      ImageView ivMore = ViewBindings.findChildViewById(rootView, id);
      if (ivMore == null) {
        break missingId;
      }

      id = R.id.ivSysUnusual;
      ImageView ivSysUnusual = ViewBindings.findChildViewById(rootView, id);
      if (ivSysUnusual == null) {
        break missingId;
      }

      id = R.id.tvDate;
      TextView tvDate = ViewBindings.findChildViewById(rootView, id);
      if (tvDate == null) {
        break missingId;
      }

      id = R.id.tvDysPressure;
      TextView tvDysPressure = ViewBindings.findChildViewById(rootView, id);
      if (tvDysPressure == null) {
        break missingId;
      }

      id = R.id.tvHeartRate;
      TextView tvHeartRate = ViewBindings.findChildViewById(rootView, id);
      if (tvHeartRate == null) {
        break missingId;
      }

      id = R.id.tvSysPressure;
      TextView tvSysPressure = ViewBindings.findChildViewById(rootView, id);
      if (tvSysPressure == null) {
        break missingId;
      }

      return new EachDataLayoutBinding((CardView) rootView, ivDysUnusual, ivMore, ivSysUnusual,
          tvDate, tvDysPressure, tvHeartRate, tvSysPressure);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}