package com.unicofrance.uniexo.data.local.database

import android.content.Context
import android.util.Log
import com.unicofrance.uniexo.R
import com.unicofrance.uniexo.data.local.database.entities.Container
import java.io.InputStream

class CsvParser(context: Context) {
    fun parse(inputStream: InputStream): List<Container> {
        val reader = inputStream.bufferedReader()
        reader.readLine() // consume header
        return reader.lineSequence()
            .filter { it.isNotBlank() }
            .map {
                // First split with quote delimiters to ignore the commas that are in quotes
                val quoteSeparation = it.split('"')
                val cols =
                    quoteSeparation[0].trim(',').split(',') +
                    quoteSeparation[1] +
                    quoteSeparation[2].trim(',').split(',')

                Container(
                    id = cols[0],
                    longitude = cols[1].toDouble(),
                    latitude = cols[2].toDouble(),
                    label = cols[3],
                    producingPlaceLabel = cols[4],
                    description = cols[5],
                    streamLabel = cols[6],
                    streamColor = cols[7],
                    iconId = when (cols[8]) {
                        "ic_container_11" -> R.drawable.ic_container_11
                        "ic_container_9" -> R.drawable.ic_container_9
                        else -> R.drawable.ic_container_ccsp_4
                    },
                    creationDatetime = cols[9]
                )
            }.toList()
    }
}