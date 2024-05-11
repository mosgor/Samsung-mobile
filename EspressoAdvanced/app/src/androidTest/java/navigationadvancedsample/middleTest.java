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

public class middleTest {
	@Rule
	public ActivityScenarioRule<MainActivity> activityScenarioRuleRule =
			new ActivityScenarioRule<>(MainActivity.class);

//	@Test
//	public void checkTextRegister() {
//		onView(withId(R.id.form))
//				.check(matches(withText("Register")));
//		on
//	}
}
