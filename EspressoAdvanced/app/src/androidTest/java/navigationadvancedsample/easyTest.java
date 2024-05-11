package navigationadvancedsample;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.android.navigationadvancedsample.MainActivity;
import com.example.android.navigationadvancedsample.R;

import org.junit.Rule;
import org.junit.Test;

public class easyTest {
	@Rule
	public ActivityScenarioRule<MainActivity> activityScenarioRuleRule = new ActivityScenarioRule<>(MainActivity.class);

	@Test
	public void checkTextOnGameTitle() {
		onView(withId(R.id.game_title))
				.check(matches(withText("Welcome!")));
	}

	@Test
	public void checkTextOnTitleAbout() {
		onView(withId(R.id.about_btn))
				.perform(click());
		onView(withId(R.id.about_tv))
				.check(matches(withText("About")));
	}

	@Test
	public void checkImageOnDisplay() {
		onView(withId(R.id.title_image))
				.check(matches(isDisplayed()));
	}

	@Test
	public void changeTextRegistration() {
		onView(withId(R.id.form))
				.perform(click());
		onView(withHint("Name"))
				.perform(typeText("MaxMaxbetov"),closeSoftKeyboard())
				.check(matches(withText("MaxMaxbetov")));
	}

	public void checkSelectAvatar() {
		onView(withId(R.id.register))
				.perform(click());
		onView(withId(R.id.))
				.check(matches(isDisplayed()));
	}
}
