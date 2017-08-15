package com.kristal.library.appbase

/**
 * Created by Kristal on 5/30/2017.
 */

data class Size(var width: Int, var height: Int) {
    constructor() : this(0, 0)
}

data class SizeF(var width: Float, var height: Float) {
    constructor() : this(0f, 0f)
}