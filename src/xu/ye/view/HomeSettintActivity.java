package xu.ye.view;

import java.util.ArrayList;
import java.util.List;
import xu.ye.R;
import xu.ye.application.MyApplication;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

public class HomeSettintActivity extends Activity {
	
	private SharedPreferences sharedPreferences ;
	
	private Spinner sp_dianhua;
	private Spinner sp_duanxin;
	
	
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.home_setting_page);
		initView();
		
	}
     //初始化视图
	private void initView() {
		
		sharedPreferences= getSharedPreferences("mycontact", Context.MODE_PRIVATE);
		final Editor editor = sharedPreferences.edit();//获取编辑器
		
		List<String> dianhuaList=new ArrayList<String>();
		dianhuaList.add("无");
		dianhuaList.add("强");
		dianhuaList.add("弱");
		dianhuaList.add("关闭");
		dianhuaList.add("自定义");
		sp_dianhua=(Spinner)findViewById(R.id.sp_dianhua);
		ArrayAdapter adapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, dianhuaList);  
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
	    sp_dianhua.setAdapter(adapter);
	    sp_dianhua.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				String dianhua=(String)arg0.getItemAtPosition(arg2);
				editor.putString("dianhua", dianhua);
				editor.commit();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	    
	    
	    
	    List<String> duanxinList=new ArrayList<String>();
	    duanxinList.add("无");
	    duanxinList.add("强");
	    duanxinList.add("弱");
	    duanxinList.add("关闭");
	    duanxinList.add("自定义");
		sp_duanxin=(Spinner)findViewById(R.id.sp_duanxin);
		ArrayAdapter duanxin_adapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, duanxinList);  
		duanxin_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
	    sp_duanxin.setAdapter(duanxin_adapter);
	    sp_duanxin.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				String dianhua=(String)arg0.getItemAtPosition(arg2);
				editor.putString("duanxin", dianhua);
				editor.commit();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
			
	    
	    Button dianhua_btn_zhidingyi=(Button)findViewById(R.id.dianhua_btn_zhidingyi);
	    dianhua_btn_zhidingyi.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(((String)sp_dianhua.getSelectedItem()).equals("自定义")){
				Intent intent =new Intent(HomeSettintActivity.this,HomeContactActivity01.class);
				startActivity(intent);			
				}
				else {
					Toast.makeText(HomeSettintActivity.this, "请先选择自定义模式", 2000).show();
				}
			}
		});
	    
	    Button duanxin_btn_zhidingyi=(Button)findViewById(R.id.duanxin_btn_zhidingyi);
	    duanxin_btn_zhidingyi.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(((String)sp_duanxin.getSelectedItem()).equals("自定义")){
				Intent intent =new Intent(HomeSettintActivity.this,HomeContactActivity02.class);
				startActivity(intent);			
				}
				else {
					Toast.makeText(HomeSettintActivity.this, "请先选择自定义模式", 2000).show();
				}
			}
		});
		
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		String dianhua=sharedPreferences.getString("dianhua", "无");
		
		if(dianhua.equals("无")){
			sp_dianhua.setSelection(0, true);
		}
		else if(dianhua.equals("强")){
			sp_dianhua.setSelection(1, true);
		}
		else if(dianhua.equals("弱")){
			sp_dianhua.setSelection(2, true);
		}
		else if(dianhua.equals("关闭")){
			sp_dianhua.setSelection(3, true);
		}
		else if(dianhua.equals("自定义")){
			sp_dianhua.setSelection(4, true);
		}
		
		
String duanxin=sharedPreferences.getString("duanxin", "无");
		
		if(duanxin.equals("无")){
			sp_duanxin.setSelection(0, true);
		}
		else if(duanxin.equals("强")){
			sp_duanxin.setSelection(1, true);
		}
		else if(duanxin.equals("弱")){
			sp_duanxin.setSelection(2, true);
		}
		else if(duanxin.equals("关闭")){
			sp_duanxin.setSelection(3, true);
		}
		else if(duanxin.equals("自定义")){
			sp_duanxin.setSelection(4, true);
		}
	
	
	
	
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
