package edu.auburn.eng.csse.comp3710.brj0003.brj0003scramble;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Blake on 3/8/2016.
 */
public class ScrambleFragment extends Fragment {

    public static final String TAG = "ScrambledFragment";

    private Button nextButton;
    private Button prevButton;
    private Spinner spin;
    private String lastWord;
    private String temp;
    private TextView results;
    private String word;
    private ArrayList<String> words = new ArrayList<>();
    private int prevNum = 0;
    private int spinPos = 0;
    private int spinVal;
    private ScrollView scroll;

    String KEY_LIST = "ScrambledWordlist";
    String KEY_PREVWORD = "ScramblePrevWord";
    String KEY_SPINNERVALUE = "ScrambleSpinnerValue";
    String KEY_SPINNERLOCATION = "ScrambleSpinnerLocation";
    String KEY_LASTWORD_SCRAMBLED = "ScrambledLastWord";

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        savedState.putStringArrayList(KEY_LIST, words);
        savedState.putInt(KEY_PREVWORD, prevNum);
        savedState.putInt(KEY_SPINNERVALUE, spinVal);
        savedState.putInt(KEY_SPINNERLOCATION, spinPos);
        savedState.putString(KEY_LASTWORD_SCRAMBLED, lastWord);
    }

    @Override
    public void onActivityCreated(Bundle inBundle) {
        super.onActivityCreated(inBundle);
        if (inBundle == null) {
            return;
        }

        prevNum = inBundle.getInt("ScramblePrevWord", 0);
        words = inBundle.getStringArrayList(KEY_LIST);
        spinVal = inBundle.getInt(KEY_SPINNERVALUE, 5);
        spinPos = inBundle.getInt(KEY_SPINNERLOCATION, 0);
        lastWord = inBundle.getString(KEY_LASTWORD_SCRAMBLED, "");
        if (spin != null) {
            String value = ((ArrayAdapter<String>)spin.getAdapter()).getItem(3);
            ((ArrayAdapter<String>)spin.getAdapter()).remove(value);
            ((ArrayAdapter<String>)spin.getAdapter()).add(words.size() + "");
            spin.setSelection(spinPos);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.scrambled_fragment, container, false);
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        results = (TextView) v.findViewById(R.id.results);
        scroll = (ScrollView) v.findViewById(R.id.scroll);
        spin = (Spinner) v.findViewById(R.id.spin);
        ArrayList<String>  spinnerVal = new ArrayList<>();
        spinnerVal.add("5");
        spinnerVal.add("10");
        spinnerVal.add("20");
        spinnerVal.add(words.size() + "");
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, spinnerVal);
        spin.setAdapter(adapter);

        if (temp != null) {
            scramble(temp);
            temp = null;
        }

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        spinPos = 0;
                        spinVal = 5;
                        prevNum = 0;
                        showWords(spinVal);
                        break;
                    case 1:
                        spinPos = 1;
                        spinVal = 10;
                        prevNum = 0;
                        showWords(spinVal);
                        break;
                    case 2:
                        spinPos = 2;
                        spinVal = 20;
                        showWords(spinVal);
                        break;
                    case 3:
                        spinPos = 3;
                        results.setText(stringFromList(words));
                        scroll.removeAllViews();
                        scroll.addView(results);
                        nextButton.setVisibility(View.GONE);
                        prevButton.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        nextButton = (Button) v.findViewById(R.id.next);
        prevButton = (Button) v.findViewById(R.id.previous);
        prevButton.setVisibility(View.GONE);
        nextButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevNum += spinVal;
                update();
            }
        });

        prevButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                prevNum -= spinVal;
                if(prevNum < 0)
                {
                    prevNum = 0;
                }
                update();
            }
        });
    }

    public void scramble(String wordIn){
        lastWord = wordIn;
        if(results == null){
            temp = wordIn;
            return;
        }

        word = wordIn;
        words.clear();
        permutate("", word, words);
        update();
    }

    private void update(){
        String value = ((ArrayAdapter<String>)spin.getAdapter()).getItem(3);
        ((ArrayAdapter<String>)spin.getAdapter()).remove(value);
        ((ArrayAdapter<String>)spin.getAdapter()).add(words.size() + "");
        spin.setSelection(spinPos);
        showWords(spinVal);
    }

    public static void permutate(String prefix, String str,  ArrayList<String> words){

        int n = str.length();
        if (n == 0)
        {
            words.add(words.size(), prefix);
        }
        else {
            for (int i = 0; i < n; i++)
                permutate(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n), words);
        }
    }

    public void showWords(int num){
        if (words.size() > num) {
            if (prevNum + spinVal >= words.size() && nextButton != null) {
                nextButton.setVisibility(View.GONE);
            }
            else if (nextButton != null) {
                nextButton.setVisibility(View.VISIBLE);
            }
            if ((prevNum == 0 || spinVal > words.size()) && prevButton != null) {
                prevButton.setVisibility(View.GONE);
            }
            if(words.size() == num){
                prevButton.setVisibility(View.GONE);
                nextButton.setVisibility(View.GONE);
            }
        }
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 0; i < num; i ++) {
            if (i + prevNum == words.size()) {
                if (nextButton != null) {
                    nextButton.setVisibility(View.GONE);
                }
                i = 99999;
                break;
            }
            temp.add(words.get(i + prevNum));
        }
        if (prevNum > 0 && prevButton != null) {
            prevButton.setVisibility(View.VISIBLE);
        }
        results.setText(stringFromList(temp));
        scroll.removeAllViews();
        scroll.addView(results);
    }

    public static ScrambleFragment createNew(){
        Bundle args = new Bundle();
        ScrambleFragment fragment = new ScrambleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public String stringFromList(ArrayList<String> wordList) {

        String temp = "";

        for (String s : wordList) {
            temp += s + "\n";
        }
        if (temp.length() > 1) {
            temp = temp.substring(0, temp.length() - 1);
        }
        return temp.trim();
    }
}
