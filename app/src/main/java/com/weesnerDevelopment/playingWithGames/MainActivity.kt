package com.weesnerDevelopment.playingWithGames

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.weesnerDevelopment.randomGameEngine.game.StateVariables
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            StateVariables.currentFragment.flow.collect {
                StateVariables.currentGameView.value?.stop()

                fragments(resources, it)?.let {
                    StateVariables.currentGameView.value?.stop()
                    supportFragmentManager.apply {
                        beginTransaction()
                            .replace(
                                R.id.main_layout,
                                findFragmentByTag(it::class.simpleName) ?: it,
                                it.tag
                            )
                            .addToBackStack(null)
                            .commit()
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}
