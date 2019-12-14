package win.tang.wnrun.ui

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_tencent.*
import win.tang.wnrun.R
import win.tang.wnrun.util.Constains
import win.tang.wnrun.weight.CommonWebView

/**
 * Created by Tang on 2019/12/14
 */
class ParseActivity:AppCompatActivity() {
    private lateinit var commonWebView: CommonWebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tencent)

        val wnUrl = intent.getStringExtra(Constains.WN_URL)

        commonWebView = CommonWebView(this, parsing)
        commonWebView.initWebView(frame_layout,wnUrl)

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (commonWebView.keyDown(keyCode)){
            true
        }else{
            super.onKeyDown(keyCode, event)
        }
    }

    override fun onDestroy() {
        commonWebView.DestoryWebView()
        super.onDestroy()
    }
}