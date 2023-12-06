package local.hal.sa42.android.favoriteshops00727;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.appbar.MaterialToolbar;

import local.hal.sa42.android.favoriteshops00727.data.ShopRepository;
import local.hal.sa42.android.favoriteshops00727.data.local.DatabaseHelper;
import local.hal.sa42.android.favoriteshops00727.data.local.Shop;

/**
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

        MaterialToolbar tvNameEdit = findViewById(R.id.tvNameEdit);
        Menu menu = tvNameEdit.getMenu();
        MenuItem btnSave = menu.findItem(R.id.btnSave);
        MenuItem btnDelete = menu.findItem(R.id.btnDelete);
        tvNameEdit.setNavigationOnClickListener(new BackButtonClickListener());
        tvNameEdit.setOnMenuItemClickListener(new ToolbarMenuItemClickListener());

        Intent intent = getIntent();
        _mode = intent.getIntExtra("mode", Consts.MODE_INSERT);

        if (_mode == Consts.MODE_INSERT) {
            tvNameEdit.setTitle(R.string.tv_name_insert);
            btnSave.setTitle("登録");
            btnDelete.setVisible(false);
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
     * Toolbar上の戻るボタンがクリックされた時のリスナクラス。
     */
    private class BackButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    /**
     * Toolbar上のメニューが選択された時のリスナクラス。
     */
    private class ToolbarMenuItemClickListener implements
            Toolbar.OnMenuItemClickListener {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            boolean returnVal = true;
            int itemId = item.getItemId();
            if (itemId == R.id.btnSave) {
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
                            inputTel, inputUrl, inputNote);
                    if (saveResult) {
                        finish();
                    } else {
                        Toast.makeText(ShopEditActivity.this, R.string.msg_save_error,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (itemId == R.id.btnDelete) {
                DeleteConfirmDialogFragment dialog = new DeleteConfirmDialogFragment(_shopRepository, _idNo);
                FragmentManager manager = getSupportFragmentManager();
                dialog.show(manager, "FullDialogFragment");
            } else {
                returnVal = false;
            }
            return returnVal;
        }
    }
}
