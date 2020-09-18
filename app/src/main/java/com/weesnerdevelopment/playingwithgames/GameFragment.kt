package com.weesnerdevelopment.playingwithgames

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.weesnerdevelopment.playingwithgames.game.GameSurfaceView
import com.weesnerdevelopment.playingwithgames.game.GameVariables
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

abstract class GameFragment : Fragment() {
    lateinit var gameView: GameSurfaceView
    private var foregroundScope: CoroutineScope? = null

    private var oldColorCount = GameVariables.pathColorCount.value

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_game, container, false)

    override fun onStart() {
        super.onStart()

        create()

        findNavController().addOnDestinationChangedListener { _, _, _ ->
            gameView.destroy()
        }
    }

    override fun onResume() {
        super.onResume()
        foregroundScope = MainScope()

        layout_game.removeAllViews()
        layout_game.addView(gameView)

        gameView.resume()

        foregroundScope?.launch {
            GameVariables.pathColorCount.flow.collect {
                if (oldColorCount != it) {
                    oldColorCount = it
                    gameView.clear()
                    gameView.resume()
                }
            }
        }

        foregroundScope?.launch {
            GameVariables.resetGame.flow.collect {
                if (it) {
                    gameView.destroy()
                    gameView.resume()
                }
            }
        }

        foregroundScope?.launch {
            GameVariables.looper.flow.collect {
                gameView.destroy()
                gameView.loopType = it
                gameView.resume()
            }
        }
    }

    override fun onPause() {
        gameView.pause()
        foregroundScope?.cancel()
        foregroundScope = null
        super.onPause()
    }

    abstract fun create()
}
