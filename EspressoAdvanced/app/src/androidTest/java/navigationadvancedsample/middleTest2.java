package navigationadvancedsample;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Intent;

import androidx.test.rule.ActivityTestRule;

import com.example.android.navigationadvancedsample.MainActivity;
import com.example.android.navigationadvancedsample.R;

import junit.extensions.ActiveTestSuite;

import org.junit.Rule;
import org.junit.Test;

public class middleTest2 {
	@Rule
	public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

	@Test
	public void demonstrateIntentProperty (){
		Intent intent = new Intent();
		intent.putExtra("EXTRA", "Test");

		rule.launchActivity(intent);

		onView(withId(R.id.game_title)).check(matches(withText("Test")));
	}

	@Test
	public void useAppContext() {
		MainActivity activity = rule.getActivity();
		assert activity.getMyString().equals("MyString");
	}
}
