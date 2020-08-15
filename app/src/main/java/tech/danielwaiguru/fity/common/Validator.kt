package tech.danielwaiguru.fity.common

interface Validator {
    fun setDetails(name: String, weight: Float)
    fun isNameValid(): Boolean
    fun isWeightValid(): Boolean
    fun areTheDetailsValid(): Boolean
}