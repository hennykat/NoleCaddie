package com.syntacticsugar.nolecaddie.ui;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.syntacticsugar.nolecaddie.R;

import java.util.List;

public class ScorecardAdapter extends ArrayAdapter<ScorecardItem> {

    public static final String TAG = ScorecardAdapter.class.getSimpleName();

    private LayoutInflater layoutInflater;

    public ScorecardAdapter(@NonNull Context context, int resource, @NonNull List<ScorecardItem> itemList) {
        super(context, resource, itemList);

        this.layoutInflater = ((Activity)context).getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_scorecard, null);
        }

        ScorecardItem item = getItem(position);
        if (item == null) {
            return convertView;
        }

        TextView holeTextView = convertView.findViewById(R.id.scorecard_item_hole_textview);
        TextView parTextView = convertView.findViewById(R.id.scorecard_item_par_textview);
        TextView scoreTextView = convertView.findViewById(R.id.scorecard_item_score_textview);

        holeTextView.setText(item.getHole());
        parTextView.setText(item.getPar());
        scoreTextView.setText(item.getScore());

        return convertView;
    }
}
