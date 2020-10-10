package com.weesnerDevelopment.playingWithGames

import android.content.res.Resources
import androidx.fragment.app.Fragment
import com.weesnerDevelopent.superJumper.SuperJumper
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
        resources.getString(R.string.mr_nom) -> MrNomGame()
        // open gl samples
        resources.getString(R.string.gl_game_sample) -> GlGameSample()
        resources.getString(R.string.first_triangle_sample) -> TriangleSample()
        resources.getString(R.string.colored_triangle_sample) -> ColoredTriangleSample()
        resources.getString(R.string.textured_triangle_sample) -> TexturedTriangleSample()
        resources.getString(R.string.indexed_triangle_sample) -> IndexedSample()
        resources.getString(R.string.blending_sample) -> BlendingSample()
        resources.getString(R.string.bobs_sample) -> BobsSample()
        resources.getString(R.string.cannon_sample) -> CannonSample()
        resources.getString(R.string.cannon_gravity_sample) -> CannonGravitySample()
        resources.getString(R.string.cannon_gravity_2_sample) -> CannonGravity2Sample()
        resources.getString(R.string.cannon_zoom_sample) -> Camera2DSample()
        resources.getString(R.string.cannon_texture_sample) -> TextureAtlasSample()
        resources.getString(R.string.cannon_texture_batch_sample) -> TextureAtlasBatchSample()
        resources.getString(R.string.animation_sample) -> CavemanSample()
        // super jumper
        resources.getString(R.string.super_jumper) -> SuperJumper()
        else -> null
    }
}
