package xu.ye.view;

import android.app.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

import xu.ye.R;
import xu.ye.bean.BackList;
import xu.ye.view.other.TopPopup;

public class Tab extends Activity{
	private Button menuBtn1;
	private Button menuBtn2;
	private Button menuBtn3;
	private Button menuBtn4;
	private Button menuBtn5;
	private Button menuBtn6;
	private DbUtils dbUtils;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		LayoutInflater inflater = getLayoutInflater();
		final View layout = inflater.inflate(R.layout.tab, null);//获取自定义布局
		builder.setView(layout);

		dbUtils=DbUtils.create(this);
		menuBtn1=(Button)findViewById(R.id.button1);
		menuBtn2=(Button)findViewById(R.id.button2);
		menuBtn3=(Button)findViewById(R.id.button3);
		menuBtn4=(Button)findViewById(R.id.button4);
		menuBtn5=(Button)findViewById(R.id.button5);
		menuBtn6=(Button)findViewById(R.id.button6);
		menuBtn1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
		           Intent intent=new Intent();
		           intent.setClass(Tab.this, MimaActivity.class);
		           startActivity(intent);
			}
		});
		
		menuBtn2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
	            Intent intent=new Intent();
	            intent.setClass(Tab.this, ContactSearchActivity.class);
	            startActivity(intent);
			}
		});
		menuBtn3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
		           Intent intent=new Intent();
		           intent.setClass(Tab.this, MsgSearchActivity.class);
		           startActivity(intent);
			}
		});

		menuBtn4.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Toast.makeText(Tab.this, "备份成功",Toast.LENGTH_LONG ).show();
			}
		});
		menuBtn5.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				sendBroadcast(new Intent("update"));
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
					Toast.makeText(Tab.this, "恢复成功", Toast.LENGTH_LONG).show();
				} catch (DbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		menuBtn6.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(Tab.this, HomeSettintActivity.class);
				startActivity(intent);
			}
		});
	}
	

//    menu.add(Menu.NONE, Menu.FIRST + 1, 1, "设置密码").setIcon(
//
//    android.R.drawable.ic_menu_add);
// 
//    menu.add(Menu.NONE, Menu.FIRST + 2, 2, "联系人搜索").setIcon(
//
//            android.R.drawable.ic_menu_add);
//
//    menu.add(Menu.NONE, Menu.FIRST + 3, 2, "短信搜索").setIcon(
//
//            android.R.drawable.ic_menu_add);
//    menu.add(Menu.NONE, Menu.FIRST + 4, 2, "短信备份").setIcon(
//
//            android.R.drawable.ic_menu_add);
//    menu.add(Menu.NONE, Menu.FIRST + 5, 2, "短信恢复").setIcon(
//
//            android.R.drawable.ic_menu_add);

	
}
