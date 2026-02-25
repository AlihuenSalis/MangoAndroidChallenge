package com.mango.challenge.core.testing

import com.mango.challenge.core.domain.model.Address
import com.mango.challenge.core.domain.model.Product
import com.mango.challenge.core.domain.model.Rating
import com.mango.challenge.core.domain.model.User
import com.mango.challenge.core.domain.model.UserName

object TestFixtures {
    fun testProduct(
        id: Int = 1,
        title: String = "Test Product",
        isFavorite: Boolean = false
    ) = Product(
        id = id,
        title = title,
        price = 9.99,
        description = "A test product",
        category = "test",
        image = "https://example.com/img.jpg",
        rating = Rating(rate = 4.5, count = 100),
        isFavorite = isFavorite
    )

    fun testUser() = User(
        id = 8,
        name = UserName(firstname = "John", lastname = "Doe"),
        email = "john@example.com",
        phone = "555-1234",
        address = Address(street = "Main St", city = "Springfield", zipcode = "12345")
    )
}
