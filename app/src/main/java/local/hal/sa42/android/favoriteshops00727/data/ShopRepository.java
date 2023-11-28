package local.hal.sa42.android.favoriteshops00727.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import local.hal.sa42.android.favoriteshops00727.Consts;
import local.hal.sa42.android.favoriteshops00727.data.local.DatabaseHelper;
import local.hal.sa42.android.favoriteshops00727.data.local.Shop;
import local.hal.sa42.android.favoriteshops00727.data.local.ShopDAO;

/**
 * SA42 Androidサンプル10 メモ帳アプリ
 * <p>
 * メモ情報に関するリポジトリクラスクラス。
 *
 * @author Shinzo SAITO
 */
public class ShopRepository {
    /**
     * データベースヘルパーオブジェクト。
     */
    private DatabaseHelper _helper;

    /**
     * コンストラクタ。
     *
     * @param helper データベースヘルパーオブジェクト。
     */
    public ShopRepository(DatabaseHelper helper) {
        _helper = helper;
    }

    /**
     * 全リストデータのカーソルオブジェクトを取得するメソッド。
     *
     * @return 全リストデータのカーソルオブジェクト。
     */
    public Cursor getAllListDataCursor() {
        SQLiteDatabase db = _helper.getWritableDatabase();
        return ShopDAO.findAll(db);
    }

    /**
     * 1件分のメモ情報を取得するメソッド。
     *
     * @param id メモ情報ID。
     * @return 引数に該当するメモ情報エンティティオブジェクト。該当データがない場合はnull。
     */
    public Shop getOneTask(long id) {
        SQLiteDatabase db = _helper.getWritableDatabase();
        return ShopDAO.findByPK(db, id);
    }

    /**
     * メモ情報を保存するメソッド。
     * メモ情報の登録(INSERT)か更新(UPDATE)かは、第1引数の値で自動判定する。
     *
     * @param mode    メモ情報の登録(INSERT)か更新(UPDATE)かを表す値。これは、Const'sクラスの定数に該当する。
     * @param id      主キーのメモ情報ID値。
     * @param name   shop_name。
     * @param tel tel
     * @param url url
     * @param note note
     * @return 保存処理が成功したかどうかを表す値。
     */
    public boolean saveMemo(int mode, long id, String name, String tel, String url, String note) {
        Shop shop = new Shop();
        shop.setId(id);
        shop.setName(name);
        shop.setTel(tel);
        shop.setUrl(url);
        shop.setNote(note);

        SQLiteDatabase db = _helper.getWritableDatabase();
        boolean result = false;
        if (mode == Consts.MODE_INSERT) {
            long insertedId = ShopDAO.insert(db, shop);
            if (insertedId >= 1) {
                result = true;
            }
        } else {
            int updateResult = ShopDAO.update(db, shop);
            if (updateResult == 1) {
                result = true;
            }
        }
        return result;
    }

    /**
     * タスク情報を削除するメソッド。
     *
     * @param id 主キーのタスクID値。
     * @return 削除処理が成功したかどうかを表す値。
     */
    public boolean deleteMemo(long id) {
        boolean result = false;
        SQLiteDatabase db = _helper.getWritableDatabase();
        int deleteResult = ShopDAO.delete(db, id);
        if (deleteResult == 1) {
            result = true;
        }
        return result;
    }
}
