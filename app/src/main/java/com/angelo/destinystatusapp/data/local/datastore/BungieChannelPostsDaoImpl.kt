package com.angelo.destinystatusapp.data.local.datastore

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.angelo.destinystatusapp.data.model.BungieChannelType
import com.angelo.destinystatusapp.proto.AllBungieChannelPosts
import com.angelo.destinystatusapp.proto.BungieChannelPosts
import com.angelo.destinystatusapp.proto.allBungieChannelPosts
import com.angelo.destinystatusapp.proto.bungieChannelPosts
import kotlinx.coroutines.flow.firstOrNull
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

private const val DATA_STORE_FILE_NAME = "bungie_channel_posts.pb"

private object AllBungieChannelPostsSerializer : Serializer<AllBungieChannelPosts> {
    override val defaultValue: AllBungieChannelPosts = allBungieChannelPosts {}

    override suspend fun readFrom(input: InputStream): AllBungieChannelPosts {
        try {
            return AllBungieChannelPosts.parseFrom(input)
        } catch (exception: IOException) {
            throw CorruptionException("Failed to read proto.", exception)
        }
    }

    override suspend fun writeTo(t: AllBungieChannelPosts, output: OutputStream) = t.writeTo(output)
}

private val Context.dataStore by dataStore(
    fileName = DATA_STORE_FILE_NAME,
    serializer = AllBungieChannelPostsSerializer
)

class BungieChannelPostsDaoImpl(context: Context) : BungieChannelPostsDao {
    private val dataStore = context.dataStore

    override suspend fun readPosts(channelType: BungieChannelType): BungieChannelPosts {
        return dataStore.data.firstOrNull()?.allBungieChannelPostsMap?.get(channelType.name) ?: bungieChannelPosts {}
    }

    override suspend fun savePosts(channelType: BungieChannelType, channelPosts: BungieChannelPosts) {
        dataStore.updateData {
            it.toBuilder().putAllBungieChannelPosts(channelType.name, channelPosts).build()
        }
    }
}
