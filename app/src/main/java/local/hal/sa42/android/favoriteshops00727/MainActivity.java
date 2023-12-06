package local.hal.sa42.android.favoriteshops00727;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

import local.hal.sa42.android.favoriteshops00727.data.ShopRepository;
import local.hal.sa42.android.favoriteshops00727.data.local.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_main);

        MaterialToolbar tbMain = findViewById(R.id.tbMain);
        tbMain.setNavigationOnClickListener(new NewButtonClickListener());

        ListView lvMemoList = findViewById(R.id.lvMemoList);
        lvMemoList.setOnItemClickListener(new ListItemClickListener());

        _helper = new DatabaseHelper(MainActivity.this);
        _shopRepository = new ShopRepository(_helper);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Cursor cursor = _shopRepository.getAllListDataCursor();
        ListView lvMemoList = findViewById(R.id.lvMemoList);
        String[] from = {"name"};
        int[] to = {android.R.id.text1};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(MainActivity.this,
                android.R.layout.simple_list_item_1, cursor, from, to, 0);
        lvMemoList.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        _helper.close();
        super.onDestroy();
    }

    /**
     * 新規ボタンがタップされた時のリスなクラス。
     */
    private class NewButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this,
                    ShopEditActivity.class);
            intent.putExtra("mode", Consts.MODE_INSERT);
            startActivity(intent);

        }
    }

    /**
     * リストがクリックされた時のリスナクラス。
     */
    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long
                id) {
            Cursor item = (Cursor) parent.getItemAtPosition(position);
            int idxId = item.getColumnIndex("_id");
            long idNo = item.getLong(idxId);

            Intent intent = new Intent(MainActivity.this, ShopEditActivity.class);
            intent.putExtra("mode", Consts.MODE_EDIT);
            intent.putExtra("idNo", idNo);
            startActivity(intent);
        }
    }
}
