package dev.dmanluc.openbankmobiletest.data.local.mapper

import dev.dmanluc.openbankmobiletest.data.local.entity.CharacterEntity
import dev.dmanluc.openbankmobiletest.domain.model.Character

fun Character.toDatabaseEntity(): CharacterEntity {
    return CharacterEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        modifiedDate = this.modifiedDate,
        resourceURI = this.resourceURI,
        thumbnail = this.thumbnail,
        urls = this.urls,
        comicsAvailable = this.comicsSummary.available,
        seriesAvailable = this.seriesSummary.available,
        storiesAvailable = this.storiesSummary.available,
        eventsAvailable = this.eventsSummary.available
    )
}