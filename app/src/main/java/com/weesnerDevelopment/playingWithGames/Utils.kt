package com.weesnerDevelopment.playingWithGames

import android.content.res.Resources
import androidx.fragment.app.Fragment
import com.weesnerDevelopment.mrNom.MrNomGame
import com.weesnerDevelopment.natureOfCode.ballFollowTouch.BallFollowTouchFragment
import com.weesnerDevelopment.natureOfCode.ballWithForces.BallWithForcesFragment
import com.weesnerDevelopment.natureOfCode.bouncingBall.BouncingBallFragment
import com.weesnerDevelopment.natureOfCode.perlinBackground.PerlinBackgroundFragment
import com.weesnerDevelopment.natureOfCode.randomWalker.WalkerFragment
import com.weesnerDevelopment.openGlGameEngine.samples.*
import com.weesnerDevelopment.randoms.bouncingBall.BallBounceFragment
import com.weesnerDevelopment.randoms.circlePhysics.CirclePhysicsFragment
import com.weesnerDevelopment.randoms.circlePhysics.collision.CircleCollisionPhysicsFragment

fun fragments(resources: Resources, selection: Int): Fragment? {
    val fragments = resources.getStringArray(R.array.fragments)

    return when (fragments[selection]) {
        // random
        resources.getString(R.string.bouncing_balls) -> BallBounceFragment()
        resources.getString(R.string.gravity_circles) -> CirclePhysicsFragment()
        resources.getString(R.string.gravity_collision_circles) -> CircleCollisionPhysicsFragment()
        // nature of code
        resources.getString(R.string.random_walker) -> WalkerFragment()
        resources.getString(R.string.perlin_background) -> PerlinBackgroundFragment()
        resources.getString(R.string.bouncing_ball) -> BouncingBallFragment()
        resources.getString(R.string.ball_follow_touch) -> BallFollowTouchFragment()
        resources.getString(R.string.ball_with_forces) -> BallWithForcesFragment()
        // mr nom
        getString(R.string.mr_nom) -> MrNomGame()
        // open gl samples
        getString(R.string.gl_game_sample) -> GlGameSample()
        getString(R.string.first_triangle_sample) -> TriangleSample()
        getString(R.string.colored_triangle_sample) -> ColoredTriangleSample()
        getString(R.string.textured_triangle_sample) -> TexturedTriangleSample()
        getString(R.string.indexed_triangle_sample) -> IndexedSample()
        getString(R.string.blending_sample) -> BlendingSample()
        getString(R.string.bobs_sample) -> BobsSample()
        getString(R.string.cannon_sample) -> CannonSample()
        getString(R.string.cannon_gravity_sample) -> CannonGravitySample()
        getString(R.string.cannon_gravity_2_sample) -> CannonGravity2Sample()
        getString(R.string.cannon_zoom_sample) -> Camera2DSample()
        getString(R.string.cannon_texture_sample) -> TextureAtlasSample()
        getString(R.string.cannon_texture_batch_sample) -> TextureAtlasBatchSample()
        getString(R.string.animation_sample) -> CavemanSample()
        // super jumper
        else -> null
    }
}