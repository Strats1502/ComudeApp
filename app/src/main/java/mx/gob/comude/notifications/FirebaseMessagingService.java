package mx.gob.comude.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;
import mx.gob.comude.R;
import mx.gob.comude.activities.HomeActivity;
import mx.gob.comude.application.MyApplication;
import mx.gob.comude.model.Notificacion;
import mx.gob.comude.utils.DateUtilities;

/**
 * Created by code on 7/06/16.
 */
public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    Realm realm;
    Notificacion notificacion;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //super.onMessageReceived(remoteMessage);
        Log.d("DEBUG", "Notificacion");
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        showNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("body"), remoteMessage.getData().get("tag"));
    }

    public void showNotification(String title, String message, String enlace) {
        Intent i;
        if (enlace == null || enlace.isEmpty()) {
            i = new Intent(this, HomeActivity.class);
        } else {
            i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(enlace));
        }
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);


        RemoteViews view = new RemoteViews(this.getApplicationContext().getPackageName(), R.layout.notification_layout);
        RemoteViews bigView = new RemoteViews(this.getApplicationContext().getPackageName(), R.layout.notification_big_layout);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Notification notification = builder
                .setAutoCancel(true)
                .setContentTitle(title)

                .setSmallIcon(R.drawable.logo)
                .setContentText(message)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[]{200, 1000, 200, 1000, 200})
                .setContentIntent(pendingIntent)
                .build();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String time = sdf.format(new Date().getTime());

        view.setTextViewText(R.id.notiftitle, title);
        view.setTextViewText(R.id.notiftime, time);
        view.setTextViewText(R.id.notiftext, message);
        bigView.setTextViewText(R.id.notiftitle, title);
        bigView.setTextViewText(R.id.notiftime, time);
        bigView.setTextViewText(R.id.notiftext, message);

        notification.contentView = view;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification.bigContentView = bigView;
        }

        System.err.println(title);
        System.err.println(message);
        System.err.println(Calendar.getInstance().getTime().toString());

        notificacion = new Notificacion(idNotificacion(), title, message, DateUtilities.dateToString(Calendar.getInstance().getTime()));

        realm.beginTransaction();
        realm.copyToRealm(notificacion);
        realm.commitTransaction();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        manager.notify(0, notification);
    }

    private int idNotificacion() {
        try {
            return realm.where(Notificacion.class).max("idNotificacion").intValue() + 1;
        } catch (NullPointerException errorLlavePrimaria) {
            return 0;
        }
    }
}
