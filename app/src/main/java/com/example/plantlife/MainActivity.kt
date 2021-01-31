package com.example.plantlife

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class MainActivity : AppCompatActivity(), KodeinAware, SensorEventListener {
    override val kodein by kodein()
    lateinit var toggle: ActionBarDrawerToggle
    private val lightSensorFragment = LightSensorFragment()
    private val homeFragment = HomeFragment()
    private val creditsFragment = CreditsFragment()
    private val manager = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_PlantLife)
        setContentView(R.layout.activity_main)
        manager.beginTransaction().apply {
            replace(R.id.fragmentHolder, homeFragment)
            commit()
        }
        toggle = ActionBarDrawerToggle(this, drawer_layout, R.string.open, R.string.close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        nav_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> showHomeFragment()
                R.id.nav_sunlight -> showLightSensorFragment()
                R.id.nav_share -> shareButton()
                R.id.nav_credits -> showCreditsFragment()
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        TODO("Not yet implemented")
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("Not yet implemented")
    }

    private fun showLightSensorFragment() {
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragmentHolder, lightSensorFragment)
        transaction.addToBackStack(null)
        transaction.commit()
        drawer_layout.closeDrawers()
    }

    private fun showHomeFragment() {
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragmentHolder, homeFragment)
        transaction.addToBackStack(null)
        transaction.commit()
        drawer_layout.closeDrawers()
    }

    private fun shareButton() {
        val shareText = "Download PlantLife from Play Store via this link: "
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText)
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Share PlantLife")
        startActivity(Intent.createChooser(shareIntent, "Share PlantLife via: "))
    }

    private fun showCreditsFragment() {
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragmentHolder, creditsFragment)
        transaction.addToBackStack(null)
        transaction.commit()
        drawer_layout.closeDrawers()
    }
}