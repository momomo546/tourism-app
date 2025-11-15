package com.example.kenroku_app.model.services.gps

import android.content.Context
import android.os.Environment
import java.io.File

class GpxWriter(private val context: Context) {

    // Documents 配下に保存する
    private val gpxFile: File by lazy {
        val dir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        if (dir != null && !dir.exists()) dir.mkdirs()
        File(dir, "gps_history.gpx")
    }

    private val header = """
        <?xml version="1.0" encoding="UTF-8"?>
        <gpx version="1.1" creator="KenrokuApp" xmlns="http://www.topografix.com/GPX/1/1">
        <trk><trkseg>
    """.trimIndent()

    private val footer = """
        </trkseg></trk></gpx>
    """.trimIndent()

    init {
        if (!gpxFile.exists()) {
            // 新規作成：ヘッダーを書く
            gpxFile.writeText(header + "\n")
        } else {
            // 既存ファイル：前回終了時につけた footer を削除
            removeFooterIfExists()
        }
    }

    /** 位置情報を GPX の trkpt として追加 */
    fun writeTrackPoint(lat: Double, lon: Double, time: Long) {
        val timestamp = java.time.Instant.ofEpochMilli(time).toString()

        val trackPoint = """
            <trkpt lat="$lat" lon="$lon">
                <time>$timestamp</time>
            </trkpt>
        """.trimIndent()

        gpxFile.appendText(trackPoint + "\n")
    }

    /** アプリ終了時に GPX を正しく閉じる */
    fun closeFile() {
        if (!isClosed()) {
            gpxFile.appendText(footer)
        }
    }

    /** footer を削除して追記可能な状態にする */
    private fun removeFooterIfExists() {
        val text = gpxFile.readText()

        if (text.contains(footer)) {
            val cleaned = text.replace(footer, "")
            gpxFile.writeText(cleaned.trim() + "\n")
        }
    }

    /** GPX が閉じられているか確認 */
    private fun isClosed(): Boolean {
        val text = gpxFile.readText().trim()
        return text.endsWith("</gpx>")
    }
}