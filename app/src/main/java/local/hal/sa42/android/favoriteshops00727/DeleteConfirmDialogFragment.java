package local.hal.sa42.android.favoriteshops00727;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;
import android.app.AlertDialog;

import androidx.fragment.app.DialogFragment;
import androidx.annotation.NonNull;

import local.hal.sa42.android.favoriteshops00727.data.ShopRepository;

/**
 * SA42 Androidサンプル11 ダイアログ
 * <p>
 * 通常ダイアログクラス。
 *
 * @author Shinzo SAITO
 */
public class DeleteConfirmDialogFragment extends DialogFragment {
    private ShopRepository _shopRepository;
    private long _idNo;

    public DeleteConfirmDialogFragment(ShopRepository shopRepository, long idNo) {
        _shopRepository = shopRepository;
        _idNo = idNo;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity parent = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(parent);
        builder.show();
        builder.setTitle(R.string.dlg_full_title);
        builder.setMessage(R.string.dlg_full_msg);
        builder.setPositiveButton(R.string.dlg_btn_ok, new
                DialogButtonClickListener());
        builder.setNegativeButton(R.string.dlg_btn_ng, new
                DialogButtonClickListener());
        AlertDialog dialog = builder.create();
        return dialog;
    }

    /**
     * ダイアログのボタンが押されたときの処理が記述されたメンバクラス。
     */
    private class DialogButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Activity parent = getActivity();
            String msg = "";
            if (which == DialogInterface.BUTTON_POSITIVE) {
                _shopRepository.deleteMemo(_idNo);
                msg = "削除しました。";
                parent.finish();
                Toast.makeText(parent, msg, Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        }
    }
}
