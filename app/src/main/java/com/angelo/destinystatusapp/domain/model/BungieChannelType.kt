package com.angelo.destinystatusapp.domain.model

enum class BungieChannelType {
    BungieHelp,
    Destiny2Team,
    DestinyTheGame;

    fun toDisplayHandle(): String {
        return "@$name"
    }
}
