package org.mrutcka.wntw.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.mrutcka.wntw.R;

import java.util.ArrayList;

public class UsersListAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> text;

    public UsersListAdapter(Context context, ArrayList<String> values) {
        super(context, R.layout.rowtasklayout, values);
        this.context = context;
        this.text = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.rowtasklayout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.nameTask_textView);
        textView.setText(text.get(position));

        return rowView;
    }
}
