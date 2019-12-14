package win.tang.wnrun

import android.app.Application
import com.tencent.bugly.Bugly

/**
 * Created by Tang on 2019/12/14
 */
class MyApplication : Application() {

    companion object {
        lateinit var context: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        Bugly.init(applicationContext, "fd33c032ee", BuildConfig.DEBUG)
    }
}