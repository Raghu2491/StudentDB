package com.example.raghavendra.raghavendr_hw9;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.test.espresso.ViewAssertion;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.raghavendra.raghavendr_hw9.Activities.Fragment_RecyclerView;
import com.example.raghavendra.raghavendr_hw9.Activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class CreateNewTest {

    @Rule
    public final ActivityTestRule<MainActivity>
            main = new ActivityTestRule<>(MainActivity.class);
    public NavigationView navigation;

    @Test
    public void shouldBeAbleToDisplayAboutMeFragmentFromNavigationViewItem() {
        onView(withId(R.id.drawer)).perform(open());

        onView(withText("About Me"))
                .perform(click());

        onView(withId(R.id.hahaha))
                .check(matches(withId(R.id.hahaha)));

    }


}
