package com.weesnerdevelopment.playingwithgames

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.weesnerdevelopment.playingwithgames.game.GameSurfaceView
import com.weesnerdevelopment.playingwithgames.game.GameVariables
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class GameFragment : Fragment() {
    lateinit var gameView: GameSurfaceView
    lateinit var foregroundScope: CoroutineScope

    private var oldColorCount = GameVariables.pathColorCount.value

    override fun onAttach(context: Context) {
        super.onAttach(context)
        create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = gameView

    override fun onStart() {
        super.onStart()
        foregroundScope = MainScope()

        findNavController().addOnDestinationChangedListener { controller, destination, arguments ->
            gameView.destroy()
        }

        foregroundScope.launch {
            GameVariables.pathColorCount.flow.collect {
                if (oldColorCount != it) {
                    oldColorCount = it
                    gameView.clear()
                    gameView.resume()
                }
            }
        }

        foregroundScope.launch {
            GameVariables.resetGame.flow.collect {
                if (it) {
                    gameView.destroy()
                    gameView.resume()
                }
            }
        }

        foregroundScope.launch {
            GameVariables.looper.flow.collect {
                gameView.destroy()
                gameView.loopType = it
                gameView.resume()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        gameView.resume()
    }

    override fun onPause() {
        gameView.pause()
        super.onPause()
    }

    override fun onStop() {
        foregroundScope.cancel()
        super.onStop()
    }

    abstract fun create()
}
