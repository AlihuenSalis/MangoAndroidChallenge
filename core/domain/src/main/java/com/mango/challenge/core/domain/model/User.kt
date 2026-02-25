package com.mango.challenge.core.domain.model

data class User(
    val id: Int,
    val name: UserName,
    val email: String,
    val phone: String,
    val address: Address
)

data class UserName(
    val firstname: String,
    val lastname: String
)

data class Address(
    val street: String,
    val city: String,
    val zipcode: String
)
