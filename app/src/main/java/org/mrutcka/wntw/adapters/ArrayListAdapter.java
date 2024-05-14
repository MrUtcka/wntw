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

public class ArrayListAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> text;
    private final ArrayList<Integer> num_image;
    private final int[] image = new int[]{R.drawable.ez_circle, R.drawable.normal_circle, R.drawable.hard_circle};

    public ArrayListAdapter(Context context, ArrayList<String> values, ArrayList<Integer> num_image) {
        super(context, R.layout.rowtasklayout, values);
        this.context = context;
        this.text = values;
        this.num_image = num_image;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.rowtasklayout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.nameTask_textView);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.seriousTask_imageView);
        textView.setText(text.get(position));
        imageView.setImageResource(image[num_image.get(position) - 1]);

        return rowView;
    }
}