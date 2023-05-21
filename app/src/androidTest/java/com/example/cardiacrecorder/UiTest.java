package com.example.cardiacrecorder;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class UiTest {

    @Rule
    public ActivityScenarioRule<HomePage> activityRule = new ActivityScenarioRule<>(HomePage.class);

    @Test
    public void testAppName() {
        onView(withText(R.string.app_name)).check(matches(isDisplayed()));
    }


    @Test
    public void deleteAll(){
//        while (true) {
//            // Check if the RecyclerView is empty
//            if (getRecyclerViewItemCount() == 0) {
//                // RecyclerView is empty, exit the loop
//                break;
//            }
//
//            // Click the button in the RecyclerView item
//            onView(withId(R.id.your_button_id)).perform(click());
//
//            // Click the "Delete" option in the popup menu
//            onView(withText("Delete")).perform(click());
//        }
    }

    private int getRecyclerViewItemCount() {
        final AtomicInteger itemCount = new AtomicInteger();
        //onView(withId(R.id.rvList)).perform(new ViewAction[]{})

        return itemCount.get();
    }

}
