package com.skosc.tkffintech.ui.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skosc.tkffintech.R


class UserInfoSectionCard(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0)
    : CardView(context, attributeSet, defStyleAttr) {

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    private val title: TextView by lazy { findViewById<TextView>(R.id.user_info_entry_title) }
    private val icon: ImageView by lazy { findViewById<ImageView>(R.id.user_info_entry_icon) }

    private val localAttrs = context.obtainStyledAttributes(attributeSet,
            R.styleable.UserInfoSectionCard,
            defStyleAttr,
            0
    )

    private val contentView: View = inflate(context, R.layout.card_user_info_section, null)
    val recycler: RecyclerView by lazy { contentView.findViewById<RecyclerView>(R.id.user_info_entry_recycler) }

    var headerText: CharSequence
        get() = title.text
        set(value) {
            title.text = value
        }

    var iconDrawable: Drawable
        get() = icon.drawable
        set(value) {
            icon.setImageDrawable(value)
        }

    init {
        useCompatPadding = true
        addView(contentView)
        title.text = localAttrs.getString(R.styleable.UserInfoSectionCard_header_text)
        icon.setImageDrawable(localAttrs.getDrawable(R.styleable.UserInfoSectionCard_header_icon))
        recycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recycler.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
    }

}