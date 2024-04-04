package com.angelo.destinystatusapp.data.local.datastore

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.angelo.destinystatusapp.proto.BungieHelpPostItems
import com.angelo.destinystatusapp.proto.bungieHelpPostItems
import kotlinx.coroutines.flow.firstOrNull
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

private const val DATA_STORE_FILE_NAME = "bungie_help_post.pb"

private object BungieHelpPostItemsSerializer : Serializer<BungieHelpPostItems> {
    override val defaultValue: BungieHelpPostItems = bungieHelpPostItems {}

    override suspend fun readFrom(input: InputStream): BungieHelpPostItems {
        try {
            return BungieHelpPostItems.parseFrom(input)
        } catch (exception: IOException) {
            throw CorruptionException("Failed to read proto.", exception)
        }
    }

    override suspend fun writeTo(t: BungieHelpPostItems, output: OutputStream) = t.writeTo(output)
}

private val Context.dataStore: DataStore<BungieHelpPostItems> by dataStore(
    fileName = DATA_STORE_FILE_NAME,
    serializer = BungieHelpPostItemsSerializer
)

class BungieHelpDaoImpl(context: Context) : BungieHelpDao {
    private val dataStore = context.dataStore

    override suspend fun readBungieHelpPostItems(): BungieHelpPostItems =
        dataStore.data.firstOrNull() ?: BungieHelpPostItems.getDefaultInstance()

    override suspend fun saveBungieHelpPostItems(items: BungieHelpPostItems) {
        dataStore.updateData { items }
    }
}
