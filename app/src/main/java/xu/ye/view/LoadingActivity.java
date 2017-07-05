package xu.ye.view;

import xu.ye.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class LoadingActivity extends Activity{
  
	private final String TAG="LoadingActivity";
	private ImageView iv_adimg;
	 			 	  
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		
		setupView();
	}



	
	private void setupView() {
		
		// TODO Auto-generated method stub
		iv_adimg= (ImageView) findViewById(R.id.iv_adimg);   
		  
        AlphaAnimation alphaAnimation = new AlphaAnimation((float) 0.1, 1);   
        alphaAnimation.setDuration(2000);//设定动画时间   
        alphaAnimation.setAnimationListener(new AnimationListener() {   
            @Override  
            public void onAnimationStart(Animation animation) {   
            }   
  
            @Override  
            public void onAnimationRepeat(Animation animation) {   
            }   
  
            @Override  
            public void onAnimationEnd(Animation animation) {   
//            	 Intent intent=new Intent();
//         		intent.setClass(LoadingActivity.this, HomeTabHostAcitivity.class);
//         		startActivity(intent);
//	        	  LoadingActivity.this.finish();
	        	  
            	SharedPreferences preferences=getSharedPreferences("user", Context.MODE_PRIVATE);
            	boolean isMima=preferences.getBoolean("isMima", true);
            	final String mima=preferences.getString("mima", "");
            	if(isMima)
            	{
            		final EditText et=new EditText(LoadingActivity.this);
            		et.setHint("初始密码为空");
            		 new AlertDialog.Builder(LoadingActivity.this).setTitle("请输入密码").setIcon(
    					     android.R.drawable.ic_dialog_info).setView(
    					    		 et).setPositiveButton("确定",  new DialogInterface.OnClickListener() {
    					    				 @Override
    					    				 public void onClick(DialogInterface dialog, int which) {
    					    			          String mima1=et.getText().toString();
    					    			          if(mima.equals(mima1))   					    			        	  
    					    			          {
    					    			        	  
    					    			        	  Toast.makeText(LoadingActivity.this,"密码正确！" , 2000).show();
    					    			        	  Intent intent=new Intent();
    					    		            		intent.setClass(LoadingActivity.this, HomeTabHostAcitivity.class);
    					    		            		startActivity(intent);
    					    			        	  LoadingActivity.this.finish();
    					    			          }
    					    			          else
    					    			          {
    					    			        	 
    					    			        	  Toast.makeText(LoadingActivity.this,"密码错误！" , 2000).show();
    					    			        	  LoadingActivity.this.finish();
    					    			       
    					    			          }
                                                  
    					    					 
    					    			   }
    					    			  })
    					     .setNegativeButton("取消", new DialogInterface.OnClickListener() {
			    				 @Override
			    				 public void onClick(DialogInterface dialog, int which) {
			    					 LoadingActivity.this.finish();
			    				 }
			    				 }
			    					 ).show();
			    				 }
    			
            	else{
            		Intent intent=new Intent();
            		intent.setClass(LoadingActivity.this, HomeTabHostAcitivity.class);
            		startActivity(intent);

            		LoadingActivity.this.finish();
            	}
            	
            	
            }
            });   
       
        iv_adimg.setAnimation(alphaAnimation);   
        iv_adimg.setVisibility(View.VISIBLE);   
	}




	@Override
   	public boolean onKeyDown(int keyCode, KeyEvent event) {
   		// TODO Auto-generated method stub
   		if(keyCode==KeyEvent.KEYCODE_BACK)
   		 {
   		  this.finish();
   			return true;
   		 }
   		return false;
   	}	

}
