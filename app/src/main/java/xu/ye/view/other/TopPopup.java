package xu.ye.view.other;

import com.ffly.demo.ImportVCardActivity;
import com.lidroid.xutils.DbUtils;

import xu.ye.R;
import xu.ye.bean.BackList;
import xu.ye.uitl.FromUtil;
import xu.ye.view.HomeContactActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;

public class TopPopup extends PopupWindow{
	
	private LayoutInflater inflater;
	private LinearLayout layout;
	private ListView listItem;
	private String[] StrItems;
	private ArrayAdapter<String> adapter;
	
	private Context mContext;
	private final String TAG="TopPopup";
	private DbUtils dbUtils;
	private Handler handler;
	public TopPopup(final Context context,final Handler handler) {
		super(context);
		mContext = context;
		this.handler=handler;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = (LinearLayout) inflater.inflate(R.layout.bottom_popup,
				null);
		setContentView(layout);
		dbUtils=DbUtils.create(mContext);
		// 设置弹出窗体的宽
		this.setWidth(400);
		// 设置弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		// 设置弹出窗体可点击
		this.setFocusable(true);
/*		// 设置弹出窗体动画效果
		this.setAnimationStyle(R.style.MainPopupAnim);*/
		
		listItem = new ListView(context);
		listItem.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.i(TAG, "arg2----->"+arg2);
				if(arg2==0){//从通讯录获取					
					SharedPreferences sharedPreferences = context.getSharedPreferences("mycontact", Context.MODE_PRIVATE);
					Editor editor = sharedPreferences.edit();//获取编辑器
					editor.putBoolean("isAddFormContact", true);
					editor.commit();
					Toast.makeText(context, "从通讯录添加成功", 2000).show();
				}
				else if(arg2==1){//从sdCard获取
					context.startActivity(new Intent(context, ImportVCardActivity.class));						
				}
				else if(arg2==2){//从sim卡中获取
					//FromUtil.doImportFromSim(context, null);
				//	deleteBack(context);
					Message msg=new Message();
					handler.sendMessage(msg);
				}
				
				((HomeContactActivity)context).init();//载入通讯录				
				//隐藏listview
				if(TopPopup.this.isShowing()){
					TopPopup.this.dismiss();
				}
				
			}
		});

        layout.addView(listItem);
	}
	
	public void show(View parent) {

		///显示在指定控件的下方
		this.showAsDropDown(parent);
		
		adapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,StrItems);
		
		listItem.setAdapter(adapter);
	}
	
	public void setItems(String [] items){
		StrItems = items;
	}
}