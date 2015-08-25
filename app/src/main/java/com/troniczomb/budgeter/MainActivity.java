package com.troniczomb.budgeter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.SQLException;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteHelper dbHelper;
        dbHelper = new SQLiteHelper(this);

        // Create the database if necessary
        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
            throw new Error("Unable to create database");
        }

        // Open the database
        try {
            dbHelper.openDataBase();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            throw sqle;
        }

        dbHelper.close();

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (position) {
            case 0:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, TransactionsFragment.newInstance(position + 1))
                        .commit();
                break;
            case 1:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, BudgetsFragment.newInstance(position + 1))
                        .commit();
                break;
            case 2:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, AccountsFragment.newInstance(position + 1))
                        .commit();
                break;
            case 3:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, SettingsFragment.newInstance(position + 1))
                        .commit();
                break;
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.settings);
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static String getTimeStamp() {
        Calendar c = Calendar.getInstance();
        String min = Integer.toString(c.get(Calendar.MINUTE));
        String hour = Integer.toString(c.get(Calendar.HOUR_OF_DAY));
        String day = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
        String month = Integer.toString(c.get(Calendar.MONTH) + 1);
        String year = Integer.toString(c.get(Calendar.YEAR));
        if (Integer.parseInt(min) < 10) {min = "0" + min;}

        return hour + ":" + min + " " + month + "/" + day + "/" + year;
    }


    public static class TransactionsFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static TransactionsFragment newInstance(int sectionNumber) {
            TransactionsFragment fragment = new TransactionsFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public TransactionsFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.transactions, container, false);
            TextView test = (TextView) rootView.findViewById(R.id.section_label);
            test.setText("transactions test");
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }


    public static class BudgetsFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static BudgetsFragment newInstance(int sectionNumber) {
            BudgetsFragment fragment = new BudgetsFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public BudgetsFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.budgets, container, false);
            TextView test = (TextView) rootView.findViewById(R.id.section_label);
            test.setText("budgets test");
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }


    public static class AccountsFragment extends Fragment {
        View newAcctDialogView;
        ArrayList<AccountItem> accounts;
        AccountAdapter adapter;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static AccountsFragment newInstance(int sectionNumber) {
            AccountsFragment fragment = new AccountsFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public AccountsFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.accounts, container, false);

            SQLiteHelper helper = new SQLiteHelper(getActivity());
            helper.openDataBase();
            accounts = helper.getAccounts();
            helper.close();

            adapter = new AccountAdapter(getActivity(), R.layout.account_item, accounts);

            ListView accountsList = (ListView) rootView.findViewById(R.id.acct_list);
            accountsList.setAdapter(adapter);
            accountsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    acctDetailsDialog();
                }
            });

            Button addAcct = (Button) rootView.findViewById(R.id.add_account);
            addAcct.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               newAccountDialog();
                                           }
                                       }
            );
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        }

        public void newAccountDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            newAcctDialogView = inflater.inflate(R.layout.new_account_dialog, null);

            ArrayAdapter<CharSequence> typesAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.acct_types, android.R.layout.simple_spinner_dropdown_item);
            typesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Spinner acctTypes = (Spinner) newAcctDialogView.findViewById(R.id.acct_type);
            acctTypes.setAdapter(typesAdapter);
            acctTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    TextView limitText = (TextView) newAcctDialogView.findViewById(R.id.text_limit);
                    EditText limit = (EditText) newAcctDialogView.findViewById(R.id.limit);

                    //if selection is credit card, show limit option
                    if (position == 2) {
                        limitText.setVisibility(View.VISIBLE);
                        limit.setVisibility(View.VISIBLE);
                    } else {
                        limitText.setVisibility(View.GONE);
                        limit.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //Do nothing
                }
            });

            builder.setView(newAcctDialogView);
            builder.setCancelable(false);
            builder.setTitle(getString(R.string.new_acct));
            builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EditText name = (EditText) newAcctDialogView.findViewById(R.id.account_name);
                    Spinner acctType = (Spinner) newAcctDialogView.findViewById(R.id.acct_type);
                    CheckBox defaultAcct = (CheckBox) newAcctDialogView.findViewById(R.id.default_acct);
                    CheckBox inTotal = (CheckBox) newAcctDialogView.findViewById(R.id.in_total);
                    EditText balance = (EditText) newAcctDialogView.findViewById(R.id.balance);
                    EditText limit = (EditText) newAcctDialogView.findViewById(R.id.limit);
                    EditText notes = (EditText) newAcctDialogView.findViewById(R.id.notes);

                    if (name.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), "Please enter an account name.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String now = getTimeStamp();

                        String tempName = name.getText().toString().trim();
                        float tempBalance = Float.parseFloat(balance.getText().toString());
                        int tempAcctType = acctType.getSelectedItemPosition();
                        String tempNotes = notes.getText().toString();
                        int tempInTotal = (inTotal.isChecked() ? 1 : 0);
                        int tempDefaultAcct = (defaultAcct.isChecked() ? 1 : 0);
                        float tempCCLimit;

                        Bundle newAccountData = new Bundle();
                        newAccountData.putString("name", tempName);
                        newAccountData.putFloat("balance", tempBalance);
                        newAccountData.putInt("acctType", tempAcctType);
                        newAccountData.putString("notes", tempNotes);
                        newAccountData.putInt("inTotal", tempInTotal);
                        newAccountData.putString("lastUsed", now);
                        newAccountData.putInt("defaultAcct", tempDefaultAcct);

                        if (limit.getText().toString().equals("")) {
                            tempCCLimit = 0;
                            newAccountData.putFloat("limit", 0);
                        } else {
                            tempCCLimit = Float.parseFloat(limit.getText().toString());
                            newAccountData.putFloat("limit", tempCCLimit);
                        }

                        SQLiteHelper dbHelper = new SQLiteHelper(getActivity());
                        dbHelper.openDataBase();
                        if (defaultAcct.isChecked()) {
                            dbHelper.clearDefaultAccts();
                            for (AccountItem i : accounts) {
                                i.setBoolDefaultAcct(false);
                            }
                        }
                        dbHelper.saveNewAcct(newAccountData);
                        dbHelper.close();

                        AccountItem item = new AccountItem(getActivity(), tempName, tempBalance, tempCCLimit, tempAcctType, tempNotes, tempInTotal, now, tempDefaultAcct);
                        accounts.add(item);
                        adapter.notifyDataSetChanged();
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //do nothing, dismiss
                }
            });

            builder.show();
        }

        public void acctDetailsDialog() {

        }
    }


    public static class SettingsFragment extends Fragment {
        TextView test;
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static SettingsFragment newInstance(int sectionNumber) {
            SettingsFragment fragment = new SettingsFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public SettingsFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.settings, container, false);
            test = (TextView) rootView.findViewById(R.id.section_label);
            test.setText("settings test");
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
}
