package com.example.apla.runpanyapp.models

import java.util.*

interface Comment {
    val id: Int
    val productId: Int
    val text: String?
    val postedAt: Date?
}
