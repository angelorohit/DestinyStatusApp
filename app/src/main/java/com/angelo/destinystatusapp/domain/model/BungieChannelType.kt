package com.angelo.destinystatusapp.domain.model

enum class BungieChannelType {
    BungieHelp,
    Destiny2Team,
    DestinyTheGame;

    fun toDisplayHandle(): String {
        return "@$name"
    }

    companion object {
        fun fromName(name: String): BungieChannelType {
            return entries.find { it.name.equals(name, ignoreCase = true) }
                ?: throw IllegalArgumentException("Invalid channel type name: $name")
        }
    }
}
