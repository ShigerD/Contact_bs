package xu.ye.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

import xu.ye.R;
import xu.ye.application.MyApplication;
import xu.ye.bean.BackList;
import xu.ye.bean.SMSBean;
import xu.ye.uitl.BaseIntentUtil;
import xu.ye.uitl.RexseeSMS;
import xu.ye.view.adapter.HomeSMSAdapter;
import xu.ye.view.adapter.MessageBoxListAdapter;
import xu.ye.view.sms.MessageBoxList;
import xu.ye.view.sms.NewSMSActivity;
import android.R.integer;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
public class HomeSMSActivity extends Activity implements OnCreateContextMenuListener   {

	private ListView listView;
	private HomeSMSAdapter adapter;
	private RexseeSMS rsms;
	private Button newSms;
	private DbUtils dbUtils;
	private List<BackList> backLists=new ArrayList<BackList>();

	//定义一个过滤器；
	private IntentFilter intentFilter;

	//定义一个广播监听器；
	private NetChangReceiver netChangReceiver;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		init();

		//动态注册广播

		intentFilter = new IntentFilter();
		//添加过滤的Action值；
		intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
		intentFilter.addAction("update");
		//实例化广播监听器；
		//将广播监听器和过滤器注册在一起；
		registerReceiver(myReceiver, intentFilter);
		Log.e("myReceiver", "++myReceiver Oncreate++");
	}

	private BroadcastReceiver myReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
//            Toast.makeText(context, "myReceiver receive", Toast.LENGTH_SHORT)
//                    .show();
			Log.e("HomeSMSActivity", "myReceiver");
			//更新ui的方法
			init();
		}
	};




	public void init(){
		
		setContentView(R.layout.home_sms_page);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		dbUtils=DbUtils.create(this);
		listView = (ListView) findViewById(R.id.list);
		adapter = new HomeSMSAdapter(HomeSMSActivity.this);
		try {
			backLists=dbUtils.findAll(BackList.class);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rsms = new RexseeSMS(HomeSMSActivity.this);
		List<SMSBean> list_mmt = rsms.getThreadsNum(rsms.getThreads(0));
		List<SMSBean> smsBeans=new ArrayList<SMSBean>();
		if (backLists!=null&&backLists.size()>0) {
			for (int i = 0; i < list_mmt.size(); i++) {
				int temp=0;
				for (int j = 0; j < backLists.size(); j++) {
					if (backLists.get(j).getSmsid().equals(list_mmt.get(i).getThread_id())) {
						temp=1;
						break;
					}
				}
				if (temp==0) {
					smsBeans.add(list_mmt.get(i));
				}
			}
		}else {
			smsBeans=list_mmt;
		}
		
		adapter.assignment(smsBeans);

		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Map<String, String> map = new HashMap<String, String>();
				SMSBean sb = adapter.getItem(position);
				map.put("phoneNumber", sb.getAddress());
				map.put("threadId", sb.getThread_id());
				BaseIntentUtil.intentSysDefault(HomeSMSActivity.this, MessageBoxList.class, map);
			}
		});
		
		
		listView.setOnCreateContextMenuListener(this);
	
		
		newSms = (Button) findViewById(R.id.newSms);
		newSms.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				BaseIntentUtil.intentSysDefault(HomeSMSActivity.this, NewSMSActivity.class, null);
			}
		});
		
	
	}


	
	/** 
	  * 创建上下文菜单 
	  */ 
	 @Override 
	 public void onCreateContextMenu(ContextMenu menu, View v, 
	   ContextMenuInfo menuInfo) 
	 { 
		 menu.setHeaderTitle("操作");
	        //添加菜单项
	     menu.add(0, 0, 0, "删除");
	       
	 }
	/**
	  * 上下文菜单的事件处理
	  */
	 @Override
	 public boolean onContextItemSelected(MenuItem item)
	 {
	 // AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo)item.getMenuInfo(); 
	  //System.out.println("position:"+menuInfo.position);
	  switch (item.getItemId())
	  {
	  case 0://在这里怎样该获取要删除的ListView的选中项的内容呢？
		  SMSBean sms= (SMSBean)adapter.getItem(item.getItemId());
		  
//			getContentResolver().delete(Uri.parse("content://sms"), "thread_id=?", new String []{sms.getThread_id()+""});
			adapter.getList().remove(sms);
			adapter.notifyDataSetChanged();
			BackList backList=new BackList();
			backList.setLxid("0");
			backList.setSmsid(sms.getThread_id());
		try {
			dbUtils.save(backList);
			Toast.makeText(HomeSMSActivity.this, "删除短信成功", 2000).show();
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		  
	   break;
	
	  default:
	   break;
	  }
	  return super.onContextItemSelected(item);
	 }
	   @Override
			public boolean onKeyDown(int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if(keyCode==KeyEvent.KEYCODE_BACK)
				 {
				   ((MyApplication)getApplication()).promptExit(this);	
					return true;
				 }
				return false;
			}	



}
