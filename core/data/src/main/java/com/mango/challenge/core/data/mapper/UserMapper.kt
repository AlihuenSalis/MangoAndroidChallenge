package com.mango.challenge.core.data.mapper

import com.mango.challenge.core.data.remote.dto.UserDto
import com.mango.challenge.core.domain.model.Address
import com.mango.challenge.core.domain.model.User
import com.mango.challenge.core.domain.model.UserName

fun UserDto.toDomain(): User = User(
    id = id,
    name = UserName(firstname = name.firstname, lastname = name.lastname),
    email = email,
    phone = phone,
    address = Address(street = address.street, city = address.city, zipcode = address.zipcode)
)
