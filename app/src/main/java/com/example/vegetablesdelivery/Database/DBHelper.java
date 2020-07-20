package com.example.vegetablesdelivery.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_TABLE = "OrderDetail";
    public static final String KEY_ID = "ID";
    public static final String PRODUCT_ID = "ProductId";
    public static final String PRODUCT_Name = "ProductName";
    public static final String QUANTITY = "Quantity";
    public static final String DISCOUNT = "Discount";
    public static final String PRICE = "Price";

    public DBHelper(Context context) {
        super(context, "databases/Tomato.db", null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db = this.getWritableDatabase();
        db.execSQL("CREATE TABLE " + DATABASE_TABLE + "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PRODUCT_ID + " TEXT NOT NULL, " +
                PRODUCT_Name + " TEXT NOT NULL, " +
                QUANTITY + " TEXT NOT NULL, " +
                DISCOUNT + " TEXT NOT NULL, " +
                PRICE + " TEXT NOT NULL " +
                ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
