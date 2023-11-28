package local.hal.sa42.android.favoriteshops00727.data.local;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.sql.Timestamp;

/**
 * SA42 Androidサンプル10 メモ帳アプリ
 * <p>
 * データベース上のshopsテーブルを操作するクラス。
 *
 * @author Shinzo SAITO
 */
public class ShopDAO {
    /**
     * 全データ検索メソッド。
     *
     * @param db SQLiteDatabaseオブジェクト。
     * @return 検索結果のCursorオブジェクト。
     */
    public static Cursor findAll(SQLiteDatabase db) {
        String sql = "SELECT * FROM shops ORDER BY updated_at DESC";
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }

    /**
     * 主キーによる検索。
     *
     * @param db SQLiteDatabaseオブジェクト。
     * @param id 主キー値。
     * @return 主キーに対応するデータを格納したShopオブジェクト。対応するデータが存在しない場合はnull。
     */
    public static Shop findByPK(SQLiteDatabase db, long id) {
        String sql = "SELECT * FROM shops WHERE _id = " + id;
        Cursor cursor = db.rawQuery(sql, null);
        Shop result = null;
        if (cursor.moveToFirst()) {
            int idxName = cursor.getColumnIndex("name");
            int idxTel = cursor.getColumnIndex("tel");
            int idxUrl = cursor.getColumnIndex("url");
            int idxNote = cursor.getColumnIndex("note");

            int idxUpdateAt = cursor.getColumnIndex("updated_at");
            String name = cursor.getString(idxName);
            String tel = cursor.getString(idxTel);
            String url = cursor.getString(idxUrl);
            String note = cursor.getString(idxNote);
            long updateAtLong = cursor.getLong(idxUpdateAt);

            Timestamp updateAt = new Timestamp(updateAtLong);

            result = new Shop();
            result.setId(id);
            result.setName(name);
            result.setTel(tel);
            result.setUrl(url);
            result.setNote(note);
            result.setUpdatedAt(updateAt);
        }
        cursor.close();
        return result;
    }

    /**
     * メモ情報を更新するメソッド。
     *
     * @param db   SQLiteDatabaseオブジェクト。
     * @param shop メモ情報。
     * @return 更新件数。
     */
    public static int update(SQLiteDatabase db,
                             Shop shop) {
        String sql = "UPDATE shops SET name = ?, tel = ?,url = ?, note = ?, updated_at = datetime" +
                "('now') WHERE" +
                " _id = ?";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindString(1, shop.getName());
        stmt.bindString(2, shop.getTel());
        stmt.bindString(3, shop.getUrl());
        stmt.bindString(4, shop.getNote());
        stmt.bindLong(5, shop.getId());
        int result = stmt.executeUpdateDelete();
        return result;
    }

    /**
     * メモ情報を新規登録するメソッド。
     *
     * @param db   SQLiteDatabaseオブジェクト。
     * @param shop メモ情報。
     * @return 登録されたレコードの主キー値。
     */
    public static long insert(SQLiteDatabase db,
                              Shop shop) {
        String sql = "INSERT INTO shops (name, tel, url, note, updated_at) VALUES (?, ?, ?, ?," +
                "datetime" +
                "('now'))";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindString(1, shop.getName());
        stmt.bindString(2, shop.getTel());
        stmt.bindString(3,shop.getUrl());
        stmt.bindString(4, shop.getNote());
        long id = stmt.executeInsert();
        return id;
    }

    /**
     * メモ情報を削除するメソッド。
     *
     * @param db SQLiteDatabaseオブジェクト。
     * @param id 主キー値。
     * @return 削除件数。
     */
    public static int delete(SQLiteDatabase db, long id) {
        String sql = "DELETE FROM shops WHERE _id = ?";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindLong(1, id);
        int result = stmt.executeUpdateDelete();
        return result;
    }
}
