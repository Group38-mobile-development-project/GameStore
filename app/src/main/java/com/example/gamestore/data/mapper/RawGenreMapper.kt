
package com.example.gamestore.data.mapper

import com.example.gamestore.data.dto.RawGenre
import com.example.gamestore.data.model.Genre

object RawGenreMapper {

    fun fromDto(dto: RawGenre): Genre {
        return Genre(
            id = dto.id,
            title = dto.title,
            identifier = dto.identifier,
            totalGames = dto.totalGames,
            backgroundImage = dto.backgroundImage ?: "",
            associatedGames = dto.associatedGames?.map { RawGameMapper.fromDto(it) }
        )
    }
}


