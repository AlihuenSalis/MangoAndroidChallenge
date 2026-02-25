package com.mango.challenge.core.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: UserNameDto,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("address") val address: AddressDto
)

data class UserNameDto(
    @SerializedName("firstname") val firstname: String,
    @SerializedName("lastname") val lastname: String
)

data class AddressDto(
    @SerializedName("street") val street: String,
    @SerializedName("city") val city: String,
    @SerializedName("zipcode") val zipcode: String
)
