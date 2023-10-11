package com.example.demo.entity

import jakarta.validation.constraints.Pattern

data class Hobby(@field:Pattern(regexp = "^[a-zA-Z0-9]{2,255}$") val hobby: String)