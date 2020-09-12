package com.weesnerdevelopment.playingwithgames

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.weesnerdevelopment.playingwithgames.game.GameLoopType
import com.weesnerdevelopment.playingwithgames.game.GameVariables
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(navController.graph, drawer_layout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        nav_view.setupWithNavController(navController)

        til_add_per_click.editText?.apply {
            setText(GameVariables.itemsPerClick.value.toString())
            addTextChangedListener {
                if (it?.isEmpty() == true || it.toString() == "0")
                    GameVariables.itemsPerClick.value = 100
                else
                    GameVariables.itemsPerClick.value = it.toString().toInt()
            }
        }

        til_colors.editText?.apply {
            setText(GameVariables.pathColorCount.value.toString())
            addTextChangedListener {
                if (it?.isEmpty() == true || it.toString() == "0")
                    GameVariables.pathColorCount.value = 1
                else
                    GameVariables.pathColorCount.value = it.toString().toInt()
            }
        }

        val loopers = resources.getStringArray(R.array.looper_types)

        spinner_looper.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                GameVariables.looper.value =
                    GameLoopType.values().first { it.value == loopers[pos] }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}

        }

        button_clear.setOnClickListener {
            GameVariables.resetGame.value = true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
