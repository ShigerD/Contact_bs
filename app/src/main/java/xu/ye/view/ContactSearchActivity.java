package xu.ye.view;

import java.util.ArrayList;
import java.util.List;

import xu.ye.R;
import xu.ye.bean.ContactBean;
import xu.ye.view.adapter.ContactHomeAdapter;
import xu.ye.view.adapter.ContactSearchAdapter;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
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

public class ContactSearchActivity extends Activity{

	private ListView lv_result;
	private Button btn_search;
	private EditText et_search;
	private ContactSearchAdapter adapter;
	private List<ContactBean> contacts=new ArrayList<ContactBean>();
	
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
					ContactBean cb=queryMember(et_search.getText().toString());
					
					contacts.add(cb);
					if(contacts==null||contacts.isEmpty())
					{
						Toast.makeText(ContactSearchActivity.this, "没有相关信息", 2000).show();
					}
					else
					{
						adapter.notifyDataSetChanged();
					}
					
				}
				else{
					Toast.makeText(ContactSearchActivity.this, "搜索字符不能为空", 2000).show();
				}
			}
		});
		
		adapter = new ContactSearchAdapter(ContactSearchActivity.this, contacts);
		lv_result.setAdapter(adapter);
		lv_result.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				ContactBean cb=(ContactBean) parent.getItemAtPosition(position);
				
				Uri uri = ContactsContract.Contacts.CONTENT_URI;
				Uri personUri = ContentUris.withAppendedId(uri, cb.getContactId());
				Intent intent2 = new Intent();
				intent2.setAction(Intent.ACTION_VIEW);
				intent2.setData(personUri);
				startActivity(intent2);
			}
		});
	}

	
	private ContactBean queryMember(String name1){

		ContactBean cb = null;

		Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; // 联系人的Uri
		String[] projection = { 
				ContactsContract.CommonDataKinds.Phone._ID,
				ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
				ContactsContract.CommonDataKinds.Phone.DATA1,
				"sort_key",
				ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
				ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
				ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY
		}; // 查询的列
		Cursor cursor = getContentResolver().query(uri, projection, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " like '%" + name1+"%'", null, null);
		if (cursor != null && cursor.getCount() > 0) {
			List list = new ArrayList<ContactBean>();
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				cursor.moveToPosition(i);
				String name = cursor.getString(1);
				String number = cursor.getString(2);
				String sortKey = cursor.getString(3);
				int contactId = cursor.getInt(4);
				Long photoId = cursor.getLong(5);
				String lookUpKey = cursor.getString(6);

				cb = new ContactBean();
				cb.setDisplayName(name);
//				if (number.startsWith("+86")) {// 去除多余的中国地区号码标志，对这个程序没有影响。
//					cb.setPhoneNum(number.substring(3));
//				} else {
					cb.setPhoneNum(number);
//				}
				cb.setSortKey(sortKey);
				cb.setContactId(contactId);
				cb.setPhotoId(photoId);
				cb.setLookUpKey(lookUpKey);
			}
		}
		cursor.close();
		return cb;
	}
	
	
}
