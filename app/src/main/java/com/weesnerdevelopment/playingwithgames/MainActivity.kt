package com.weesnerdevelopment.playingwithgames

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.weesnerdevelopment.playingwithgames.game.StateVariables
import com.weesnerdevelopment.playingwithgames.natureOfCode.ballFollowTouch.BallFollowTouchFragment
import com.weesnerdevelopment.playingwithgames.natureOfCode.ballWithForces.BallWithForcesFragment
import com.weesnerdevelopment.playingwithgames.natureOfCode.bouncingBall.BouncingBallFragment
import com.weesnerdevelopment.playingwithgames.natureOfCode.perlinBackground.PerlinBackgroundFragment
import com.weesnerdevelopment.playingwithgames.natureOfCode.randomWalker.WalkerFragment
import com.weesnerdevelopment.playingwithgames.random.bouncingBall.BallBounceFragment
import com.weesnerdevelopment.playingwithgames.random.circlePhysics.CirclePhysicsFragment
import com.weesnerdevelopment.playingwithgames.random.circlePhysics.collision.CircleCollisionPhysicsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listSize = resources.getStringArray(R.array.fragments).size - 1
        fragments(resources, listSize)?.let {
            StateVariables.currentFragment.value = listSize
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_layout, it, it::class.simpleName)
                .commit()
        }

    }
}

fun Context.fragments(resources: Resources, selection: Int): GameFragment? {
    val fragments = resources.getStringArray(R.array.fragments)

    return when (fragments[selection]) {
        // random
        getString(R.string.bouncing_balls) -> BallBounceFragment()
        getString(R.string.gravity_circles) -> CirclePhysicsFragment()
        getString(R.string.gravity_collision_circles) -> CircleCollisionPhysicsFragment()
        // nature of code
        getString(R.string.random_walker) -> WalkerFragment()
        getString(R.string.perlin_background) -> PerlinBackgroundFragment()
        getString(R.string.bouncing_ball) -> BouncingBallFragment()
        getString(R.string.ball_follow_touch) -> BallFollowTouchFragment()
        getString(R.string.ball_with_forces) -> BallWithForcesFragment()
        else -> null
    }
}
