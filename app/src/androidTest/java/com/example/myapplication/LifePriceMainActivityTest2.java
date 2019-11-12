package com.example.myapplication;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.myapplication.data.FileDataSource;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LifePriceMainActivityTest2 {

    @Rule
    public ActivityTestRule<LifePriceMainActivity> mActivityTestRule = new ActivityTestRule<>(LifePriceMainActivity.class);

    @Test
    public void lifePriceMainActivityTest() {
        String testText= (int)(Math.random()*10000)+"name";
        String testPrice= ((int)(Math.random()*1000))/100.0+"";
        /*检查要测试的对象不存在*/
        ViewInteraction theNotExistTextView = onView(
                allOf(withId(R.id.text_view_name), withText("商品："+testText)));
        theNotExistTextView.check(doesNotExist());
        ViewInteraction theNotExitsPriceTextView = onView(
                allOf(withId(R.id.text_view_price), withText("价格："+testPrice)));
        theNotExitsPriceTextView.check(doesNotExist());

        /*新建菜单不存在*/
        ViewInteraction textViewMenu = onView(
                allOf(withId(android.R.id.title), withText("新建"), isDisplayed()));
        textViewMenu.check(doesNotExist());

        /*第一个对象长按*/
        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                        withId(R.id.list_view_goods),
                        0),
                        isDisplayed()));
        linearLayout.perform(longClick());

        /*出现新建菜单*/
        ViewInteraction textView = onView(
                allOf(withId(android.R.id.title), withText("新建"), isDisplayed()));
        textView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.edit_text_good_name), isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.edit_text_good_name), isDisplayed()));
        appCompatEditText2.perform(replaceText(testText), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.edit_text_good_price), isDisplayed()));
        appCompatEditText3.perform(replaceText(testPrice), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.button_ok), withText("确定"), isDisplayed()));
        appCompatButton.perform(click());

        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction theTextView = onView(
                allOf(withId(R.id.text_view_name), withText("商品："+testText)));
        theTextView.check(matches(isDisplayed()));
        ViewInteraction thePriceTextView = onView(
                allOf(withId(R.id.text_view_price), withText("价格："+testPrice)));
        theTextView.check(matches(isDisplayed()));

        /*第一个对象长按*/
        ViewInteraction linearLayout1 = onView(
                allOf(childAtPosition(
                        withId(R.id.list_view_goods),
                        0),
                        isDisplayed()));
        linearLayout1.perform(longClick());

        /*出现删除菜单*/
        ViewInteraction textView1 = onView(
                allOf(withId(android.R.id.title), withText("删除"), isDisplayed()));
        textView1.perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("你确定要删除这条吗？"))
                .inRoot(withDecorView(not(mActivityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

        ViewInteraction buttonConfirm=onView(withText("你确定要删除这条吗？"))
                .inRoot(withDecorView(not(mActivityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
        buttonConfirm.perform(click());

        /*检查要测试的对象不存在*/
        theNotExistTextView = onView(
                allOf(withId(R.id.text_view_name), withText("商品："+testText)));
        theNotExistTextView.check(doesNotExist());
        theNotExitsPriceTextView = onView(
                allOf(withId(R.id.text_view_price), withText("价格："+testPrice)));
        theNotExitsPriceTextView.check(doesNotExist());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
