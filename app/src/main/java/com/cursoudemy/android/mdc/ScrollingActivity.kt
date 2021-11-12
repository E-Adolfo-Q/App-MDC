package com.cursoudemy.android.mdc

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.URLUtil
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.cursoudemy.android.mdc.databinding.ActivityScrollingBinding
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


class ScrollingActivity : AppCompatActivity() {

    //Variable global
    private lateinit var binding: ActivityScrollingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_scrolling)
        //Inicializado el objecto binding
        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*findViewById<FloatingActionButton>(R.id.fab).setOnClickListener{
          if(findViewById<BottomAppBar>(R.id.button_app_bar).fabAlignmentMode == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER){
              findViewById<BottomAppBar>(R.id.button_app_bar).fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
          }else{
              findViewById<BottomAppBar>(R.id.button_app_bar).fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
          }
        }*/

        binding.fab.setOnClickListener{
            if(binding.buttonAppBar.fabAlignmentMode == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER){
                binding.buttonAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            }else{
                binding.buttonAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            }
        }

        binding.buttonAppBar.setNavigationOnClickListener({
            Snackbar.make(binding.root, R.string.message_action_success,Snackbar.LENGTH_LONG).setAnchorView(binding.fab).show()
        })

        binding.content.btnSkip.setOnClickListener { binding.content.cvAd?.visibility = View.GONE }

        binding.content.btnBuy.setOnClickListener {
            Snackbar.make(it, R.string.card_buying, Snackbar.LENGTH_LONG)
                .setAnchorView(binding.fab)
                .setAction(R.string.card_to_go,{
                    Toast.makeText(this,R.string.card_historial,Toast.LENGTH_SHORT).show()
                })
                .show()
        }

        //.into(binding.content.imgCover)


        loadImage()

        /*
        * Glide.with(this)
        * .load("https://i.ytimg.com/vi/uNhAHzUpsXQ/hqdefault.jpg")
        * .diskCacheStrategy(DiskCacheStrategy.ALL)
        * .centerCrop()
        * .into(binding.content.imgCover)
        *
        * */

        binding.content.cbEnablePass.setOnClickListener {
            //binding.content.tilPassword?.isEnabled = !binding.content.tilPassword?.isEnabled
            binding.content.tilPassword.isEnabled = !binding.content.tilPassword.isEnabled
        }

        //Actualmente tenemos un listener en el Textfield dedicado a recibir una URL
        binding.content.etUrl.onFocusChangeListener = View.OnFocusChangeListener { view, focused ->
            //variable errorStr si puede ser null
            var errorStr: String? = null
            val url = binding.content.etUrl.text.toString()

            if(!focused){
               if(url.isEmpty()){
                   errorStr = getString(R.string.card_required)
               } else if(URLUtil.isValidUrl(url)){
                   loadImage(url)
               }else{
                   errorStr = getString(R.string.card_invalid_url)
               }
            }

            binding.content.tilUrl.error = errorStr
        }

        binding.content.toggleButton?.addOnButtonCheckedListener { group, checkedId, isChecked ->
            binding.content.root.setBackgroundColor(
                when(checkedId){
                    R.id.btnRed -> Color.RED
                    R.id.btnBlue -> Color.BLUE
                    else -> Color.GREEN
                }
            )
        }
    }

    private fun loadImage(url: String = "https://i.blogs.es/e39c62/android-kotlin-curso-gratis/1366_2000.jpg"){
            Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.content.imgCover)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}