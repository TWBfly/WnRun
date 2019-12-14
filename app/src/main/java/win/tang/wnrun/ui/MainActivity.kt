package win.tang.wnrun.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import win.tang.wnrun.R
import win.tang.wnrun.util.StartActivityUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tencent.setOnClickListener {
            StartActivityUtils.start(this, TencentActivity::class.java)
        }
    }
}
