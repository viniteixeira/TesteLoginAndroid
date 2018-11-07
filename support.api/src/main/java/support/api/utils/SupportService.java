package support.api.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Material para dica...
 * http://www.vogella.com/tutorials/AndroidServices/article.html
 */
public class SupportService extends Service {

    private static final ArrayList<Object> mArrObjects = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();

        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        // InexactRepeating allows Android to optimize the energy consumption
        for (Object object : mArrObjects) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                    object.timeTrigger, object.timeInterval, object.pending);
        }

        // service.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
        // REPEAT_TIME, pending);
    }

    public static void addObject(Object... objects) {
        mArrObjects.clear();
        for (Object object : objects) {
            mArrObjects.add(object);
        }
    }

    public static void addObject(ArrayList<Object> arrObjects) {
        mArrObjects.clear();
        mArrObjects.addAll(arrObjects);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static class Object {
        public final PendingIntent pending;
        public final long timeTrigger;
        public final long timeInterval;

        public Object(Context context, int requestCode, Class classe, int secondTrigger, int secondInterval) {
            Calendar calendar;
            Intent intent = new Intent(context, classe);

            calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, secondTrigger);

            this.timeTrigger = calendar.getTimeInMillis();
            this.timeInterval = secondInterval * 1000;
            this.pending = PendingIntent.getBroadcast(context, requestCode, intent,
                    PendingIntent.FLAG_CANCEL_CURRENT);
        }
    }
}
