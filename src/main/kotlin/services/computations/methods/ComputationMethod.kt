package services.computations.methods

import domain.models.UserInputModel
import services.LogService

interface CalcLog

interface ComputationMethod {
    val description: String
    fun compute(userInputModel: UserInputModel, logService: LogService): List<CalcLog>
}