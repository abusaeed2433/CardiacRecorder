package com.example.cardiacrecorder;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import java.util.Objects;

public class UiTest {

    private final String SYS = "62", SYS_NEW = "67";
    private final String DYS = "102", DYS_NEW = "95";
    private final String HEART = "80", HEART_NEW = "97";
    private final String COMMENT = "No comment", COMMENT_NEW = "Modified wrong entry";

    @Rule
    public ActivityScenarioRule<HomePage> activityRule = new ActivityScenarioRule<>(HomePage.class);


    @Test
    public void testAppName() {
        onView(withText(R.string.app_name)).check(matches(isDisplayed()));
    }

    @Test
    public void runAllTest(){
        testDeleteAll();

        addData();
        testDetails();

        testSimpleFilter();
        testResetFilter();

        editData();
        testSingleDelete();

        addData();
        addData();
        testDeleteAll();
    }


    @Test
    public void addData(){

        onView(withId(R.id.fabAdd)).perform(click());

        onView(withId(R.id.editTextDt)).perform(click());

//        onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker.class.getName())))
//                .perform(PickerActions.setDate(2023, 10, 10));

        onView(ViewMatchers.withText("OK")).perform(click());

        onView(withId(R.id.editTextTime)).perform(click());

        onView(ViewMatchers.withText("OK")).perform(click());


        onView(withId(R.id.editTextSysPressure)).perform(ViewActions.typeText(SYS));

        onView(withId(R.id.editTextDysPressure)).perform(ViewActions.typeText(DYS));

        onView(withId(R.id.editTextHeartRate)).perform(ViewActions.typeText(HEART));

        onView(withId(R.id.editTextComment)).perform(ViewActions.typeText(COMMENT));

        Espresso.pressBack(); //hide keyboard

        int prevCount = getTotalItem();

        onView(withId(R.id.buttonSave)).perform(click());

        Espresso.pressBack();
        safeSleep(2);

        int curCount = getTotalItem();

        assertEquals(prevCount,curCount-1);

    }

    @Test
    public void testDetails(){

        int index = getTotalItem()-1;

        onView(withId(R.id.rvList)).perform(RecyclerViewActions.actionOnItemAtPosition(index, click()));

        onView(withId(R.id.DetailsSysPressure)).check(matches(isDisplayed()));

        onView(withText(SYS +"mm Hg")).check(matches(isDisplayed()));
        onView(withText(DYS +"mm Hg")).check(matches(isDisplayed()));
        onView(withText(HEART +"BPM")).check(matches(isDisplayed()));
        onView(withText(COMMENT)).check(matches(isDisplayed()));

        Espresso.pressBack();

    }

    @Test
    public void editData(){

        onView(withId(R.id.rvList))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,MyViewAction.clickChildViewWithId(R.id.ivMore)));

        onView(withText("Edit")).inRoot(isPlatformPopup()).perform(click());

        onView(withId(R.id.editTextSysPressure)).perform(clearText());
        onView(withId(R.id.editTextSysPressure)).perform(ViewActions.typeText(SYS_NEW));

        onView(withId(R.id.editTextDysPressure)).perform(clearText());
        onView(withId(R.id.editTextDysPressure)).perform(ViewActions.typeText(DYS_NEW));

        onView(withId(R.id.editTextHeartRate)).perform(clearText());
        onView(withId(R.id.editTextHeartRate)).perform(ViewActions.typeText(HEART_NEW));

        onView(withId(R.id.editTextComment)).perform(clearText());
        onView(withId(R.id.editTextComment)).perform(ViewActions.typeText(COMMENT_NEW));

        Espresso.pressBack(); //hide keyboard

        int prevCount = getTotalItem();

        onView(withId(R.id.buttonSave)).perform(click());

        Espresso.pressBack();
        safeSleep(2);

        int curCount = getTotalItem();

        assertEquals(prevCount,curCount);
    }

    @Test
    public void testSingleDelete(){
        onView(withId(R.id.rvList)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.ivMore))
        );
        int prevCount = getTotalItem();

        onView(withText("Delete")).inRoot(isPlatformPopup()).perform(click());

        int curCount = getTotalItem();

        assertEquals(prevCount,curCount+1);

    }


    @Test
    public void testDeleteAll(){

        while (true){
            try {
                onView(withId(R.id.rvList)).perform(
                        RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.ivMore))
                );
                onView(withText("Delete"))
                        .inRoot(isPlatformPopup())
                        .perform(click());
                safeSleep(2);
            }catch (Exception ignored){
                break;
            }
        }

        onView(withId(R.id.tvNoData)).check(matches(isDisplayed()));
    }


    @Test
    public void testResetFilter(){
        onView(withId(R.id.clFilterOption)).perform(click());

        safeSleep(2);
        onView(withId(R.id.rvSys)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.rvDys)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        onView(withId(R.id.rvHeartRate)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        onView(withId(R.id.buttonSaveFilter)).perform(click());

        onView(withId(R.id.tvNoData)).check(matches(not(isDisplayed())));
    }
    @Test
    public void testSimpleFilter(){
        onView(withId(R.id.clFilterOption)).perform(click());

        safeSleep(2);
        onView(withId(R.id.rvSys)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.rvDys)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        onView(withId(R.id.rvHeartRate)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        onView(withId(R.id.buttonSaveFilter)).perform(click());

        onView(withId(R.id.tvNoData)).check(matches(isDisplayed()));

    }

    public static class MyViewAction {

        public static ViewAction clickChildViewWithId(final int id) {
            return new ViewAction() {
                @Override
                public Matcher<View> getConstraints() {
                    return ViewMatchers.isAssignableFrom(RecyclerView.class);
                }

                @Override
                public String getDescription() {
                    return "Click on a child view with specified id.";
                }

                @Override
                public void perform(UiController uiController, View view) {
                    View v = view.findViewById(id);
                    v.performClick();
                }
            };
        }

    }

    private int getTotalItem(){
        final int[] initialItemCount = new int[1];
        activityRule.getScenario().onActivity(activity -> {
            if( ((RecyclerView)activity.findViewById(R.id.rvList)).getAdapter() != null ) {
                initialItemCount[0] = Objects.requireNonNull(((RecyclerView) activity.findViewById(R.id.rvList)).getAdapter()).getItemCount();
            }
        });
        return initialItemCount[0];
    }


    private static void safeSleep(long ...args){
        try{
            Thread.sleep(args.length > 0 ? args[0]*500 : 500);
        }catch (Exception ignored){}
    }
}
