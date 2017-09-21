package code.github.features;

import android.support.annotation.NonNull;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import code.github.R;
import code.github.features.search.SearchActivity;
import io.appflate.restmock.RESTMockServer;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.google.common.base.Preconditions.checkArgument;
import static io.appflate.restmock.utils.RequestMatchers.pathContains;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by shank on 9/21/17.
 */

public class SearchActivityTest {

    @Rule
    public ActivityTestRule<SearchActivity> searchActivityTestRule =
            new ActivityTestRule<>(SearchActivity.class);

    @Before
    public void setup() throws Exception{
        RESTMockServer.reset();
        RESTMockServer.whenGET(pathContains("user/repo"))
                .thenReturnFile(200, "user/repo.json");
    }

    @Test
    public void showUserRepositoriesTest() throws Exception {
        String description = "test repo";
        onView(withId(R.id.recycler_view))
                .check(matches(atPosition(0, withText(description))));
    }

    @Test
    public void searchButtonTest() throws Exception {
        String query = "Wifi dog";
        onView(withId(R.id.searchbox)).perform(typeText(query), closeSoftKeyboard());
        onView(withId(R.id.search_button)).perform(click());
    }

   private Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher){
       BoundedMatcher<View, RecyclerView> matcher = new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
           @Override
           protected boolean matchesSafely(final RecyclerView view) {
               RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
               if (viewHolder == null) {
                   // has no item on such position
                   return false;
               }
               return itemMatcher.matches(viewHolder.itemView);
           }

           @Override
           public void describeTo(org.hamcrest.Description description) {
               description.appendText("has item at position " + position + ": ");
               itemMatcher.describeTo(description);
           }
       };
       return matcher;
   }

}
