package com.troniczomb.budgeter;

import android.accounts.Account;
import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by TronicZomB on 8/16/15.
 */
public class AccountItem {
    public String acctName;
    public String notes;
    public String lastUsed;
    public String strBalance;
    public String strCCLimit;
    public String strAcctType;
    public String available;
    public String usedLimit;
    public String usedPercent;

    public boolean boolInTptal;
    public boolean boolDefaultAcct;

    public float balance;
    public float ccLimit;
    public int acctType;
    public int inTotal;
    public int defaultAcct;

    ArrayList<String> tempType;

    public AccountItem() {
        super();
    }

    public AccountItem(Context context, String acctName, float balance, float ccLimit, int acctType,
                       String notes, int inTotal, String lastUsed, int defaultAcct) {
        super();

        tempType = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(R.array.acct_types)));

        this.acctName = acctName;
        this.balance = balance;
        this.strBalance = String.format("%.2f", balance);
        this.ccLimit = ccLimit;
        this.strCCLimit = String.format("%.2f", ccLimit);
        this.acctType = acctType;
        this.strAcctType = tempType.get(acctType);
        this.notes = notes;
        this.inTotal = inTotal;
        this.boolInTptal = ((inTotal == 1) ? true:false);
        this.lastUsed = lastUsed;
        this.defaultAcct = defaultAcct;
        this.boolDefaultAcct = ((defaultAcct == 1) ? true:false);

        if (this.boolDefaultAcct) {
            this.acctName = acctName + " (Default)";
        }

        //if account is a credi card, amount available is calculated different
        if (acctType == 2) {
            float tempAvailable = ccLimit - balance;
            available = "$" + String.format("%.2f", tempAvailable);

            usedLimit = "$" + strBalance + "/ $" + strCCLimit;

            float tempPercent = balance / ccLimit;
            usedPercent = Integer.toString((int) (tempPercent * 100)) + "% Utilized";
        }
        else {
            available = "$" + String.format("%.2f", balance);
            usedLimit = "";
            usedPercent = "";
        }
    }

    public int getDefaultAcct() {
        return defaultAcct;
    }

    public void setDefaultAcct(int defaultAcct) {
        this.defaultAcct = defaultAcct;
        this.boolDefaultAcct = ((defaultAcct == 1) ? true:false);

        if (this.boolDefaultAcct) {
            this.acctName = acctName + " (Default)";
        }
        else {
            this.acctName = acctName.replace(" (Default)", "");
        }
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(String lastUsed) {
        this.lastUsed = lastUsed;
    }

    public String getStrBalance() {
        return strBalance;
    }

    public void setStrBalance(String strBalance) {
        this.strBalance = strBalance;
        this.balance = Float.parseFloat(strBalance);
    }

    public String getStrCCLimit() {
        return strCCLimit;
    }

    public void setStrCCLimit(String strCCLimit) {
        this.strCCLimit = strCCLimit;
        this.ccLimit = Float.parseFloat(strCCLimit);
    }

    public String getStrAcctType() {
        return strAcctType;
    }

    public void setStrAcctType(String strAcctType) {
        this.strAcctType = strAcctType;
        this.acctType = tempType.indexOf(strAcctType);
    }

    public boolean isBoolInTptal() {
        return boolInTptal;
    }

    public void setBoolInTptal(boolean boolInTptal) {
        this.boolInTptal = boolInTptal;
        this.inTotal = (boolInTptal ? 1:0);
    }

    public boolean isBoolDefaultAcct() {
        return boolDefaultAcct;
    }

    public void setBoolDefaultAcct(boolean boolDefaultAcct) {
        this.boolDefaultAcct = boolDefaultAcct;
        this.defaultAcct = (boolDefaultAcct ? 1:0);

        if (this.boolDefaultAcct) {
            this.acctName = acctName + " (Default)";
        }
        else {
            this.acctName = acctName.replace(" (Default)", "");
        }
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
        this.strBalance = String.format("%.2f", balance);
    }

    public float getCcLimit() {
        return ccLimit;
    }

    public void setCcLimit(float ccLimit) {
        this.ccLimit = ccLimit;
        this.strCCLimit = String.format("%.2f", ccLimit);
    }

    public int getAcctType() {
        return acctType;
    }

    public void setAcctType(int acctType) {
        this.acctType = acctType;
        this.strAcctType = tempType.get(acctType);
    }

    public int getInTotal() {
        return inTotal;
    }

    public void setInTotal(int inTotal) {
        this.inTotal = inTotal;
        this.boolInTptal = ((inTotal == 1) ? true:false);
    }

}
