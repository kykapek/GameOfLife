package ru.kykapek.gameoflife.ui.game

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.grid_square.view.*
import ru.kykapek.gameoflife.R
import ru.kykapek.gameoflife.databinding.FragmentGameBinding
import ru.kykapek.gameoflife.model.Cell
import ru.kykapek.gameoflife.model.Grid

class GameFragment : Fragment() {

    private lateinit var gridRecyclerView: RecyclerView
    private var adapter: RecyclerViewAdapter? = null
    private lateinit var grid :Grid
    private var handler: Handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    val gameViewModel: GameViewModel by viewModels()
    private lateinit var state: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        grid = gameViewModel.grid
        state = gameViewModel.gameState
        //checkState(state)
        gridRecyclerView = rvField
        gridRecyclerView.layoutManager = GridLayoutManager(requireActivity().application, 30)
        updateUI()
        ivStop.isEnabled = false

        ivNext.setOnClickListener {
            //if (state == PAUSED) {
                grid.nextGeneration()
                adapter?.notifyDataSetChanged()
            //}
        }

        ivStart.setOnClickListener {
            state = STARTED
            checkState(state)
        }

        ivPause.setOnClickListener {
            state = PAUSED
            checkState(state)
        }

        ivStop.setOnClickListener {
            //handler.removeCallbacks(runnable)
        }
    }

    private fun updateUI() {
        adapter = RecyclerViewAdapter()
        gridRecyclerView.adapter = adapter
        //grid.cells = gameViewModel.cells
    }

    fun checkState(state: String) {
        when(state) {
            "Paused" -> pauseGeneration()
            "Started" -> startGeneration()
            "Stopped" -> stopGeneration()
        }
    }

    private fun pauseGeneration() {
        gameViewModel.gameState = PAUSED
        handler.removeCallbacks(runnable)
    }

    private fun stopGeneration() {

    }

    private fun startGeneration() {
        gameViewModel.gameState = STARTED
        ivStop.isEnabled = true
        runnable = Runnable {
            grid.nextGeneration()
            adapter?.notifyDataSetChanged()
            handler.postDelayed(
                runnable,
                500
            )
        }
        handler.postDelayed(
            runnable,
            500
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        //gameViewModel.gameState = state
    }

    private inner class SquareHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var mPosition = 0

        fun bindPosition(p: Int) {

            mPosition = p
            val arrayIndex: Int = mPosition / 30
            val subArrayIndex = mPosition % 30

            if (grid.cells[arrayIndex][subArrayIndex].status) {
                itemView.grid_square.setBackgroundColor(Color.YELLOW)

            } else {
                itemView.grid_square.setBackgroundColor(Color.GRAY)
            }
        }
        init {
            itemView.grid_square.setOnClickListener {
                val arrayIndex: Int = mPosition / 30
                val subArrayIndex = mPosition % 30
                grid.cells[arrayIndex][subArrayIndex].changeStatus()
                adapter?.notifyItemChanged(mPosition)
            }
        }
    }

    private inner class RecyclerViewAdapter : RecyclerView.Adapter<SquareHolder>() {
        override fun onBindViewHolder(holder: SquareHolder, position: Int) {
            holder.bindPosition(position)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SquareHolder {
            val inflater = LayoutInflater.from(parent.context)
            return SquareHolder(inflater.inflate(R.layout.grid_square, parent, false))
        }

        override fun getItemCount(): Int {
            return 900
        }
    }

    companion object {
        const val PAUSED = "Paused"
        const val STARTED = "Started"
        const val STOPPED = "Stopped"
        const val NO_STATE = "No_State"
        const val ROWS = 30
        const val COLOMNS = 30
    }
}