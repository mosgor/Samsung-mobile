package com.example.android.navigationadvancedsample;


import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import com.example.android.navigationadvancedsample.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

	@Rule
	public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
			new ActivityScenarioRule<>(MainActivity.class);

	@Test
	public void mainActivityTest() {
		ViewInteraction bottomNavigationItemView = onView(
				allOf(withId(R.id.form), withContentDescription("Register"),
						childAtPosition(
								childAtPosition(
										withId(R.id.bottom_nav),
										0),
								2),
						isDisplayed()));
		bottomNavigationItemView.perform(click());

		ViewInteraction editText = onView(
				allOf(withId(R.id.username_text), withText("Name"),
						withParent(allOf(withId(R.id.constraintLayout),
								withParent(withId(R.id.nav_host_container)))),
						isDisplayed()));
		editText.check(matches(withText("Name")));

		ViewInteraction button = onView(
				allOf(withId(R.id.signup_btn), withText("SIGN UP"), withContentDescription("sign up"),
						withParent(allOf(withId(R.id.constraintLayout),
								withParent(withId(R.id.nav_host_container)))),
						isDisplayed()));
		button.check(matches(isDisplayed()));
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
