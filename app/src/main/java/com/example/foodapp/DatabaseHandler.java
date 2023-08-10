package com.example.foodapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.foodapp.models.ProductModels;
import com.example.foodapp.models.ProductTypeModels;
import com.example.foodapp.models.UserModels;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SupermarketManager";
    public static final String TABLE_PRODUCT = "Product";
    public static final String TABLE_PRODUCTTYPE = "ProductType";
    public static final String TABLE_USER = "User";

    //------------------- TÊN CÁC CỘT BẢNG LOẠI SẢN PHẨM
    private static final String C_IDTYPE = "IDType";
    private static final String C_IMAGETYPE = "ImageType";
    private static final String C_NAMETYPE = "NameType";

    //------------------- TÊN CÁC CỘT BẢNG SẢN PHẨM
    private static final String C_IDPRODUCT = "ID";
    private static final String C_IMAGEPRODUCT = "ImageProduct";
    private static final String C_NAMEPRODUCT = "NameProduct";
    private static final String C_PRICEPRODUCT = "PriceProduct";

    //------------------- TÊN CÁC CỘT BẢNG KHÁCH HÀNG
    private static final String C_IDUSER = "IDUser";
    private static final String C_FULLNAME = "FullName";
    private static final String C_PHONE = "Phone";
    private static final String C_PASSWORD = "Password";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //---------- TẠO BẢNG KHÁCH HÀNG
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + C_IDUSER + " INTEGER PRIMARY KEY, "
                + C_FULLNAME + " TEXT, "
                + C_PHONE + " TEXT, "
                + C_PASSWORD + " TEXT)";
        db.execSQL(CREATE_USER_TABLE);

        //---------- TẠO BẢNG LOẠI SẢN PHẨM
        String CREATE_PRODUCT_TYPE_TABLE = "CREATE TABLE " + TABLE_PRODUCTTYPE + "("
                + C_IDTYPE + " INTEGER PRIMARY KEY, "
                + C_IMAGETYPE + " INTEGER, "
                + C_NAMETYPE + " TEXT)";
        db.execSQL(CREATE_PRODUCT_TYPE_TABLE);

        //---------- TẠO BẢNG SẢN PHẨM
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCT + "("
                + C_IDPRODUCT + " INTEGER PRIMARY KEY, "
                + C_IMAGEPRODUCT + " INTEGER, "
                + C_NAMEPRODUCT + " TEXT, "
                + C_PRICEPRODUCT+ " TEXT, "
                + C_IDTYPE + " INTEGER, "
                + "FOREIGN KEY (" + C_IDTYPE + ") REFERENCES " + TABLE_PRODUCTTYPE + "(" + C_IDTYPE + "))";
        db.execSQL(CREATE_PRODUCTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTTYPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        onCreate(db);
    }

    //-------------------------------- BẢNG LOẠI SẢN PHẨM
    void addProductType(ProductTypeModels productTypeModels) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(C_IMAGETYPE, productTypeModels.getImageType());
        values.put(C_NAMETYPE, productTypeModels.getNameType());

        db.insert(TABLE_PRODUCTTYPE, null, values);
        db.close();
    }

    ProductTypeModels getProductType(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRODUCTTYPE, new String[] { C_IDTYPE, C_IMAGETYPE, C_NAMETYPE }, C_IDTYPE + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ProductTypeModels productTypeModels = new ProductTypeModels(Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)), cursor.getString(2));
        return productTypeModels;
    }

    public List<ProductTypeModels> getAllProductsType() {
        List<ProductTypeModels> productTypeList = new ArrayList<ProductTypeModels>();

        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTTYPE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ProductTypeModels productType = new ProductTypeModels();

                productType.setId(Integer.parseInt(cursor.getString(0)));
                productType.setImageType(Integer.parseInt(cursor.getString(1)));
                productType.setNameType(cursor.getString(2));

                productTypeList.add(productType);
            } while (cursor.moveToNext());
        }

        return productTypeList;
    }

    //-------------------------------- BẢNG SẢN PHẨM
    void addProduct(ProductModels productModels) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(C_IMAGEPRODUCT, productModels.getImg());
        values.put(C_NAMEPRODUCT, productModels.getName());
        values.put(C_PRICEPRODUCT, productModels.getPrice());
        values.put(C_IDTYPE, productModels.getIdType());

        db.insert(TABLE_PRODUCT, null, values);
        db.close();
    }

    ProductModels getProduct(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRODUCT, new String[] { C_IDPRODUCT, C_IMAGEPRODUCT, C_NAMEPRODUCT, C_PRICEPRODUCT, C_IDTYPE }, C_IDPRODUCT + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ProductModels productModels = new ProductModels(Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)), cursor.getString(2), cursor.getString(3), Integer.parseInt(cursor.getString(4)));
        return productModels;
    }

    public List<ProductModels> getAllProducts() {
        List<ProductModels> productList = new ArrayList<ProductModels>();

        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ProductModels product = new ProductModels();
                product.setId(Integer.parseInt(cursor.getString(0)));
                product.setImg(Integer.parseInt(cursor.getString(1)));
                product.setName(cursor.getString(2));
                product.setPrice(cursor.getString(3));
                product.setIdType(Integer.parseInt(cursor.getString(4)));
                productList.add(product);
            } while (cursor.moveToNext());
        }

        return productList;
    }

    public List<ProductModels> getAllProductsID(int IDType) {
        List<ProductModels> productList = new ArrayList<ProductModels>();

        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT + " WHERE " + C_IDTYPE + " = " +  IDType;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ProductModels product = new ProductModels();
                product.setId(Integer.parseInt(cursor.getString(0)));
                product.setImg(Integer.parseInt(cursor.getString(1)));
                product.setName(cursor.getString(2));
                product.setPrice(cursor.getString(3));
                product.setIdType(Integer.parseInt(cursor.getString(4)));
                productList.add(product);
            } while (cursor.moveToNext());
        }

        return productList;
    }

    public List<ProductModels> getAllRelatedProducts(int IDType, String nameProduct) {
        List<ProductModels> productList = new ArrayList<ProductModels>();

        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT + " WHERE " + C_IDTYPE + " = " +  IDType + " AND " + C_NAMEPRODUCT + " != " + "'" + nameProduct + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ProductModels product = new ProductModels();
                product.setId(Integer.parseInt(cursor.getString(0)));
                product.setImg(Integer.parseInt(cursor.getString(1)));
                product.setName(cursor.getString(2));
                product.setPrice(cursor.getString(3));
                product.setIdType(Integer.parseInt(cursor.getString(4)));
                productList.add(product);
            } while (cursor.moveToNext());
        }

        return productList;
    }

    public List<ProductModels> getSuggestedProducts() {
        List<ProductModels> productList = new ArrayList<ProductModels>();

        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT + " ORDER BY RANDOM() LIMIT 5";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ProductModels product = new ProductModels();
                product.setId(Integer.parseInt(cursor.getString(0)));
                product.setImg(Integer.parseInt(cursor.getString(1)));
                product.setName(cursor.getString(2));
                product.setPrice(cursor.getString(3));
                product.setIdType(Integer.parseInt(cursor.getString(4)));
                productList.add(product);
            } while (cursor.moveToNext());
        }

        return productList;
    }

    public List<ProductModels> searchProduct(String nameProduct) {
        List<ProductModels> productList = new ArrayList<ProductModels>();

        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT + " WHERE " + C_NAMEPRODUCT + " LIKE '%" + nameProduct + "%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ProductModels product = new ProductModels();
                product.setId(Integer.parseInt(cursor.getString(0)));
                product.setImg(Integer.parseInt(cursor.getString(1)));
                product.setName(cursor.getString(2));
                product.setPrice(cursor.getString(3));
                product.setIdType(Integer.parseInt(cursor.getString(4)));
                productList.add(product);
            } while (cursor.moveToNext());
        }

        return productList;
    }

    //-------------------------------- BẢNG KHÁCH HÀNG
    void addUser(UserModels userModels) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(C_FULLNAME, userModels.getFullName());
        values.put(C_PHONE, userModels.getPhoneNum());
        values.put(C_PASSWORD, userModels.getPassword());

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public int updateUser(UserModels userModels) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(C_FULLNAME, userModels.getFullName());
        values.put(C_PHONE, userModels.getPhoneNum());
        values.put(C_PASSWORD, userModels.getPassword());

        // updating row
        return db.update(TABLE_USER, values, C_IDUSER + " = ?",
                new String[] { String.valueOf(userModels.getId()) });
    }

    UserModels getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER, new String[] { C_IDUSER, C_FULLNAME, C_PHONE, C_PASSWORD }, C_IDUSER + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        UserModels userModels = new UserModels(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));
        return userModels;
    }

    public List<UserModels> getAllUser() {
        List<UserModels> userList = new ArrayList<UserModels>();

        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                UserModels user = new UserModels();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setFullName(cursor.getString(1));
                user.setPhoneNum(cursor.getString(2));
                user.setPassword(cursor.getString(3));
                userList.add(user);
            } while (cursor.moveToNext());
        }

        return userList;
    }

    //------------- KIỂM TRA BẢNG CÓ DỮ LIỆU KHÔNG
    public boolean isTableEmpty(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(tableName, null, null, null, null, null, null);
        boolean isEmpty = (cursor.getCount() == 0);
        cursor.close();
        db.close();
        return isEmpty;
    }

    //------------- XÓA TOÀN BỘ DỮ LIỆU TRONG BẢNG
    public void deleteDataTable(String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, null, null);
        db.close();
    }

}
