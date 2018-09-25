package mandj.appbuildin.codingcoach.innovo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import mandj.appbuildin.codingcoach.innovo.Study.Runner.SetPuzzle;
import mandj.appbuildin.codingcoach.innovo.Study.TabbedListView.PuzzleTabs;

public class Settings extends AppCompatActivity {

    SetPuzzle setPuzzle;
    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pref);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getFragmentManager().beginTransaction().replace(R.id.content_frame, new SettingsFragment()).commit();
        intent = getIntent();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
            if (id == android.R.id.home && intent != null) {
                Class<?> callingActivity = intent.getExtras().getClass();
                Intent upIntent = new Intent(this,callingActivity);
                NavUtils.navigateUpTo(this, upIntent);
                return true;
            }
        return true;
    }
}

