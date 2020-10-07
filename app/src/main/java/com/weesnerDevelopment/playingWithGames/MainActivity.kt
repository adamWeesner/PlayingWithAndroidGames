package com.weesnerDevelopment.playingWithGames

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.weesnerDevelopment.mrNom.MrNomGame
import com.weesnerDevelopment.playingWithGames.game.StateVariables
import com.weesnerDevelopment.playingWithGames.natureOfCode.ballFollowTouch.BallFollowTouchFragment
import com.weesnerDevelopment.playingWithGames.natureOfCode.ballWithForces.BallWithForcesFragment
import com.weesnerDevelopment.playingWithGames.natureOfCode.bouncingBall.BouncingBallFragment
import com.weesnerDevelopment.playingWithGames.natureOfCode.perlinBackground.PerlinBackgroundFragment
import com.weesnerDevelopment.playingWithGames.natureOfCode.randomWalker.WalkerFragment
import com.weesnerDevelopment.playingWithGames.random.bouncingBall.BallBounceFragment
import com.weesnerDevelopment.playingWithGames.random.circlePhysics.CirclePhysicsFragment
import com.weesnerDevelopment.playingWithGames.random.circlePhysics.collision.CircleCollisionPhysicsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lastIndex = resources.getStringArray(R.array.fragments).lastIndex

        fragments(resources, 1)?.let {
            StateVariables.currentFragment.value = 1
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_layout, it, it::class.simpleName)
                .commit()
        }

    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0){
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}
