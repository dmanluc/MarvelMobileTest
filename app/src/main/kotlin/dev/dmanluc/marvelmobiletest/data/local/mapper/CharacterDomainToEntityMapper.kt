package dev.dmanluc.marvelmobiletest.data.local.mapper

import dev.dmanluc.marvelmobiletest.data.local.entity.CharacterEntity
import dev.dmanluc.marvelmobiletest.domain.model.Character

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