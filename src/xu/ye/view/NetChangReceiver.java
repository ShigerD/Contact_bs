package xu.ye.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by T on 2017/4/27.
 */

//创建一个继承BroadcastReceiver的广播监听器；
public class NetChangReceiver extends BroadcastReceiver {

    //重写onReceive方法，该方法的实体为，接收到广播后的执行代码；
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Log.e("myReceiver", "myReceiver");


    }
}
