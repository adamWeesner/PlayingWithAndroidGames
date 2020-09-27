package com.weesnerDevelopment.gameEngine.file

import java.io.InputStream
import java.io.OutputStream

interface FileIO {
    fun readAsset(fileName: String): InputStream
    fun readFile(fileName: String): InputStream
    fun writeFile(fileName: String): OutputStream
}
