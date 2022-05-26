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

    val grid = Grid(30, 30)


    companion object {
        const val rows = 30
        const val columns = 30
    }
}