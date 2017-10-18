package edu.auburn.eng.csse.comp3710.brj0003.brj0003scramble;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Blake on 3/9/2016.
 */
public class HistoryFragment extends Fragment {

    public static final String TAG = "HistoryFragment";

    private static final ArrayList<String> history = new ArrayList<>();

    private TextView leftPane;
    private TextView rightPane;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.history_fragment, container, false);
    }
    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        leftPane = (TextView) v.findViewById(R.id.left);
        rightPane = (TextView) v.findViewById(R.id.right);
        updateViews();
    }

    public void updateViews(){
        if (getView() == null) return;
        StringBuilder left = new StringBuilder();
        for (int i = 0; i < history.size(); i += 2) {
            left.append(history.get(i)).append('\n');
        }
        leftPane.setText(left.toString().trim());

        StringBuilder right = new StringBuilder();
        for (int j = 1; j < history.size(); j += 2) {
            right.append(history.get(j)).append('\n');
        }
        rightPane.setText(right.toString().trim());
    }

    public int getHistorySize(){
        return history.size();
    }

    public void addHistory(String word){
        if(!history.contains(word))
        {
            history.add(word);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putStringArrayList("history", history);
    }

    @Override
    public void onActivityCreated(Bundle inBundle) {
        super.onActivityCreated(inBundle);

        if (inBundle != null) {
            for (String word : inBundle.getStringArrayList("history")) {
                addHistory(word);
            }
            updateViews();
        }
    }
}
