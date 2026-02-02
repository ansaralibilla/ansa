package com.ansa.embroidery.data

import android.net.Uri

enum class EmbroideryFormat(val extension: String) {
    DST("dst"),
    PES("pes"),
    JEF("jef"),
    EXP("exp"),
    VP3("vp3"),
    XXX("xxx"),
    UNKNOWN("unknown"),
    ;

    companion object {
        fun fromUri(uri: Uri): EmbroideryFormat {
            val name = uri.lastPathSegment ?: return UNKNOWN
            val extension = name.substringAfterLast('.', missingDelimiterValue = "").lowercase()
            return entries.firstOrNull { it.extension == extension } ?: UNKNOWN
        }
    }
}
