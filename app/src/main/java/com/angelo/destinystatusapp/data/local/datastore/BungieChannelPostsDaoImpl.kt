package com.angelo.destinystatusapp.data.local.datastore

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.angelo.destinystatusapp.proto.BungieChannelPosts
import com.angelo.destinystatusapp.proto.BungiePostItem
import com.angelo.destinystatusapp.proto.bungieChannelPosts
import com.angelo.destinystatusapp.proto.bungieHelpPostItems
import kotlinx.coroutines.flow.firstOrNull
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

private const val DATA_STORE_FILE_NAME = "bungie_channel_posts.pb"

private object BungieChannelPostsSerializer : Serializer<BungieChannelPosts> {
    override val defaultValue: BungieChannelPosts = bungieChannelPosts {}

    override suspend fun readFrom(input: InputStream): BungieChannelPosts {
        try {
            return BungieChannelPosts.parseFrom(input)
        } catch (exception: IOException) {
            throw CorruptionException("Failed to read proto.", exception)
        }
    }

    override suspend fun writeTo(t: BungieChannelPosts, output: OutputStream) = t.writeTo(output)
}

private val Context.dataStore: DataStore<BungieChannelPosts> by dataStore(
    fileName = DATA_STORE_FILE_NAME,
    serializer = BungieChannelPostsSerializer
)

class BungieChannelPostsDaoImpl(context: Context) : BungieChannelPostsDao {
    private val dataStore = context.dataStore

    override suspend fun readBungieHelpPostItems(): List<BungiePostItem> =
        dataStore.data.firstOrNull()?.bungieHelpPostItems?.itemsList ?: emptyList()

    override suspend fun saveBungieHelpPostItems(items: List<BungiePostItem>) {
        dataStore.updateData {
            bungieChannelPosts {
                bungieHelpPostItems = bungieHelpPostItems {
                    this.items.clear()
                    this.items += items
                }
            }
        }
    }
}
