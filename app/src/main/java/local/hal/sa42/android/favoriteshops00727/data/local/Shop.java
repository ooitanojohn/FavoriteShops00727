package local.hal.sa42.android.favoriteshops00727.data.local;

import java.sql.Timestamp;

/**
 * SA42 Androidサンプル10 メモ帳アプリ
 * <p>
 * メモ情報を格納するエンティティクラス。
 *
 * @author Shinzo SAITO
 */
public class Shop {
    /**
     * 主キーのID値。
     */
    private long _id;
    /**
     * shop_name
     */
    private String _name;
    /**
     * tel
     */
    private String _tel;
    /**
     * url
     */
    private String _url;
    /**
     * note
     */
    private String _note;
    /**
     * 更新日時。
     */
    private Timestamp _updatedAt;

    //以下アクセサメソッド。

    public long getId() {
        return _id;
    }

    public void setId(long id) {
        _id = id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public String getTel() {
        return _tel;
    }

    public void setTel(String tel) {
        _tel = tel;
    }

    public String getUrl() {
        return _url;
    }

    public void setUrl(String url) {
        _url = url;
    }

    public String getNote() {
        return _note;
    }

    public void setNote(String note) {
        _note = note;
    }

    public Timestamp getUpdatedAt() {
        return _updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        _updatedAt = updatedAt;
    }
}
