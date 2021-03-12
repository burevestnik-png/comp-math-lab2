package services.computations.methods

import domain.models.UserInputModel
import services.LogService

class ChordMethod : ComputationMethod {
    override val description: String
        get() = "chord method"

    override fun compute(userInputModel: UserInputModel, logService: LogService) {
        TODO("Not yet implemented")
    }

    private fun isRootExists(userInputModel: UserInputModel) {

    }
}