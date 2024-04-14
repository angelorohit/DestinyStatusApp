package com.angelo.destinystatusapp.domain.mapper

import com.angelo.destinystatusapp.data.model.BungieChannelType as DataChannelType
import com.angelo.destinystatusapp.domain.model.BungieChannelType as DomainChannelType

fun DomainChannelType.toDataChannelType(): DataChannelType = when (this) {
    DomainChannelType.BungieHelp -> DataChannelType.BungieHelp
    DomainChannelType.Destiny2Team -> DataChannelType.Destiny2Team
    DomainChannelType.DestinyTheGame -> DataChannelType.DestinyTheGame
}
