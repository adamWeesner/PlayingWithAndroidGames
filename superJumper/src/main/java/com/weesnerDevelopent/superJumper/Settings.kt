package com.weesnerDevelopent.superJumper

import com.weesnerDevelopment.gameEngine.file.FileIO
import java.io.*

object Settings {
    private const val file = ".superjumper"

    var soundEnabled = true
    val highScoresCount = 5
    val highscores = Array<Int>(highScoresCount) { 1 }

    fun load(files: FileIO) {
        var bufferedReader: BufferedReader? = null
        try {
            bufferedReader = BufferedReader(InputStreamReader(files.readFile(file)))
            soundEnabled = bufferedReader.readLine().toBoolean()

            for (i in 0 until highScoresCount) highscores[i] = bufferedReader.readLine().toInt()
        } catch (e: IOException) {
            // :( It's ok we have defaults
        } catch (e: NumberFormatException) {
            // :/ It's ok, defaults save our day
        } finally {
            try {
                bufferedReader?.close()
            } catch (e: IOException) {
            }
        }
    }

    fun save(files: FileIO) {
        var out: BufferedWriter? = null
        try {
            out = BufferedWriter(OutputStreamWriter(files.writeFile(file)))

            out.write("$soundEnabled\n")

            for (i in 0..4) out.write("${highscores[i]}\n")
        } catch (e: IOException) {
        } finally {
            try {
                out?.close()
            } catch (e: IOException) {
            }
        }
    }

    fun addScore(score: Int) {
        for (i in 0..4) {
            if (highscores[i] < score) {
                for (j in 4 downTo i + 1) highscores[j] = highscores[j - 1]
                highscores[i] = score
                break
            }
        }
    }
}
