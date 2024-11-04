import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.angelo.destinystatusapp.data.local.datastore.BungieChannelPostsDaoImpl
import com.angelo.destinystatusapp.data.model.BungieChannelType
import com.angelo.destinystatusapp.proto.BungieChannelPosts
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BungieChannelPostsDaoImplTest {
    private lateinit var dao: BungieChannelPostsDaoImpl
    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setUp() {
        dao = BungieChannelPostsDaoImpl(context)
    }

    @Test
    fun testReadPosts_noDefaultValue_returnsDefaultInstance() = runBlocking {
        val result = dao.readPosts(BungieChannelType.BungieHelp)

        assertEquals(BungieChannelPosts.getDefaultInstance(), result)
    }

    @Test
    fun testSavePosts_readPosts() = runBlocking {
        val testChannelType = BungieChannelType.Destiny2Team
        val testPosts = BungieChannelPosts.newBuilder().setWriteTimestampMillis(1000L).build()

        dao.savePosts(testChannelType, testPosts)
        val result = dao.readPosts(testChannelType)

        assertEquals(testPosts.writeTimestampMillis, result.writeTimestampMillis)
    }
}
