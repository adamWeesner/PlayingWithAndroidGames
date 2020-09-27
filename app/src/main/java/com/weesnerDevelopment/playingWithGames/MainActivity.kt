package com.weesnerDevelopment.playingWithGames

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

        fragments(resources, 1)?.let {
            StateVariables.currentFragment.value = 1
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
