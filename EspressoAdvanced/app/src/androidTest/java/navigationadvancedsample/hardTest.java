package navigationadvancedsample;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import static org.hamcrest.Matchers.allOf;

import android.content.Intent;
import android.net.Uri;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import com.example.android.navigationadvancedsample.MainActivity;
import com.example.android.navigationadvancedsample.R;

import org.junit.Rule;
import org.junit.Test;

public class hardTest {
	private String userName;
	private String url;

	@Rule
	public InstantTaskExecutorRule instantExecutorRule;

	@Rule
	public ActivityTestRule<MainActivity> activityRule;

	public hardTest() {
		this.url = "www.example.com/user/" + this.userName;
		this.instantExecutorRule = new InstantTaskExecutorRule();
		this.activityRule = new ActivityTestRule(MainActivity.class){
			@Override
			protected Intent getActivityIntent() {
				return new Intent("android.intent.action.View", Uri.parse(url));
			}
		};
	}

	@Test
	public void checkIntent() {
		assertProfile();
		Espresso.pressBack();
		assertList();
		Espresso.pressBack();
		assertHome();
	}

	public void assertProfile() {
		onView(withText(userName))
				.check(matches(isDisplayed()));
	}

	public void assertList() {
		onView(allOf(withText(R.string.title_list), isDescendantOfA(withId(R.id.action_bar))))
				.check(matches(isDisplayed()));
	}

	public void assertHome() {
		onView(withText(R.string.welcome))
				.check(matches(isDisplayed()));
	}
}
