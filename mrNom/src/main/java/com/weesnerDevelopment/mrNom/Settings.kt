package com.weesnerDevelopment.mrNom

import com.weesnerDevelopment.gameEngine.file.FileIO
import com.weesnerDevelopment.gameEngine.math.Size
import java.io.*

object Settings {
    var soundEnabled: Boolean = true
    val highScoresCount = 5
    val highScores = ArrayList<Int>(highScoresCount)

    val gridSize = Size(10, 13)

    fun load(files: FileIO) {
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(InputStreamReader(files.readFile(".mrnom")))
            soundEnabled = reader.readLine().toBoolean()
            for (i in 0 until highScoresCount)
                highScores[i] = reader.readLine().toInt()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        } finally {
            try {
                reader?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun save(files: FileIO) {
        var out: BufferedWriter? = null
        try {
            out = BufferedWriter(OutputStreamWriter(files.writeFile(".mrnom")))
            out.write(soundEnabled.toString() + "\n")
            for (i in 0 until highScoresCount)
                out.write(highScores[i].toString() + "\n")
        } catch (e: IOException) {
        } finally {
            try {
                out?.close()
            } catch (e: IOException) {
            }
        }
    }

    fun addScore(score: Int) {
        highScores.add(score)
        highScores.sortDescending()
        highScores.removeLast()
    }
}