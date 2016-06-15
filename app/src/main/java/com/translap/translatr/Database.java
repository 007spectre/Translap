package com.translap.translatr;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by vivek on 10/29/13.
 */
public class Database extends SQLiteOpenHelper {
    private static final String TAG = Database.class.getSimpleName();

    public Database(Context context)
    {
        super(context, "EmployeeDatabase.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableEmp="create table emp(id text,selection text)";
        db.execSQL(tableEmp);

        String tableCat="create table cat(id text,category text)";
        db.execSQL(tableCat);
        Log.d(TAG, "New table created: ");

        String tablesub="create table sub(id text,category text,test text,question text,coin text)";
        db.execSQL(tablesub);
        Log.d(TAG, "New table created: ");
        String tablequs="create table qus(id text,category text,test text,question text,ans1 text,ans2 text,ans3 text,ans4 text,cans text)";
        db.execSQL(tablequs);
        Log.d(TAG, "New table created: ");


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public void insertData(String id,String selection)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("id",id);
        values.put("selection",selection);

        long id1=     sqLiteDatabase.insert("emp",null,values);
        Log.d(TAG, "New category category into sqlite: " + id1);

    }

    public void insertCat(ArrayList<EnCategory> categories)
    {


        try {
            SQLiteDatabase db = this.getWritableDatabase();
            for (EnCategory category : categories) {

                Cursor cursor = db.rawQuery(
                        "Select count(*) from cat where  id='" + category.getId() + "'", null);

                cursor.moveToFirst();
                if (cursor.getInt(0) <= 0) {
                    // Cursor cursor=db.rawQuery("Select * from newss", null);
                    //  cursor.moveToFirst();
                    //  if (cursor.getInt(0) <= 0) {
                    ContentValues companyValues = new ContentValues();
                    companyValues.put("id", category.getId());
                    companyValues.put("category", category.getCategory());


                    long id1 = db.insert("cat", null, companyValues);
                    Log.d(TAG, "New category category into sqlite: " + id1);

                }
            }

        } catch (Exception ex) {
            Log.e("Exception in adding product", ex.getMessage());
            File sdcard = Environment.getExternalStorageDirectory();
            File file = new File(sdcard, "ErrorLog.txt");
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                bw.append(ex.getMessage());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


    }

    }

    public void insertSub(ArrayList<TestDetail> categories)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            for (TestDetail category : categories) {
                Cursor cursor = db.rawQuery(
                        "Select count(*) from sub where  id='" + category.getId() + "'", null);
                // Cursor cursor=db.rawQuery("Select * from newss", null);

                cursor.moveToFirst();
                if (cursor.getInt(0) <= 0) {
                    ContentValues companyValues = new ContentValues();
                    companyValues.put("id", category.getId());
                    companyValues.put("category", category.getCategory());
                    companyValues.put("test", category.getTest());
                    companyValues.put("question", category.getQuestion());
                    companyValues.put("coin", category.getCoin());
                    long id1 = db.insert("sub", null, companyValues);
                    Log.d(TAG, "New category category into sqlite: " + id1);


                }
            }
        } catch (Exception ex) {
            Log.e("Exception in adding product", ex.getMessage());
            File sdcard = Environment.getExternalStorageDirectory();
            File file = new File(sdcard, "ErrorLog.txt");
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                bw.append(ex.getMessage());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }

    }

    public void insertQus(ArrayList<Question> categories)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            for (Question category : categories) {
                Cursor cursor = db.rawQuery(
                        "Select count(*) from qus where  id='" + category.getId() + "'", null);
                // Cursor cursor=db.rawQuery("Select * from newss", null);

                cursor.moveToFirst();
                if (cursor.getInt(0) <= 0) {
                    ContentValues companyValues = new ContentValues();
                    companyValues.put("id", category.getId());
                    companyValues.put("category", category.getCategory());
                    companyValues.put("test", category.getTest());
                    companyValues.put("question", category.getQuestion());
                    companyValues.put("ans1", category.getAns1());
                    companyValues.put("ans2", category.getAns2());
                    companyValues.put("ans3", category.getAns3());
                    companyValues.put("ans4", category.getAns4());
                    companyValues.put("cans", category.getCans());
                    long id1 = db.insert("qus", null, companyValues);
                    Log.d(TAG, "New category category into sqlite: " + id1);


                }
            }
        } catch (Exception ex) {
            Log.e("Exception in adding product", ex.getMessage());
            File sdcard = Environment.getExternalStorageDirectory();
            File file = new File(sdcard, "ErrorLog.txt");
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                bw.append(ex.getMessage());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }

    }

    public ArrayList fetchQus(String category,String test )
    {
        ArrayList<Question> productList = new ArrayList<Question>();
        SQLiteDatabase db = this.getWritableDatabase();
        String str="Test2";

        Cursor cursor = db.rawQuery("Select * from qus where category='"+category+"' and test='"
                +test+ "'"
                , null);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Question product = new Question();
                product.setId(cursor.getString(0));
                product.setCategory(cursor.getString(1));
                product.setTest(cursor.getString(2));
                product.setQuestion(cursor.getString(3));
                product.setAns1(cursor.getString(4));
                product.setAns2(cursor.getString(5));
                product.setAns3(cursor.getString(6));
                product.setAns4(cursor.getString(7));
                product.setCans(cursor.getString(8));


                productList.add(product);


            } while (cursor.moveToNext());
        }
            return productList;
        }






    public ArrayList fetchData()
    {
        ArrayList<String> stringArrayList=new ArrayList<String>();
        String fetchdata="select * from emp";
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(fetchdata, null);
        if(cursor.moveToFirst()){
            do
            {
                stringArrayList.add(cursor.getString(0));
                stringArrayList.add(cursor.getString(1));

            } while (cursor.moveToNext());
        }
        return stringArrayList;
    }


    public ArrayList<EnCategory> fetchCat()
    {

        ArrayList<EnCategory> productList = new ArrayList<EnCategory>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db
                .rawQuery(
                        "select * from cat  ",
                        null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                EnCategory product = new EnCategory();
                product.setId(cursor.getString(0));
                product.setCategory(cursor.getString(1));

                productList.add(product);
            } while (cursor.moveToNext());
        }
        return productList;

    }

    public String  getCorrectans(String id,String ans) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "Select cans from qus where id='"
                + id + "' and cans='" + ans + "'" ;
        String bankbalresult="";

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                bankbalresult = cursor.getString(7);
            } while (cursor.moveToNext());
        }

        return bankbalresult;

    }











    public ArrayList  fetchSub(String category )
    {
   //     TestDetail t=new TestDetail();
        ArrayList<TestDetail> productList = new ArrayList<TestDetail>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "Select * from sub where  category='" + category + "'", null);

      //  Cursor cursor = db
              //  .rawQuery(
                //        "select * from sub where  ",
                  //      null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                TestDetail product = new TestDetail();
                product.setId(cursor.getString(0));
                product.setCategory(cursor.getString(1));
                product.setQuestion(cursor.getString(2));
                product.setTest(cursor.getString(3));
                product.setCoin(cursor.getString(4));

                productList.add(product);
            } while (cursor.moveToNext());
        }
        return productList;

    }
}