package com.example.findroommate;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


 public class FirebaseMessagingServices extends FirebaseMessagingService {
    String user_id;
    String other_user_id;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size() > 0) {
            //handle the data message here

            //getting the title and the body
            Log.e("message", remoteMessage.getData().toString());
            String title = remoteMessage.getData().get("Roomify");
            String body = remoteMessage.getData().get("message");
            String type = remoteMessage.getData().get("noti_type");
            user_id = remoteMessage.getData().get("user_id");
            other_user_id = remoteMessage.getData().get("other_user_id");
            Log.e("type", type);
            // String title = "Classy Drive";
            // String body ="Testing";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel Channel = createChannel();
                setNotification(body, type, "my_channel_01", Channel);
            } else {
                setNotification(body, type, "my_channel_01", null);
            }
        }
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("token", s);
    }

    void setNotification(String messageBody, String title, String CHANNEL_ID, NotificationChannel channel) {
        PendingIntent pendingIntent = null;

        Intent intent=new Intent(this,MainActivity.class);

        pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.iconl);
        inboxStyle.addLine(messageBody);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(getNotificationIcon())
                .setLargeIcon(icon)
                .setContentTitle("Roomify")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                .setChannelId(CHANNEL_ID);

        notificationBuilder.setSound(defaultSoundUri);
        notificationBuilder.setPriority(Notification.PRIORITY_MAX);
        notificationBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }
        assert notificationManager != null;
        notificationManager.notify(1, notificationBuilder.build());
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.logo : R.drawable.iconl;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    NotificationChannel createChannel() {
        String id = "my_channel_01";
        CharSequence name = getString(R.string.app_name);
        String description = getString(R.string.app_name);
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel(id, name, importance);
        mChannel.setDescription(description);
        mChannel.setShowBadge(true);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.createNotificationChannel(mChannel);
        return mChannel;
    }
}
