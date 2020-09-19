package com.weesnerdevelopment.playingwithgames

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputLayout
import com.weesnerdevelopment.playingwithgames.game.GameVariable


class GameVariableItem<T : Any> : ConstraintLayout {
    private val textInputLayout: TextInputLayout by lazy { findViewById<TextInputLayout>(R.id.text_input_layout_game_variable) }
    private val spinner: Spinner by lazy { findViewById<Spinner>(R.id.spinner_game_variable) }

    lateinit var gameVariable: GameVariable<T>

    constructor(
        c: Context,
        gameVariable: GameVariable<T>,
        hint: String,
        change: (variable: GameVariable<T>, text: String) -> Unit
    ) : super(c) {
        this.gameVariable = gameVariable
        setupLayout(c)
        showTextInputLayout(hint, change)
    }

    constructor(
        c: Context,
        gameVariable: GameVariable<T>,
        entries: Array<String>,
        selected: (variable: GameVariable<T>, selected: String) -> Unit
    ) : super(c) {
        this.gameVariable = gameVariable
        setupLayout(c)
        showSpinner(entries, selected)
    }

    constructor(c: Context) : super(c) {
        setupLayout(c)
    }

    constructor(c: Context, attr: AttributeSet?) : super(c, attr) {
        setupLayout(c)
    }

    // d is defaultAttributeSet
    constructor(c: Context, attr: AttributeSet, defAttr: Int) : super(c, attr, defAttr) {
        setupLayout(c)
    }

    private fun setupLayout(context: Context) {
        inflate(context, R.layout.item_game_variable, this)
    }

    private fun showSpinner(
        entries: Array<String>,
        selected: (variable: GameVariable<T>, selected: String) -> Unit
    ) {
        textInputLayout.isVisible = false

        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, entries)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.apply {
            isVisible = true
            setAdapter(adapter)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {}
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                    selected(gameVariable, entries[pos])
                }
            }
        }
    }

    private fun showTextInputLayout(
        hint: String,
        change: (variable: GameVariable<T>, text: String) -> Unit
    ) {
        spinner.isVisible = false

        textInputLayout.isVisible = true
        textInputLayout.hint = hint
        if (gameVariable.value is Number)
            textInputLayout.editText?.inputType = InputType.TYPE_CLASS_NUMBER
        textInputLayout.editText?.setText(gameVariable.value.toString())
        textInputLayout.editText?.addTextChangedListener { change(gameVariable, it.toString()) }
    }
}
