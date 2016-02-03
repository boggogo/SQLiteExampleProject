package georgikoemdzhiev.sqliteexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by koemdzhiev on 03/02/16.
 */
public class DBHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "shopsInfo";
    // Contacts table name
    private static final String TABLE_SHOPS = "Shops";
    // Shops Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SH_ADDR = "shop_address";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_SHOPS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT, "
                + KEY_SH_ADDR + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPS);
        onCreate(sqLiteDatabase);
    }


    public void addShop(Shop shop){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID,shop.getId());
        contentValues.put(KEY_NAME,shop.getName());
        contentValues.put(KEY_SH_ADDR,shop.getAddress());

        sqLiteDatabase.insert(TABLE_SHOPS, null, contentValues);
        sqLiteDatabase.close();
    }

    // Getting one shop
    public Shop getShop(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SHOPS,new String[]{KEY_ID,KEY_NAME,KEY_SH_ADDR},KEY_ID + "=?",new String[]{String.valueOf(id)},null,null,null);
        if(cursor != null){
            cursor.moveToFirst();
        }

        Shop contact = new Shop(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2));
// return shop
        return contact;
    }

    public List<Shop> getAllShops() {
        List<Shop> shopList = new ArrayList<Shop>();
        String selectQuery = "SELECT * FROM " + TABLE_SHOPS;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                Shop shop = new Shop();
                shop.setId(cursor.getInt(0));
                shop.setName(cursor.getString(1));
                shop.setAddress(cursor.getString(2));

                shopList.add(shop);
            }while (cursor.moveToNext());
        }


        return shopList;
    }

    // Getting shops Count
    public int getShopsCount() {
        String countQuery = "SELECT * FROM " + TABLE_SHOPS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
// return count
        return cursor.getCount();
    }

    // Updating a shop
    public int updateShop(Shop shop) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID,shop.getId());
        values.put(KEY_NAME, shop.getName());
        values.put(KEY_SH_ADDR, shop.getAddress());
// updating row
        return db.update(TABLE_SHOPS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(shop.getId())});
    }

    // Deleting a shop
    public void deleteShop(Shop shop) {
        SQLiteDatabase db = this.getWritableDatabase();
        //TABLE_SHOPS, KEY_ID + " = ?", new String[] { String.valueOf(shop.getId()) }
        db.delete(TABLE_SHOPS,KEY_ID + " = ?",new String[]{String.valueOf(shop.getId())});
        db.close();
    }
}
