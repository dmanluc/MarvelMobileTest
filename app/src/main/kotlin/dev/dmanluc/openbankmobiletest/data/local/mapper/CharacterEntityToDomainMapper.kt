package dev.dmanluc.openbankmobiletest.data.local.mapper

import dev.dmanluc.openbankmobiletest.data.local.entity.CharacterEntity
import dev.dmanluc.openbankmobiletest.domain.model.Character
import dev.dmanluc.openbankmobiletest.domain.model.SummaryList

fun CharacterEntity.toDomainModel(): Character {
    return Character(
        id = this.id,
        name = this.name,
        description = this.description,
        modifiedDate = this.modifiedDate,
        resourceURI = this.resourceURI,
        thumbnail = this.thumbnail,
        urls = this.urls,
        comicsSummary = SummaryList(
            this.comicsAvailable,
            0,
            "",
            emptyList()
        ),
        seriesSummary = SummaryList(
            this.seriesAvailable,
            0,
            "",
            emptyList()
        ),
        storiesSummary = SummaryList(
            this.storiesAvailable,
            0,
            "",
            emptyList()
        ),
        eventsSummary = SummaryList(
            this.eventsAvailable,
            0,
            "",
            emptyList()
        )
    )
}