package xu.ye.view;

import xu.ye.R;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 密码
 * @author Administrator
 *
 */

public class MimaActivity extends Activity{

	private EditText et_mima;
	private Button btn_sumbit;
	private Button btn_cancel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mima);
		setupView();
	}



	private void setupView() {
		// TODO Auto-generated method stub
		et_mima=(EditText)findViewById(R.id.et_mima);
		btn_sumbit=(Button)findViewById(R.id.btn_sumbit);
		btn_sumbit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferences preferences=getSharedPreferences("user",Context.MODE_PRIVATE);
				Editor editor=preferences.edit();
				boolean isMima=false;
				String mima="";
				if(!et_mima.getText().toString().equals("")){
					isMima=true;
					mima=et_mima.getText().toString();
				}
			
				editor.putBoolean("isMima", isMima);
				editor.putString("mima", mima);
				editor.commit();
				
				Toast.makeText(MimaActivity.this, "设置成功", 2000).show();
				MimaActivity.this.finish();
				
			}
		});
		
		btn_cancel=(Button)findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MimaActivity.this.finish();
			}
			
		});
	}



}
