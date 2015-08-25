package com.troniczomb.budgeter;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

/**
 * Created by TronicZomB on 8/13/15.
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    private static String DB_PATH = "";
    private final static String DB_NAME = "budgeter_db.db";
    private final static int DB_VERSION = 1;

    private SQLiteDatabase mDatabase;
    private final Context mContext;

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DB_PATH = context.getFilesDir().getAbsolutePath().replace("files", "databases") + File.separator;
        }
        else {
            DB_PATH = context.getFilesDir().getPath() + context.getPackageName() + "/databases/";
        }
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Do nothing
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Do nothing
    }

    /**
     *  Creates database for the MicroRap handheld application use from the database located in /assets/.
     *  This allows for the creation of the database on a computer and then import it into the application,
     *  instead of creating it just within the application.
     *
     * @throws java.io.IOException
     */
    public void createDataBase() throws IOException {
        boolean mDataBaseExist = checkDataBase();
        if(!mDataBaseExist) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    /**
     *  Handles the actual copying of assest's database to application memory
     *
     * @throws IOException
     */
    private void copyDataBase() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }

        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    /**
     *  Checks if database exists in application memory
     */
    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    /**
     *  Opens database located in application memory for read/write use
     *
     * @return True if the database was opened successfully, false otherwise.
     * @throws android.database.SQLException
     */
    public boolean openDataBase() throws SQLException {
        String mPath = DB_PATH + DB_NAME;
        mDatabase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);

        return mDatabase != null;
    }

    /**
     *  Closes database
     */
    public synchronized void close() {
        if(mDatabase != null)
            mDatabase.close();
        super.close();
    }

    /**
     * @_deprecated
     *  Writes application database out to external memory where it can be read off of the computer (SQLite Database Browser) or on Android (X-plore).
     *  Since the application memory is not accessible unless the device is rooted, this allows for the reading of the database without root.
     *
     *  This is solely to be used for debugging purposes, thus the '@deprecated'. Although this could be repurposed to create a back up of the internal database.
     *
     * @throws IOException
     */
    //@Deprecated
    public void writeToSD() throws IOException {
        SQLiteDatabase db = this.getWritableDatabase();
        File sd = Environment.getExternalStorageDirectory();

        if (sd.canWrite()) {
            String currentDBPath = DB_NAME;
            String backupDBPath = "backupname.db";
            File currentDB = new File(DB_PATH, currentDBPath);
            File backupDB = new File(sd, backupDBPath);

            if (currentDB.exists()) {
                FileInputStream srcStream = new FileInputStream(currentDB);
                FileOutputStream dstStream = new FileOutputStream(backupDB);
                FileChannel src = srcStream.getChannel();
                FileChannel dst = dstStream.getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                srcStream.close();
                dstStream.close();
            }
        }
        db.close();
    }


    //*************************************************
    //
    //      Read Methods
    //
    //*************************************************

    public ArrayList<AccountItem> getAccounts() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name, balance, cc_limit, acct_type, notes, in_total, last_used, default_acct FROM Accounts", null);

        ArrayList<AccountItem> accounts = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                String tempName = cursor.getString(cursor.getColumnIndex("name"));
                float tempBalance = cursor.getFloat(cursor.getColumnIndex("balance"));
                float tempccLimit = cursor.getFloat(cursor.getColumnIndex("cc_limit"));
                int tempAcctType = cursor.getInt(cursor.getColumnIndex("acct_type"));
                String tempNotes = cursor.getString(cursor.getColumnIndex("notes"));
                int tempInTotal = cursor.getInt(cursor.getColumnIndex("in_total"));
                String tempLastUsed = cursor.getString(cursor.getColumnIndex("last_used"));
                int tempDefaultAcct = cursor.getInt(cursor.getColumnIndex("default_acct"));

                AccountItem item = new AccountItem(mContext, tempName, tempBalance, tempccLimit, tempAcctType, tempNotes, tempInTotal, tempLastUsed, tempDefaultAcct);
                accounts.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return accounts;
    }


    //*************************************************
    //
    //      Write Methods
    //
    //*************************************************

    public void saveNewAcct(Bundle newAcctData) {
        String name = newAcctData.getString("name");
        Float balance = newAcctData.getFloat("balance");
        Float limit = newAcctData.getFloat("limit");
        int acctType = newAcctData.getInt("acctType");
        String notes = newAcctData.getString("notes");
        int inTotal = newAcctData.getInt("inTotal");
        String lastUsed = newAcctData.getString("lastUsed");
        int defaultAcct = newAcctData.getInt("defaultAcct");

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO Accounts (acct_id, name, balance, cc_limit, acct_type, notes, in_total, last_used, default_acct) " +
                "VALUES (null, '" + name + "', " + balance + ", " + limit + ", " + acctType + ", '" + notes + "', " + inTotal + ", '" + lastUsed + "', " + defaultAcct + ");");
    }

    public void clearDefaultAccts() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE Accounts SET default_acct = 0");
    }

}
