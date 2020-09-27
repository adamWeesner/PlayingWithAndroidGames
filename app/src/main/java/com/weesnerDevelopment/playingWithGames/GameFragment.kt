package com.weesnerDevelopment.playingWithGames

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.from
import com.weesnerDevelopment.mrNom.MrNomGame
import com.weesnerDevelopment.playingWithGames.game.GameLoopType
import com.weesnerDevelopment.playingWithGames.game.GameSurfaceView
import com.weesnerDevelopment.playingWithGames.game.GameVariables
import com.weesnerDevelopment.playingWithGames.game.StateVariables
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class GameFragment : Fragment() {
    lateinit var gameView: GameSurfaceView
    lateinit var gameVariables: List<GameVariableItem<*>>

    private var foregroundScope: CoroutineScope? = null
    private var oldColorCount = GameVariables.pathColorCount.value

    override fun onAttach(context: Context) {
        super.onAttach(context)

        gameVariables = listOf(
            GameVariableItem(
                requireContext(),
                GameVariables.looper,
                resources.getStringArray(R.array.looper_types)
            ) { variable, looper ->
                val newLooper = GameLoopType.values().first { it.value == looper }

                if (variable.value != newLooper) variable.value = newLooper
            },
            GameVariableItem(
                requireContext(),
                GameVariables.fps,
                "Max FPS"
            ) { variable, text ->
                variable.value = if (text.isEmpty() || text == "0") 60 else text.toInt()
            },
            GameVariableItem(
                requireContext(),
                GameVariables.itemsPerClick,
                "Add on Tap"
            ) { variable, text ->
                variable.value = if (text.isEmpty() || text == "0") 1 else text.toInt()
            },
            GameVariableItem(
                requireContext(),
                GameVariables.pathColorCount,
                "Colors"
            ) { variable, text ->
                variable.value = if (text.isEmpty() || text == "0") 1 else text.toInt()
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_game, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        create()

        layout_game_variables.removeAllViews()
        gameVariables.forEach { layout_game_variables.addView(it) }

        spinner_fragments.setSelection(StateVariables.currentFragment.value)
        spinner_fragments.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                if (StateVariables.currentFragment.value == pos) return

                if (resources.getStringArray(R.array.fragments)[pos] == getString(R.string.mr_nom))
                    startActivity(Intent(requireContext(), MrNomGame::class.java))

                val selectedFragment = requireContext().fragments(resources, pos)
                StateVariables.currentFragment.value = pos
                gameView.stop()

                if (selectedFragment != null) {
                    gameView.stop()
                    requireActivity().supportFragmentManager.apply {
                        beginTransaction().replace(
                            R.id.main_layout,
                            findFragmentByTag(selectedFragment::class.simpleName)
                                ?: selectedFragment,
                            selectedFragment.tag
                        ).commit()
                    }
                }
            }
        }

        button_clear.setOnClickListener {
            StateVariables.resetGame.value = true
        }

        layout_game.removeAllViews()
        layout_game.addView(gameView)
    }

    override fun onStart() {
        super.onStart()
        foregroundScope = MainScope()

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
            GameVariables.fps.flow.collect {
                gameView.clear()
                gameView.resume()
            }
        }

        foregroundScope?.launch {
            StateVariables.resetGame.flow.collect {
                if (it) {
                    gameView.stop()
                    gameView.resume()
                }
            }
        }

        foregroundScope?.launch {
            GameVariables.looper.flow.collect {
                gameView.clear()
                gameView.loopType = it
                gameView.resume()
            }
        }

        from(bottom_sheet).apply {
            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {}

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    if (slideOffset == 0f) gameView.resume()
                    else gameView.stop()
                }
            })
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
        gameView.stop()
        foregroundScope?.cancel()
        foregroundScope = null
        super.onStop()
    }

    abstract fun create()
}
