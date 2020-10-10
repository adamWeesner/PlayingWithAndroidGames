package com.weesnerDevelopent.superJumper

import com.weesnerDevelopent.superJumper.screens.ScreenMainMenu
import com.weesnerDevelopment.gameEngine.game.Screen
import com.weesnerDevelopment.openGlGameEngine.GlGame
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class SuperJumper : GlGame() {
    var firstTimeCreate = true

    override var startScreen: Screen = ScreenMainMenu(this)

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        super.onSurfaceCreated(gl, config)
        if (firstTimeCreate) {
            Settings.load(fileIO)
            Assets.load(this)
            firstTimeCreate = false
        } else {
            Assets.reload()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Settings.soundEnabled) Assets.music.pause()
    }
}
