package com.daffamuhtar.fmkotlin.data.model

data class Photo(
    var file: String,
    var thumbnail: String?,
    var fileFormat: String?,
    var isEditable: Boolean
)
