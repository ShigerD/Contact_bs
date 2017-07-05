package xu.ye.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xu.ye.R;
import xu.ye.bean.ContactBean;
import xu.ye.bean.MessageBean;
import xu.ye.bean.SMSBean;
import xu.ye.uitl.BaseIntentUtil;
import xu.ye.view.adapter.ContactHomeAdapter;
import xu.ye.view.adapter.MsgSearchAdapter;
import xu.ye.view.adapter.MsgSearchAdapter;
import xu.ye.view.sms.MessageBoxList;
import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MsgSearchActivity extends Activity{

	private ListView lv_result;
	private Button btn_search;
	private EditText et_search;
	private MsgSearchAdapter adapter;
	private List<MessageBean> msgs=new ArrayList<MessageBean>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		btn_search=(Button)findViewById(R.id.btn_search);
		et_search=(EditText)findViewById(R.id.et_search);
		lv_result=(ListView)findViewById(R.id.lv_result);
		
		btn_search.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!et_search.getText().toString().equals(""))
				{
					List list=queryMember(et_search.getText().toString());
					msgs=list;
					if(msgs==null||msgs.isEmpty()){
						Toast.makeText(MsgSearchActivity.this, "没有相关信息", 2000).show();
					}
					else{
					adapter.setList(msgs);
					adapter.notifyDataSetChanged();
					}
				}
				else{
					Toast.makeText(MsgSearchActivity.this, "搜索字符不能为空", 2000).show();
				}
			}
		});
		
		adapter = new MsgSearchAdapter(MsgSearchActivity.this,msgs);
		lv_result.setAdapter(adapter);
		lv_result.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Map<String, String> map = new HashMap<String, String>();
				MessageBean sb = adapter.getItem(position);
				map.put("phoneNumber", sb.getAddress());
				map.put("threadId", sb.getThreadId());
				BaseIntentUtil.intentSysDefault(MsgSearchActivity.this, MessageBoxList.class, map);
				
			}
		});
	}

	
	private List queryMember(String body1){

		MessageBean cb = null;
		List list = null;
		Uri uri = Uri.parse("content://sms"); 
		String[] projection = new String[] {
				"date",
				"address",
				"person",
				"body",
				"type",
				"thread_id"
		};// 查询的列
	   Cursor cursor=getContentResolver().query(uri, projection, "body like'%"+body1+"%'", null, "date asc");
	   
	   if (cursor != null && cursor.getCount() > 0) {
		   list=new ArrayList<MessageBean>();
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				cursor.moveToPosition(i);
				String date = cursor.getString(0);
				String address = cursor.getString(1);
				String person = cursor.getString(2);
				String body = cursor.getString(3);
				String type = cursor.getString(4);
				String thread_id=cursor.getString(5);
				
				cb = new MessageBean();
				cb.setDate(date);
				cb.setName(person);
				cb.setText(body);
				cb.setAddress(address);
				cb.setThreadId(thread_id);
                				
				list.add(cb);
			}
		}
	     
		cursor.close();
		return list;
		
	}
	
	
}
