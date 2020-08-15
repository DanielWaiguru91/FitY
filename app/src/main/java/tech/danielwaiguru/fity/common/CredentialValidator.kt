package tech.danielwaiguru.fity.common

import kotlin.properties.Delegates

class CredentialValidator : Validator {
    lateinit var username: String
    private var weight by Delegates.notNull<Float>()
    override fun setDetails(name: String, weight: Float) {
        this.username = name
        this.weight = weight
    }

    override fun isNameValid(): Boolean = username.length > 5

    override fun isWeightValid(): Boolean = weight > 0

    override fun areTheDetailsValid(): Boolean = isNameValid() && isWeightValid()

}