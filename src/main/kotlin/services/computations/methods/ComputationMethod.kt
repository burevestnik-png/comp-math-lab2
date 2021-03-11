package services.computations.methods

import domain.models.UserInputModel

interface ComputationMethod {
    val description: String
    fun compute(userInputModel: UserInputModel)
}