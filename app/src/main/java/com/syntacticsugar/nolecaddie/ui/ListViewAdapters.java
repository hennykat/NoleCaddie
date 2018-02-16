package com.syntacticsugar.nolecaddie.ui;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.syntacticsugar.nolecaddie.R;

import static com.syntacticsugar.nolecaddie.config.AppConfig.FIRST_COLUMN;
import static com.syntacticsugar.nolecaddie.config.AppConfig.SECOND_COLUMN;
import static com.syntacticsugar.nolecaddie.config.AppConfig.THIRD_COLUMN;

/**
 * Created by sam on 7/17/15.
 * Updated by henny 2018
 */
public class ListViewAdapters extends BaseAdapter {

    public ArrayList<HashMap<String, String>> list;

    private Activity activity;
    private TextView txtFirst;
    private TextView txtSecond;
    private TextView txtThird;

    public ListViewAdapters(Activity activity, ArrayList<HashMap<String, String>> list) {
        super();
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub


        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.scores_layout, null);

            txtFirst = convertView.findViewById(R.id.hole);
            txtSecond = convertView.findViewById(R.id.par);
            txtThird = convertView.findViewById(R.id.score);
        }

        HashMap<String, String> map = list.get(position);
        txtFirst.setText(map.get(FIRST_COLUMN));
        txtSecond.setText(map.get(SECOND_COLUMN));
        txtThird.setText(map.get(THIRD_COLUMN));

        return convertView;
    }

}
