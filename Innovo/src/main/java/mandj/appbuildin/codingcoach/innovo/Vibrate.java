package mandj.appbuildin.codingcoach.innovo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;


public class Vibrate {

    public static void VibrateOne(final int time, Context context) {
        Log.d("start", String.valueOf(time));

        final Vibrator vib = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        if (time <= 50) {
            Log.d("Vibrating", String.valueOf(time) + "ms");
            if (preferences.getBoolean("example_checkbox", true))
                vib.vibrate(time);
        } else {
            Log.d("Vibrating in new thread", String.valueOf(time) + "ms");
            new Thread() {
                public void run() {
                    if (preferences.getBoolean("example_checkbox", true))
                        vib.vibrate(time);

                }
            }.run();
        }
    }


    public static void VibrateOne(final int[] timeArr, Context context) {

        final Vibrator vib = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        new Thread() {
            public void run() {
                Log.d("Vibrating Arr in new T", String.valueOf(timeArr) + "ms");
                if (preferences.getBoolean("example_checkbox", true)) {
                    boolean task = true;
                    for (int time : timeArr)
                        if (task) {
                            Log.d("T - Vibrate", " ");
                            vib.vibrate(time);
                            task = false;
                        } else {
                            Log.d("T - Sleeping", " ");
                            try {
                                Thread.sleep(time);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            task = true;
                        }
                }
            }
        }.run();
    }

}