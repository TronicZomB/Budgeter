package com.troniczomb.budgeter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by TronicZomB on 8/13/15.
 */
public class DrawerAdapter extends ArrayAdapter<DrawerItem> {

    Context context;
    int layoutResourceId;
    DrawerItem data[] = null;

    public DrawerAdapter(Context context, int layoutResourceId, DrawerItem[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        DrawerItemHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new DrawerItemHolder();
            holder.imgIcon = (ImageView) row.findViewById(R.id.icon);
            holder.txtTitle = (TextView) row.findViewById(R.id.title);

            row.setTag(holder);
        } else {
            holder = (DrawerItemHolder) row.getTag();
        }

        DrawerItem item = data[position];
        holder.txtTitle.setText(item.title);
        holder.imgIcon.setImageResource(item.icon);

        return row;
    }

    static class DrawerItemHolder {
        ImageView imgIcon;
        TextView txtTitle;
    }
}