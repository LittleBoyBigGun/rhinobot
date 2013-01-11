package org.rhinobot.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import org.manichord.grimlock.events.ActivityEvent;
import org.rhinobot.EventMap;
import org.rhinobot.R;
import org.rhinobot.ScriptBuilder;
import org.rhinobot.widget.ScriptedView;

public class Dots extends Activity {

    EventMap<ActivityEvent> events = EventMap.create(ActivityEvent.class);

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dots);
        ScriptedView view = (ScriptedView)findViewById(R.id.view);
        new ScriptBuilder(getAssets())
                .defineEventSource("activity", this, events)
                .defineEventSource("view", view, view.events)
                .evaluate("js/utils.js")
                .evaluate("js/dots.js");
        events.invoke(ActivityEvent.create, savedInstanceState);
    }

    @Override
    public Object onRetainNonConfigurationInstance() {
        return events.invoke(ActivityEvent.retain);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        events.invoke(ActivityEvent.select, menuItem);
        return super.onOptionsItemSelected(menuItem);
    }

}
