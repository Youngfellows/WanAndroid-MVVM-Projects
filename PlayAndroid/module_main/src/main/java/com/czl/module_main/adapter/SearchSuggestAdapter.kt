package com.czl.module_main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.czl.lib_base.data.db.SearchHistoryEntity
import com.czl.module_main.R
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter


/**
 * @author Alwyn
 * @Date 2020/11/5
 * @Description
 */
class SearchSuggestAdapter(inflater: LayoutInflater) :
    SuggestionsAdapter<String, SearchSuggestAdapter.SuggestionHolder>(inflater) {
    private var listener: OnItemViewClickListener? = null

    fun setListener(listener: OnItemViewClickListener) {
        this.listener = listener
    }

    inner class SuggestionHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById<View>(R.id.tv_title) as TextView

        init {
            itemView.setOnClickListener {
                listener?.OnItemClickListener(bindingAdapterPosition, it)
            }
            itemView.findViewById<ImageView>(R.id.iv_delete).setOnClickListener {
                listener?.OnItemDeleteListener(bindingAdapterPosition,it)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionHolder {
        return SuggestionHolder(
            layoutInflater.inflate(
                R.layout.main_item_suggestion,
                parent,
                false
            )
        )
    }

    override fun getSingleViewHeight(): Int {
        return 40
    }

    override fun onBindSuggestionHolder(
        suggestion: String,
        holder: SuggestionHolder,
        position: Int
    ) {
        holder.title.text = suggestion
    }
}