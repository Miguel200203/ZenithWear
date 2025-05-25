package com.example.zenithwear.data.Model

import com.example.zenithwear.R

fun getDrawableResource(imageId: Int): Int {
    return when (imageId) {
        1 -> R.drawable.sudadera
        2 -> R.drawable.ropah
        3 -> R.drawable.ropam
        4 -> R.drawable.ropak
        5 -> R.drawable.collections
        6 -> R.drawable.curry
        7 -> R.drawable.footwear
        8 -> R.drawable.samba
        9 -> R.drawable.accessories
        10 -> R.drawable.mochila
        11 -> R.drawable.sportwear
        else -> R.drawable.error
    }
}
