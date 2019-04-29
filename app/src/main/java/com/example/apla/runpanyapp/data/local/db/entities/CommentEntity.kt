package com.example.apla.runpanyapp.data.local.db.entities

import androidx.room.*
import com.example.apla.runpanyapp.models.Comment
import java.util.*

@Entity(tableName = "comments",
        foreignKeys = [ForeignKey(
                entity = ProductEntity::class,
                parentColumns = ["id"],
                childColumns = ["productId"],
                onDelete = ForeignKey.CASCADE
        )],
        indices = [Index(value = ["productId"], name = "productId")]
)
class CommentEntity : Comment {
    @PrimaryKey(autoGenerate = true)
    override var id: Int = 0
    override var productId: Int = 0
    override var text: String? = null
    override var postedAt: Date? = null

    constructor()

    @Ignore
    constructor(id: Int, productId: Int, text: String, postedAt: Date) {
        this.id = id
        this.productId = productId
        this.text = text
        this.postedAt = postedAt
    }
}