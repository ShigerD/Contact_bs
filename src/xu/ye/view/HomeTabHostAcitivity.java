package xu.ye.view;

import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

import xu.ye.R;
import xu.ye.bean.BackList;
import xu.ye.view.other.SystemScreenInfo;
import xu.ye.view.ui.AnimationTabHost;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

public class HomeTabHostAcitivity extends TabActivity {

	private AnimationTabHost mTabHost;
	private TabWidget mTabWidget;
	private ImageView cursor;
	private int offset = 0;
	private int currIndex = 0;
	private int bmpW;
	private ImageView[] views;
	private DbUtils dbUtils;
	private LocalBroadcastManager localBroadcastManager;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_hometabhost);
		dbUtils=DbUtils.create(this);
		SystemScreenInfo.getSystemInfo(HomeTabHostAcitivity.this);

		InitImageView();

		localBroadcastManager= LocalBroadcastManager.getInstance(this);
		mTabWidget = (TabWidget) findViewById(android.R.id.tabs);
		mTabHost = (AnimationTabHost) findViewById(android.R.id.tabhost);

		new Handler().postDelayed(new Runnable() {
			public void run() {
				initBottomMenu();
			}
		}, 300);

		init();
	}

	private int getImageId(int index, boolean isSelect){
		int result = -1;
		closeOptionsMenu();
		switch (index) {

		case 0: 
			result = isSelect ? R.drawable.tab_dial_selected : R.drawable.tab_dial_normal;
			break;
		case 1:
			result = isSelect ? R.drawable.tab_contact_selected : R.drawable.tab_contact_normal;
			break;
		case 2:
			result = isSelect ? R.drawable.tab_sms_selected : R.drawable.tab_sms_normal;
			break;
		case 3:
			result = isSelect ? R.drawable.tab_settings_selected : R.drawable.tab_settings_normal;
			openOptionsMenu();
//			sendBroadcast(new Intent("update"));
//			sendBroadcast(new Intent("cn.etzmico.broadcastreceiverregister.SENDBROADCAST"));
			break;
		}
		return result;
	}

	private void initBottomMenu() {
		int viewCount = mTabWidget.getChildCount();
		views = new ImageView[4];
		for (int i = 0; i <4; i++) {
			View v = (LinearLayout) mTabWidget.getChildAt(i);
			views[i] = (ImageView) v.findViewById(R.id.main_activity_tab_image);
			
		}
//
		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
			public void onTabChanged(String tabId) {
				int tabID = Integer.valueOf(tabId);
				Log.v("点钟id", tabId);
				for (int i = 0; i < views.length; i++) {
					if (i!=tabID) {
						views[i].setImageResource(getImageId(i, false));
					}
				}
				views[tabID].setImageResource(getImageId(tabID, true));
				onPageSelected(tabID);
			}
		});
	}

	private void init() {
		setIndicator("拨号", 0, new Intent(this, HomeDialActivity.class), R.drawable.tab_dial_selected);
		setIndicator("联系人", 1, new Intent(this, HomeContactActivity.class), R.drawable.tab_contact_normal);
		setIndicator("信息", 2, new Intent(this, HomeSMSActivity.class), R.drawable.tab_sms_normal);
		//setIndicator("设置", 3, new Intent(this, HomeTabHostAcitivity.class), R.drawable.tab_settings_normal);
		setIndicator("设置", 3, new Intent(this, Tab.class), R.drawable.tab_settings_normal);
		//setIndicator("设置", 3, new Intent(this, HomeSettintActivity.class), R.drawable.tab_settings_normal);

		mTabHost.setOpenAnimation(true);
//		onPageSelected(1);
	}

	private void setIndicator(String ss, int tabId, Intent intent, int image_id) {

		View localView = LayoutInflater.from(this.mTabHost.getContext()).inflate(R.layout.tab_widget_view, null);
		((ImageView) localView.findViewById(R.id.main_activity_tab_image)).setImageResource(image_id);
		((TextView) localView.findViewById(R.id.main_activity_tab_text)).setText(ss);
		String str = String.valueOf(tabId);
		TabHost.TabSpec localTabSpec = mTabHost.newTabSpec(str).setIndicator(localView).setContent(intent);
		mTabHost.addTab(localTabSpec);
	}

	private void InitImageView() {
		

			

		cursor = (ImageView) findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.main_tab_anim_light).getWidth();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;
		offset = (screenW / 4 - bmpW) / 2;
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor.setImageMatrix(matrix);
	}

	public void onPageSelected(int arg0) {

		int one = offset * 2 + bmpW;
		Animation animation = null;
		animation = new TranslateAnimation(one * currIndex, one * arg0 , 0, 0);
		currIndex = arg0;
		animation.setFillAfter(true);
		animation.setDuration(300);
		cursor.startAnimation(animation);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
		}
		return false;
	}

	protected void onDestroy() {
		super.onDestroy();
	}
	
	
	//菜单栏
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
   

        menu.add(Menu.NONE, Menu.FIRST + 1, 1, "设置密码").setIcon(

        android.R.drawable.ic_menu_add);
     
        menu.add(Menu.NONE, Menu.FIRST + 2, 2, "联系人搜索").setIcon(

                android.R.drawable.ic_menu_add);

        menu.add(Menu.NONE, Menu.FIRST + 3, 2, "短信搜索").setIcon(

                android.R.drawable.ic_menu_add);
        menu.add(Menu.NONE, Menu.FIRST + 4, 2, "短信备份").setIcon(

                android.R.drawable.ic_menu_add);
        menu.add(Menu.NONE, Menu.FIRST + 5, 2, "短信恢复").setIcon(

                android.R.drawable.ic_menu_add);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
        case Menu.FIRST + 1:
        {
           Intent intent=new Intent();
          intent.setClass(HomeTabHostAcitivity.this, MimaActivity.class);
          startActivity(intent);
        }
            break;
        case Menu.FIRST + 2:
        {
            Intent intent=new Intent();
           intent.setClass(HomeTabHostAcitivity.this, ContactSearchActivity.class);
           startActivity(intent);
        }
             break;
        case Menu.FIRST + 3:
        {
           Intent intent=new Intent();
           intent.setClass(HomeTabHostAcitivity.this, MsgSearchActivity.class);
           startActivity(intent);
        }
             break;
        case Menu.FIRST + 4:
        {
           Toast.makeText(HomeTabHostAcitivity.this, "备份成功", Toast.LENGTH_LONG).show();
        }
        break;
        case Menu.FIRST + 5:
        {
        	List<BackList> backLists=new ArrayList<BackList>();
        	try {
				backLists=dbUtils.findAll(BackList.class);
				if (backLists!=null) {
					for (int i = 0; i < backLists.size(); i++) {
						if (backLists.get(i).getLxid().equals("0")) {
							dbUtils.delete(backLists.get(i));
						}
					}
				}
				  Toast.makeText(HomeTabHostAcitivity.this, "恢复成功", Toast.LENGTH_LONG).show();


				sendBroadcast(new Intent("update"));
//
        	} catch (DbException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}




             break;
        }
        return false;
    }



    @Override
    public void onOptionsMenuClosed(Menu menu) {
        /*Toast.makeText(this, "选项菜单关闭了", Toast.LENGTH_LONG).show();*/

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        /*Toast.makeText(this,
                "选项菜单显示之前onPrepareOptionsMenu方法会被调用，你可以用此方法来根据打当时的情况调整菜单",
                Toast.LENGTH_LONG).show();
*/

        // 如果返回false，此方法就把用户点击menu的动作给消费了，onCreateOptionsMenu方法将不会被调用
        return true;

    }

}