package win.tang.wnrun.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

/**
 * 启动Activity
 */
public class StartActivityUtils {

    public static void start(Activity activity, Class clazz) {
        start(activity, clazz, null);
    }

    //activity onActivityResult
    public static void start(Activity activity, Class clazz, int requestCode) {
        start(activity, clazz, null, requestCode);
    }

    //fragment onActivityResult
    public static void start(Fragment fragment, Class clazz, int requestCode) {
        start(fragment, clazz, null, requestCode);
    }

    public static void start(Activity activity, Class clazz, Bundle bundle) {
        Intent intent = new Intent(activity, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
    }

    public static void start(Activity activity, Intent intent) {
        activity.startActivity(intent);
    }

    public static void start(Activity activity, Class clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(activity, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivityForResult(intent, requestCode);
    }

    public static void start(Fragment fragment, Class clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(fragment.getActivity(), clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        fragment.startActivityForResult(intent, requestCode);
    }
}
