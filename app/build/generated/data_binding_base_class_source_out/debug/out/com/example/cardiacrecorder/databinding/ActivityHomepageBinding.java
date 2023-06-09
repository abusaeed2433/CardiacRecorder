// Generated by view binder compiler. Do not edit!
package com.example.cardiacrecorder.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.cardiacrecorder.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityHomepageBinding implements ViewBinding {
  @NonNull
  private final CoordinatorLayout rootView;

  @NonNull
  public final RelativeLayout bottomSheet;

  @NonNull
  public final ConstraintLayout clFilterOption;

  @NonNull
  public final CoordinatorLayout clHomeRoot;

  @NonNull
  public final ConstraintLayout constraintLayout;

  @NonNull
  public final FloatingActionButton fabAdd;

  @NonNull
  public final ImageView imageView8;

  @NonNull
  public final RecyclerView rvList;

  @NonNull
  public final TextView textViewType;

  @NonNull
  public final TextView tvNoData;

  @NonNull
  public final TextClock tvTime;

  @NonNull
  public final FilterOptionFullBinding viewBottomSheet;

  private ActivityHomepageBinding(@NonNull CoordinatorLayout rootView,
      @NonNull RelativeLayout bottomSheet, @NonNull ConstraintLayout clFilterOption,
      @NonNull CoordinatorLayout clHomeRoot, @NonNull ConstraintLayout constraintLayout,
      @NonNull FloatingActionButton fabAdd, @NonNull ImageView imageView8,
      @NonNull RecyclerView rvList, @NonNull TextView textViewType, @NonNull TextView tvNoData,
      @NonNull TextClock tvTime, @NonNull FilterOptionFullBinding viewBottomSheet) {
    this.rootView = rootView;
    this.bottomSheet = bottomSheet;
    this.clFilterOption = clFilterOption;
    this.clHomeRoot = clHomeRoot;
    this.constraintLayout = constraintLayout;
    this.fabAdd = fabAdd;
    this.imageView8 = imageView8;
    this.rvList = rvList;
    this.textViewType = textViewType;
    this.tvNoData = tvNoData;
    this.tvTime = tvTime;
    this.viewBottomSheet = viewBottomSheet;
  }

  @Override
  @NonNull
  public CoordinatorLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityHomepageBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityHomepageBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_homepage, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityHomepageBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bottom_sheet;
      RelativeLayout bottomSheet = ViewBindings.findChildViewById(rootView, id);
      if (bottomSheet == null) {
        break missingId;
      }

      id = R.id.clFilterOption;
      ConstraintLayout clFilterOption = ViewBindings.findChildViewById(rootView, id);
      if (clFilterOption == null) {
        break missingId;
      }

      CoordinatorLayout clHomeRoot = (CoordinatorLayout) rootView;

      id = R.id.constraintLayout;
      ConstraintLayout constraintLayout = ViewBindings.findChildViewById(rootView, id);
      if (constraintLayout == null) {
        break missingId;
      }

      id = R.id.fabAdd;
      FloatingActionButton fabAdd = ViewBindings.findChildViewById(rootView, id);
      if (fabAdd == null) {
        break missingId;
      }

      id = R.id.imageView8;
      ImageView imageView8 = ViewBindings.findChildViewById(rootView, id);
      if (imageView8 == null) {
        break missingId;
      }

      id = R.id.rvList;
      RecyclerView rvList = ViewBindings.findChildViewById(rootView, id);
      if (rvList == null) {
        break missingId;
      }

      id = R.id.text_view_type;
      TextView textViewType = ViewBindings.findChildViewById(rootView, id);
      if (textViewType == null) {
        break missingId;
      }

      id = R.id.tvNoData;
      TextView tvNoData = ViewBindings.findChildViewById(rootView, id);
      if (tvNoData == null) {
        break missingId;
      }

      id = R.id.tvTime;
      TextClock tvTime = ViewBindings.findChildViewById(rootView, id);
      if (tvTime == null) {
        break missingId;
      }

      id = R.id.view_bottom_sheet;
      View viewBottomSheet = ViewBindings.findChildViewById(rootView, id);
      if (viewBottomSheet == null) {
        break missingId;
      }
      FilterOptionFullBinding binding_viewBottomSheet = FilterOptionFullBinding.bind(viewBottomSheet);

      return new ActivityHomepageBinding((CoordinatorLayout) rootView, bottomSheet, clFilterOption,
          clHomeRoot, constraintLayout, fabAdd, imageView8, rvList, textViewType, tvNoData, tvTime,
          binding_viewBottomSheet);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
