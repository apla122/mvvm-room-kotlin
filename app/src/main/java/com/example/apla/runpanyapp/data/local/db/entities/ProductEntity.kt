package com.example.apla.runpanyapp.data.local.db.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.apla.runpanyapp.models.Product

@Entity(tableName = "products")
class ProductEntity : Product {
    @PrimaryKey
    override var id: Int = 0
    override var name: String? = null
    override var description: String? = null
    override var price: Int = 0

    constructor()

    @Ignore
    constructor(id: Int, name: String, description: String, price: Int) {
        this.id = id
        this.name = name
        this.description = description
        this.price = price
    }

    constructor(product: Product) {
        this.id = product.id
        this.name = product.name
        this.description = product.description
        this.price = product.price
    }
}