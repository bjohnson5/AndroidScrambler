package edu.auburn.eng.csse.comp3710.brj0003.brj0003scramble;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements MainFragment.mainCallback {

    private ScrambleFragment scramble = ScrambleFragment.createNew();
    private HistoryFragment history = new HistoryFragment();
    private MainFragment main = MainFragment.createNew();

    private String lastWord = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create a fragment manager that adds the fragments to the screen
        FragmentManager fm = getSupportFragmentManager();
        if(fm.findFragmentById(R.id.fragment_container1) == null){
            main = new MainFragment();
            fm.beginTransaction().replace(R.id.fragment_container1, main).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onScrambleClick(String word) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(fm.findFragmentByTag(ScrambleFragment.TAG) == null)
        {
            ft.replace(R.id.fragment_container2, scramble);
        }
        ft.replace(R.id.fragment_container2, scramble, ScrambleFragment.TAG);
        ft.commit();

        lastWord = word;
        history.addHistory(word);
        scramble.scramble(word);
    }

    public void onHistoryClick(){

        if(history.getHistorySize() != 0)
        {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_container2, history, HistoryFragment.TAG);
            ft.addToBackStack(null);
            ft.commit();
        }
        else
        {
            Toast.makeText(this, "No history", Toast.LENGTH_SHORT).show();
        }
    }
}
