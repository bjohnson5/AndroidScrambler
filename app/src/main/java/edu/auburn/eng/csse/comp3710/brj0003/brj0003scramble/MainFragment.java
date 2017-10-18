package edu.auburn.eng.csse.comp3710.brj0003.brj0003scramble;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Blake on 3/8/2016.
 */
public class MainFragment extends Fragment {
    Button scrambleButton;
    Button historyButton;
    EditText textBox;
    mainCallback callback;

    public interface mainCallback{
        void onScrambleClick(String word);
        void onHistoryClick();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        // Initialize all the elements in this fragment and set up button listeners
        scrambleButton = (Button) v.findViewById(R.id.scrambleButton);
        historyButton = (Button) v.findViewById(R.id.historyButton);
        textBox = (EditText) v.findViewById(R.id.editText);

        scrambleButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(textBox.getWindowToken(), 0);

                String word = textBox.getText().toString().trim();
                if(word.equals(""))
                {
                    Toast.makeText(getActivity(), "No word to scramble.", Toast.LENGTH_SHORT).show();
                    return;
                }

                callback.onScrambleClick(word);
            }
        });

        historyButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                callback.onHistoryClick();
            }
        });
    }

    public static MainFragment createNew(){
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    // Initialize the callback instance
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity a = (Activity) context;
        callback = (mainCallback) a;
    }
}
