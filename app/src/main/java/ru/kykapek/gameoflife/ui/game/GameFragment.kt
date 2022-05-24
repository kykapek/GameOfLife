package ru.kykapek.gameoflife.ui.game

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_game.*
import ru.kykapek.gameoflife.R
import ru.kykapek.gameoflife.model.Cell
import ru.kykapek.gameoflife.model.Grid

class GameFragment : Fragment() {

    private lateinit var gridRecyclerView: RecyclerView
    private var adapter: RecyclerViewAdapter? = null
    private var grid = Grid(30, 30)
    private var handler: Handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gridRecyclerView = rvField
        gridRecyclerView.layoutManager = GridLayoutManager(activity, 30)
        updateUI()

        ivStart.setOnClickListener {
            runnable = Runnable {
                grid.nextGeneration()
                adapter?.notifyDataSetChanged()

                // Schedule the task to repeat after 1 second
                handler.postDelayed(
                    runnable,
                    1000 // Delay in milliseconds
                )
            }

            // Schedule the task to repeat after 1 second
            handler.postDelayed(
                runnable,
                1000 // Delay in milliseconds
            )
        }

        ivStop.setOnClickListener {
            handler.removeCallbacks(runnable) //deschedule repeating process
        }
    }

    // Assign class variables
    private fun updateUI() {
        adapter = RecyclerViewAdapter()
        gridRecyclerView.adapter = adapter
    }

    private fun changeColors(scheme: String?) {
        // Target each view
        var arrayIndex: Int
        var subArrayIndex: Int
        var holder: SquareHolder

        // Cycle through all views
        for (i in 0 until 900) {

            // Used this to find out how to iterate over each RecyclerView view: https://stackoverflow.com/questions/32811156/how-to-iterate-over-recyclerview-items
            holder = gridRecyclerView.findViewHolderForAdapterPosition(i) as SquareHolder // Reference to each view

            arrayIndex = i / 30
            subArrayIndex = i % 30

            // Switch to designated color scheme based on GUI selection

                    if (grid.cells[arrayIndex][subArrayIndex].status) {
                        holder.mButton.setBackgroundResource(R.drawable.yellow_foreground)
                    } else {
                        holder.mButton.setBackgroundResource(R.drawable.gray_foreground)
                    }

        }
    }

    private inner class SquareHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mButton: Button = itemView.findViewById<View>(R.id.grid_square) as Button
        private var mPosition = 0

        fun bindPosition(p: Int) {
            mPosition = p
            val arrayIndex: Int = mPosition / 30
            val subArrayIndex = mPosition % 30

            // Assign alive color
            if (grid.cells[arrayIndex][subArrayIndex].status) {
                mButton.setBackgroundResource(R.drawable.yellow_foreground)

            } else { // Assign dead color
                mButton.setBackgroundResource(R.drawable.gray_foreground)
            }
        }

        // Flip status of cell when clicked
        init {
            mButton.setOnClickListener {
                val arrayIndex: Int = mPosition / 30
                val subArrayIndex = mPosition % 30
                grid.cells[arrayIndex][subArrayIndex].changeStatus()
                adapter?.notifyItemChanged(mPosition)
            }
        }
    }


    // RecyclerViewAdapter
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
}