package com.weesnerdevelopment.playingwithgames.buildingAndroidGames.file

import android.content.Context
import android.content.SharedPreferences
import android.content.res.AssetManager
import androidx.preference.PreferenceManager
import java.io.*

class AndroidFileIO(
    private val context: Context
) : FileIO {
    private val assetManager: AssetManager = context.assets
    private val externalStoragePath: String =
        context.filesDir.absolutePath + File.separator

    override fun readAsset(fileName: String): InputStream =
        assetManager.open(fileName)

    override fun readFile(fileName: String): InputStream =
        FileInputStream(externalStoragePath + fileName)

    override fun writeFile(fileName: String): OutputStream =
        FileOutputStream(externalStoragePath + fileName)

    override fun getPreferences(): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)
}
