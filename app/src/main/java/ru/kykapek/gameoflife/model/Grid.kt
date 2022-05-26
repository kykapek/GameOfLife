package ru.kykapek.gameoflife.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Grid(var rows: Int, var columns: Int) : Parcelable {

    var cells: MutableList<MutableList<Cell>> = mutableListOf()

    init {
        for (row in 0 until rows) {
            val rowOfCells: MutableList<Cell> = mutableListOf()
            for (col in 0 until columns) {
                rowOfCells.add(Cell(row, col, false))
            }
            cells.add(rowOfCells)
        }
    }

    fun nextGeneration() {
        val livingNeighborsCount = MutableList(30) {MutableList(30) { 0 }}
        for(i in 0 until rows) {
            for(j in 0 until columns){
                val leftOfRow: Int = i + rows - 1
                val rightOfRow: Int = i + 1
                val leftOfColumn: Int = j + columns - 1
                val rightOfColumn: Int = j + 1

                if ( cells[i][j].status ) {
                    livingNeighborsCount[leftOfRow % rows][leftOfColumn % columns]++
                    livingNeighborsCount[leftOfRow % rows][j % columns]++
                    livingNeighborsCount[(i + rows - 1) % rows][rightOfColumn % columns]++
                    livingNeighborsCount[i % rows][leftOfColumn % columns]++
                    livingNeighborsCount[i % rows][rightOfColumn % columns]++
                    livingNeighborsCount[rightOfRow % rows][leftOfColumn % columns]++
                    livingNeighborsCount[rightOfRow % rows][j % columns]++
                    livingNeighborsCount[rightOfRow % rows][rightOfColumn % columns]++
                }
            }
        }

        for(i in 0 until rows) {
            for(j in 0 until columns){
                if (livingNeighborsCount[i][j] >= 4){
                    cells[i][j].assignStatus(false)
                }

                if (livingNeighborsCount[i][j] < 2){
                    cells[i][j].assignStatus(false)
                }

                if (livingNeighborsCount[i][j] == 3){
                    cells[i][j].assignStatus(true)
                }
            }
        }
    }
}