package luyao.box.ui

import android.Manifest
import android.content.pm.PackageManager
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.title_layout.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import luyao.box.APK_PATH
import luyao.box.BASE_PATH
import luyao.box.R
import luyao.box.common.base.BaseActivity
import luyao.box.ui.activity.CurrentActivity
import luyao.box.ui.appManager.AppListActivity
import java.io.File


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE


    override fun getLayoutResId() = R.layout.activity_main

    override fun initView() {
        setSupportActionBar(mToolbar)

        fab.setOnClickListener {
            startActivity(AppListActivity::class.java)
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, mToolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        currentActivity.setOnClickListener { startActivity(CurrentActivity::class.java) }

        checkPermissions()
    }

    override fun initData() {
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            createBaseFile()
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            MaterialDialog(this).show {
                title(R.string.permission_get)
                message(R.string.permission_note)
                positiveButton { ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), 1001) }
            }
        } else createBaseFile()
    }

    private fun createBaseFile() {
        CoroutineScope(Dispatchers.IO).launch {
            val baseFolder = File(BASE_PATH)
            if (!baseFolder.exists()) baseFolder.mkdirs()

            val apkFolder = File(APK_PATH)
            if (!apkFolder.exists()) apkFolder.mkdirs()
        }
    }
}
