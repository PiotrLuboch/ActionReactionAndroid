package com.project.pluboch.actionreaction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by piotr on 20.04.17.
 */

public class ActionReactionAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<ActionReaction> dataSource;

    public ActionReactionAdapter(Context context, ArrayList<ActionReaction> items) {
        this.context = context;
        dataSource = items;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = inflater.inflate(R.layout.list_item_view, parent, false);

        TextView title = (TextView) rowView.findViewById(R.id.txtActionReactionTitle);

        ActionReaction actionReaction = (ActionReaction) getItem(position);
        title.setText(actionReaction.getTitle());


        return rowView;
    }
}
