package local.hal.sa42.android.favoriteshops00727;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import local.hal.sa42.android.favoriteshops00727.data.ShopRepository;
import local.hal.sa42.android.favoriteshops00727.data.local.DatabaseHelper;
import local.hal.sa42.android.favoriteshops00727.data.local.Shop;

/**
 * SA42 Androidサンプル10 メモ帳アプリ
 * 第2画面表示用アクティビティクラス。
 * メモ情報編集画面を表示する。
 *
 * @author Shinzo SAITO
 */
public class ShopEditActivity extends AppCompatActivity {
    /**
     * 新規登録モードか更新モードかを表すフィールド。
     */
    private int _mode = Consts.MODE_INSERT;
    /**
     * 更新モードの際、現在表示しているメモ情報のデータベース上の主キー値。
     */
    private long _idNo = 0;
    /**
     * データベースヘルパーオブジェクト。
     */
    private DatabaseHelper _helper;
    /**
     * メモ情報に関するリポジトリオブジェクト。
     */
    private ShopRepository _shopRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_edit);

        _helper = new local.hal.sa42.android.favoriteshops00727.data.local.DatabaseHelper(ShopEditActivity.this);
        _shopRepository = new ShopRepository(_helper);

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new SaveButtonClickListener());
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new BackButtonClickListener());
        Button btnFullDialog = findViewById(R.id.btnDelete);
        btnFullDialog.setOnClickListener(new ShowFullDialogClickListener());

        Intent intent = getIntent();
        _mode = intent.getIntExtra("mode", Consts.MODE_INSERT);

        if (_mode == Consts.MODE_INSERT) {
            TextView tvNameEdit = findViewById(R.id.tvNameEdit);
            tvNameEdit.setText(R.string.tv_name_insert);
            btnSave.setText(R.string.btn_insert);
            btnFullDialog.setVisibility(View.INVISIBLE);
        } else {
            _idNo = intent.getLongExtra("idNo", 0);
            Shop shopData = _shopRepository.getOneTask(_idNo);

            EditText etInputName = findViewById(R.id.etInputName);
            etInputName.setText(shopData.getName());
            EditText etInputTel = findViewById(R.id.etInputTel);
            etInputTel.setText(shopData.getTel());
            EditText etInputUrl = findViewById(R.id.etInputUrl);
            etInputUrl.setText(shopData.getUrl());
            EditText etInputNote = findViewById(R.id.etInputNote);
            etInputNote.setText(shopData.getNote());
        }
    }

    @Override
    protected void onDestroy() {
        _helper.close();
        super.onDestroy();
    }

    /**
     * 登録・更新ボタンがタップされたときのリスナクラス。
     */
    private class SaveButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            EditText etInputName = findViewById(R.id.etInputName);
            String inputName = etInputName.getText().toString();
            if (inputName.equals("")) {
                Toast.makeText(ShopEditActivity.this, R.string.msg_input_name,
                        Toast.LENGTH_SHORT).show();
            } else {
                EditText etInputTel = findViewById(R.id.etInputTel);
                String inputTel = etInputTel.getText().toString();
                EditText etInputUrl = findViewById(R.id.etInputUrl);
                String inputUrl = etInputUrl.getText().toString();
                EditText etInputNote = findViewById(R.id.etInputNote);
                String inputNote = etInputNote.getText().toString();
                boolean saveResult = _shopRepository.saveMemo(_mode, _idNo, inputName,
                        inputTel,inputUrl,inputNote);
                if (saveResult) {
                    finish();
                } else {
                    Toast.makeText(ShopEditActivity.this, R.string.msg_save_error,
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    /**
     * 戻るボタンがタップされたときのリスナクラス。
     */
    private class BackButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    /**
     * 通常ダイアログ表示ボタンがクリックされた時のリスナクラス。
     */
    private class ShowFullDialogClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            DeleteConfirmDialogFragment dialog = new DeleteConfirmDialogFragment(_shopRepository,_idNo);
            FragmentManager manager = getSupportFragmentManager();
            dialog.show(manager, "FullDialogFragment");
        }
    }
}
