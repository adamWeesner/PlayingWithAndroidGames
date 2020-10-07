import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import com.weesnerDevelopment.gameEngine.game.Screen
import com.weesnerDevelopment.gameEngine.math.UV
import com.weesnerDevelopment.gameEngine.math.Vector
import com.weesnerDevelopment.gameEngine.util.Size
import com.weesnerDevelopment.openGlGameEngine.GlGame
import com.weesnerDevelopment.openGlGameEngine.Texture
import com.weesnerDevelopment.openGlGameEngine.TextureRegion
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class TestTextures {
    lateinit var game: GlGame
    lateinit var scenario: FragmentScenario<GlGame>

    @Before
    fun setup() {
        game = TestGame()
        scenario = launchFragmentInContainer { game }.apply {
            moveToState(Lifecycle.State.RESUMED)
        }
    }

    @Test
    fun canLoadTextureSize() = runBlocking {
        delay(1)
        val texture = Texture(game, "atlas.png")
        assert(texture.size == Size(64))
    }

    @Test
    fun canLoadCannonRegionFromTexture() = runBlocking {
        delay(1)
        val texture = Texture(game, "atlas.png")
        val textureRegion = TextureRegion(texture, Vector.zero, Size(64, 32))

        assertEquals(UV(0f, 0f), textureRegion.textureStart)
        assertEquals(UV(1f, .5f), textureRegion.textureEnd)
    }

    @Test
    fun canLoadBallRegionFromTexture() = runBlocking {
        delay(1)
        val texture = Texture(game, "atlas.png")
        val textureRegion = TextureRegion(texture, Vector(0, 32), Size(16))

        assertEquals(UV(0f, .5f), textureRegion.textureStart)
        assertEquals(UV(.25f, .75f), textureRegion.textureEnd)
    }

    @Test
    fun canLoadBobRegionFromTexture() = runBlocking {
        delay(1)
        val texture = Texture(game, "atlas.png")
        val textureRegion = TextureRegion(texture, Vector(32), Size(32))

        assertEquals(Vector(32, 32), textureRegion.start)
        assertEquals(UV(.5f, .5f), textureRegion.textureStart)
        assertEquals(UV(1f, 1f), textureRegion.textureEnd)
    }
}

class TestGame : GlGame() {
    override var startScreen: Screen = TestScreen(this)
}

private class TestScreen(game: GlGame) : Screen(game)