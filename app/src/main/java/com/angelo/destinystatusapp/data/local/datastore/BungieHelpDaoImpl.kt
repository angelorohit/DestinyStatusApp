package com.angelo.destinystatusapp.data.local.datastore

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.angelo.destinystatusapp.proto.BungiePostItems
import com.angelo.destinystatusapp.proto.bungiePostItems
import kotlinx.coroutines.flow.firstOrNull
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

private const val DATA_STORE_FILE_NAME = "bungie_help_post.pb"

private object BungieHelpPostItemsSerializer : Serializer<BungiePostItems> {
    override val defaultValue: BungiePostItems = bungiePostItems {}

    override suspend fun readFrom(input: InputStream): BungiePostItems {
        try {
            return BungiePostItems.parseFrom(input)
        } catch (exception: IOException) {
            throw CorruptionException("Failed to read proto.", exception)
        }
    }

    override suspend fun writeTo(t: BungiePostItems, output: OutputStream) = t.writeTo(output)
}

private val Context.dataStore: DataStore<BungiePostItems> by dataStore(
    fileName = DATA_STORE_FILE_NAME,
    serializer = BungieHelpPostItemsSerializer
)

class BungieHelpDaoImpl(context: Context) : BungieHelpDao {
    private val dataStore = context.dataStore

    override suspend fun readBungieHelpPostItems(): BungiePostItems =
        dataStore.data.firstOrNull() ?: BungiePostItems.getDefaultInstance()

    override suspend fun saveBungieHelpPostItems(items: BungiePostItems) {
        dataStore.updateData { items }
    }
}
