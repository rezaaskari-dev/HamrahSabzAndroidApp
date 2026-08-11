package com.android.argan.Detail

data class DetailItem(
    val question: String,
    val answer: String,
    var isExpanded: Boolean = false
)