package com.omersenturk.apiflowposts.extension


fun String.capitalizeFirst(): String {
    val text = this.trim()
    return if (text.isNotEmpty()) {
        text.replaceFirstChar { it.uppercase() }
    } else {
        text
    }
}
