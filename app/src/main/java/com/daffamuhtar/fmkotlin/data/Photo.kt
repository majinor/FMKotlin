package com.daffamuhtar.fmkotlin.data

data class Photo(
    var file: String,
    var thumbnail: String?,
    var fileFormat: String?,
    var isEditable: Boolean
)
