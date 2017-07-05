package xu.ye.uitl;

import xu.ye.R;
import xu.ye.view.HomeContactActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class NotifyUtil {

public static void notify(Context context,String mtickerText,String mcontentTitle,String mcontentText){
	//定义NotificationManager

    String ns = Context.NOTIFICATION_SERVICE;

    NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);

    //定义通知栏展现的内容信息

    int icon = R.drawable.ic_launcher;

    CharSequence tickerText = mtickerText;

    long when = System.currentTimeMillis();

    Notification notification = new Notification(icon, tickerText, when);

  

    //定义下拉通知栏时要展现的内容信息

    Context mcontext = context.getApplicationContext();

    CharSequence contentTitle =mcontentTitle;

    CharSequence contentText =mcontentText ;

    Intent notificationIntent = new Intent(context, HomeContactActivity.class);

    PendingIntent contentIntent = PendingIntent.getActivity(context, 0,notificationIntent, 0);

    //notification.setLatestEventInfo(mcontext, contentTitle, contentText,contentIntent);

    //用mNotificationManager的notify方法通知用户生成标题栏消息通知

    mNotificationManager.notify(1, notification);
}
}
