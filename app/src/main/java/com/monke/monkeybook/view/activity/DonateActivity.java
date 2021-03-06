package com.monke.monkeybook.view.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hwangjr.rxbus.RxBus;
import com.monke.basemvplib.impl.IPresenter;
import com.monke.monkeybook.R;
import com.monke.monkeybook.base.MBaseActivity;
import com.monke.monkeybook.help.ACache;
import com.monke.monkeybook.help.Donate;
import com.monke.monkeybook.help.RxBusTag;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by GKF on 2018/1/13.
 * 捐赠页面
 */

public class DonateActivity extends MBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.vw_zfb_tz)
    CardView vwZfbTz;
    @BindView(R.id.vw_zfb_hb)
    CardView vwZfbHb;
    @BindView(R.id.vw_zfb_rwm)
    CardView vwZfbRwm;
    @BindView(R.id.vw_wx_rwm)
    CardView vwWxRwm;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.vw_qq_rwm)
    CardView vwQqRwm;
    @BindView(R.id.vw_zfb_hb_ssm)
    CardView vwZfbHbSsm;

    public static void startThis(Context context) {
        Intent intent = new Intent(context, DonateActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected IPresenter initInjector() {
        return null;
    }

    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.activity_donate);
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        this.setSupportActionBar(toolbar);
        setupActionBar();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void bindEvent() {
        vwZfbTz.setOnClickListener(view -> Donate.aliDonate(this));
        vwZfbHb.setOnClickListener(view -> openActionViewIntent("https://gedoor.github.io/MyBookshelf/zfbhbrwm.png"));
        vwZfbRwm.setOnClickListener(view -> openActionViewIntent("https://gedoor.github.io/MyBookshelf/zfbskrwm.jpg"));
        vwWxRwm.setOnClickListener(view -> openActionViewIntent("https://gedoor.github.io/MyBookshelf/wxskrwm.jpg"));
        vwQqRwm.setOnClickListener(view -> openActionViewIntent("https://gedoor.github.io/MyBookshelf/qqskrwm.jpg"));
        vwZfbHbSsm.setOnClickListener(view -> getZfbHb(this));
    }

    public static void getZfbHb(Context context) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(null, "537954522");
        if (clipboard != null) {
            clipboard.setPrimaryClip(clipData);
            Toast.makeText(context, "隐藏书源已开启\n红包码已复制\n支付宝首页搜索“537954522” 立即领红包", Toast.LENGTH_LONG).show();
        }
        try {
            PackageManager packageManager = context.getApplicationContext().getPackageManager();
            Intent intent = packageManager.getLaunchIntentForPackage("com.eg.android.AlipayGphone");
            assert intent != null;
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ACache.get(context).put("getZfbHb", "True", 3 * ACache.TIME_DAY);
            RxBus.get().post(RxBusTag.GET_ZFB_Hb, true);
        }
    }

    private void openActionViewIntent(String address) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(address));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.can_not_open, Toast.LENGTH_SHORT).show();
        }
    }

    //设置ToolBar
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.donate);
        }
    }

    //菜单
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
