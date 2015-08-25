package com.troniczomb.budgeter;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by TronicZomB on 8/16/15.
 */
public class AccountAdapter extends ArrayAdapter<AccountItem> {

    Context context;
    int layoutResourceId;
    ArrayList<AccountItem> data = null;

    public AccountAdapter(Context context, int layoutResourceId, ArrayList<AccountItem> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        AccountItemHolder holder = null;

        if (row == null) {
            LayoutInflater inflator = ((Activity) context).getLayoutInflater();
            row = inflator.inflate(layoutResourceId, parent, false);

            holder = new AccountItemHolder();
            holder.tvAcctName = (TextView) row.findViewById(R.id.acct_name);
            holder.tvAcctType = (TextView) row.findViewById(R.id.acct_type);
            holder.tvLastUsed = (TextView) row.findViewById(R.id.last_used);
            holder.tvAvailable = (TextView) row.findViewById(R.id.available);
            holder.tvUsedLimit = (TextView) row.findViewById(R.id.used_limit);
            holder.tvPercent = (TextView) row.findViewById(R.id.percent_used);

            row.setTag(holder);
        } else {
            holder = (AccountItemHolder) row.getTag();
        }

        AccountItem item = data.get(position);
        holder.tvAcctName.setText(item.acctName);
        holder.tvAcctType.setText(item.strAcctType);
        holder.tvLastUsed.setText(item.lastUsed);
        holder.tvAvailable.setText(item.available);
        holder.tvUsedLimit.setText(item.usedLimit);
        holder.tvPercent.setText(item.usedPercent);
        return row;
    }

    static class AccountItemHolder {
        TextView tvAcctName;
        TextView tvAcctType;
        TextView tvLastUsed;
        TextView tvAvailable;
        TextView tvUsedLimit;
        TextView tvPercent;
    }
}
