package services.dao

import domain.Equation

class EquationDAO: DAO<Equation> {
    override fun getItem(source: String, mode: Mode): Equation {
        when (mode) {
            Mode.RESOURCE -> {

            }
        }

        return Equation(emptyArray(), emptyArray())
    }

    override fun saveItem(source: String, t: Equation) {
        TODO("Not yet implemented")
    }
}