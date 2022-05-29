package ru.kykapek.gameoflife.ui.game

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.kykapek.gameoflife.model.Cell
import ru.kykapek.gameoflife.model.Grid

class GameViewModel : ViewModel() {

    var cells: MutableList<MutableList<Cell>> = mutableListOf()
    private val _cells = MutableLiveData<MutableList<MutableList<Cell>>>()

    fun getGrid() : MutableLiveData<MutableList<MutableList<Cell>>> {
        return _cells
    }

    fun getCells() : Any {
        return cells
    }

    var grid = Grid(ROWS, COLUMNS)

    var gameState = PAUSED


    companion object {
        const val PAUSED = "Paused"
        const val STARTED = "Started"
        const val STOPPED = "Stopped"
        const val NO_STATE = "No_State"
        const val ROWS = 30
        const val COLUMNS = 30

    }
}