package com.example.home;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


/**
 * Created by ProgrammingKnowledge on 4/3/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MoneyControl.db";

    public static final String TABLE_TRANSACTIONS = "Transcation"; // TABLE_NAME
    public static final String column_transaction_ID = "_id";  // COL_1
    public static final String column_transactionType="TransactionType";
    public static final String column_category_E = "Category"; //COl_4
    public static final String column_date_E = "Date"; //COL_2
    public static final String column_recurrency_E="Recurrency"; //COL8
    public static final String column_amount_E = "Amount"; //COL_3
    public static final String column_payment_E="Payment";//COL_5
    public static final String column_currency_E="Currency"; //COL_6
    public static final String column_note_E="Note"; //COL_7

    public static final String TABLE_INCOMES = "Incomes"; // TABLE_NAME
    public static final String column_income_ID = "Income_ID";  // COL_1
    public static final String column_category_I = "Category_I"; //COl_4
    public static final String column_date_I = "Date_I"; //COL_2
    public static final String column_recurrency_I="Recurrency_I"; //COL8
    public static final String column_amount_I = "Amount_I"; //COL_3
    public static final String column_payment_I="Payment_I";//COL_5
    public static final String column_currency_I="Currency_I"; //COL_6
    public static final String column_note_I="Note_I"; //COL_7

    public static final String TABLE_PIN = "Pin";
    public static final String column_status="Status";
    public static final String column_pin="Pin";
    public static final String column_id="id";

    public static final String TABLE_CATEGORIES= "Categories";
    public static final String column_category_id="Category_Id";
    public static final String column_category_name="Category_Name";

    public static final boolean column_status_defult_value=false;
    public static final String column_pin_defult_value=null;

    public static final String TABLE_BUDGET = "Budget"; // TABLE_NAME
    public static final String column_budget_ID = "Budget_ID";  // COL_1
    public static final String column_amount_B = "Amount_B";  // COL_2
    public static final String column_category_B = "Category_B"; //COl_3
    public static final String column_date_B = "Date_B"; //COL_4
    public static final String column_Time_B = "Time"; //COL_5

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //EXPENSE
        db.execSQL("create table " + TABLE_TRANSACTIONS +" (_id INTEGER PRIMARY KEY AUTOINCREMENT,TransactionType TEXT ,Category TEXT,Date TEXT,Recurrency TEXT,Amount INTEGER,Payment TEXT,Currency TEXT,Note TEXT)");

        //INCOME
        db.execSQL("create table " + TABLE_INCOMES +" (Income_ID INTEGER PRIMARY KEY AUTOINCREMENT,Category_I TEXT,Date_I TEXT,Recurrency_I TEXT,Amount_I INTEGER,Payment_I TEXT,Currency_I TEXT,Note_I TEXT)");

        //PIN
        String SQL_CREATE_TABLE_PIN = "CREATE TABLE " + TABLE_PIN  +" ("
                + column_id + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + column_status + " BOOLEAN,"
                + column_pin + " TEXT);";
        db.execSQL(SQL_CREATE_TABLE_PIN);

        db.execSQL("create table " + TABLE_CATEGORIES +" (Category_Id INTEGER PRIMARY KEY AUTOINCREMENT, Category_Name TEXT)");

        ContentValues contentValues = new ContentValues();
        contentValues.put(column_status, column_status_defult_value);
        contentValues.put(column_pin, column_pin_defult_value);

        long result = db.insert(TABLE_PIN,null ,contentValues);
        if(result == -1)
            throw new IllegalStateException("PIN Could not be initialized");

        db.execSQL("create table " + TABLE_BUDGET +" (Budget_ID INTEGER PRIMARY KEY AUTOINCREMENT,Amount_B INTEGER,Category_B TEXT,Date_B TEXT,Time TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_TRANSACTIONS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_INCOMES);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PIN);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_BUDGET);
        onCreate(db);
    }


    //inserting categories in to the DB



    public boolean insertData(String transactiontype1,String category,String date,String Recurrency,String amount,String payment, String currency, String note)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //for Transaction
        contentValues.put(column_transactionType,transactiontype1);
        contentValues.put(column_category_E,category);
        contentValues.put(column_date_E,date);
        contentValues.put(column_recurrency_E,Recurrency);
        contentValues.put(column_amount_E,amount);
        contentValues.put(column_payment_E,payment);
        contentValues.put(column_currency_E,currency);
        contentValues.put(column_note_E,note);
        long result = db.insert(TABLE_TRANSACTIONS,null ,contentValues);

        if(result == -1)
            return false;
        else
            return true;

        //for INCOME
    }
    public boolean insertDataIncome(String category,String date,String Recurrency,String amount,String payment, String currency, String note) {
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(column_category_I,category);
            contentValues.put(column_date_I,date);
            contentValues.put(column_recurrency_I,Recurrency);
            contentValues.put(column_amount_I,amount);
            contentValues.put(column_payment_I,payment);
            contentValues.put(column_currency_I,currency);
            contentValues.put(column_note_I,note);

            long result= db.insert(TABLE_INCOMES,null ,contentValues);
            if(result==-1)
                return false;
            else
                return true;
        }
    }
    public ArrayList<String> getNewCategories()
    {
        ArrayList<String> NewCategory= new ArrayList<>();
        String newCategory= "SELECT " + column_category_name + " FROM " + TABLE_CATEGORIES;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c = db.rawQuery(newCategory,null);
        if(c.moveToNext()){
            do{
                CategoryConst cate = new CategoryConst();
                cate.setCategory_name(c.getString(c.getColumnIndex(column_category_name)));
                NewCategory.add(cate.getCategory_name());
            }while (c.moveToNext());
        }
        return NewCategory;
    }

    public ArrayList<String> getCategoriesB()
    {
        ArrayList<String> NewCategoryB= new ArrayList<>();
        String newCategoryB = "SELECT " + column_category_B + " FROM " + TABLE_BUDGET;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c = db.rawQuery(newCategoryB, null);
        if (c.moveToNext()){
            do {
                CategoryConst cateB = new CategoryConst();
                cateB.setCategory_name(c.getString(c.getColumnIndex(column_category_B)));
                NewCategoryB.add(cateB.getCategory_name());
            }while (c.moveToNext());
        }
        return NewCategoryB;
    }


    public boolean insertCategories(CategoryConst cat)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(column_category_name,cat.getCategory_name());
        long resultCategory = db.insert(TABLE_CATEGORIES,null ,contentValues);
        if(resultCategory == -1)
            return false;
        else
            return true;
    }
    //public boolean insertData_B(String amount,String category,String date,String time)
    public  boolean insertData_B(CategoryConst cat)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(column_amount_B,cat.getAmount());
        contentValues.put(column_category_B,cat.getCategory_name());
        long result = db.insert(TABLE_BUDGET,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_TRANSACTIONS,null);
        return res;
    }
    public Cursor getAllData_I() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_INCOMES,null);
        return res;
    }
    public Cursor getAllCategories() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_CATEGORIES,null);
        return res;
    }
    public Cursor getAllData_B() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_BUDGET,null);
        return res;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Cursor dynamic_query(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(query,null);
        return res;
    }

    public boolean isPinEnabled(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select count(1) from "+TABLE_PIN +" where status = 1",null);
        res.moveToNext();
        if (res.getInt(0) > 0)
            return true;
        else return false;
    }

    public boolean isInitialPinCreated(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select count(1) from "+TABLE_PIN +" where pin is null",null);
        res.moveToNext();
        if (res.getInt(0) > 0)
            return false;
        else return true;
    }

    public String fetchCurrentPin(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select pin from "+TABLE_PIN +" where id = 1",null);
        StringBuilder buffer = new StringBuilder();
        while (res.moveToNext()) {
            for (int i = 0; i < res.getColumnCount(); i++)
                if ( null != res.getString(i))
                    buffer.append(res.getString(i));
                else buffer.append("???");
        }
        return buffer.toString();
    }

    public boolean updatePin(String pin, boolean state){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(column_pin, pin);
        contentValues.put(column_status, state);

        db.update(TABLE_PIN, contentValues, "id = ?",new String[] { "1" });
        return true;
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean updateData(String transactionID, String transcationType,String category,String date,String Recurrency,String amount,String payment, String currency, String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(column_transaction_ID,transactionID);
        contentValues.put(column_transactionType,transcationType);
        contentValues.put(column_category_E,category);
        contentValues.put(column_date_E,date);
        contentValues.put(column_recurrency_E,Recurrency);
        contentValues.put(column_amount_E,amount);
        contentValues.put(column_payment_E,payment);
        contentValues.put(column_currency_E,currency);
        contentValues.put(column_note_E,note);

        db.update(TABLE_TRANSACTIONS, contentValues, "Transaction_ID = ?",new String[] { transactionID });
        return true;
    }
    public boolean updateData_I(String incomeID,String category_I,String date_I,String Recurrency_I,String amount_I,String payment_I, String currency_I, String note_I) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(column_income_ID,incomeID);
        contentValues.put(column_category_I,category_I);
        contentValues.put(column_date_I,date_I);
        contentValues.put(column_recurrency_I,Recurrency_I);
        contentValues.put(column_amount_I,amount_I);
        contentValues.put(column_payment_I,payment_I);
        contentValues.put(column_currency_I,currency_I);
        contentValues.put(column_note_I,note_I);

        db.update(TABLE_INCOMES, contentValues, "Income_ID = ?",new String[] { incomeID });
        return true;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Integer deleteData (String transactionID) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_TRANSACTIONS, "Transaction_ID = ?",new String[] {transactionID});
    }
    public Integer deleteData_I (String incomeID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_INCOMES, "Income_ID = ?",new String[] {incomeID});
    }
    public Integer deleteCategory (String categoryName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CATEGORIES, "Category_Name = ?",new String[] {categoryName});
    }

    public Integer deleteData_B (String categoryB) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_BUDGET, "Category_B = ?",new String[] {categoryB});
    }
    ////////////////////////////////////////////////////////////////////////////////////////
}
