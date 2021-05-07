package com.example.pokeapp.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.pokeapp.R
import com.example.pokeapp.databinding.EmptyStateViewBinding

class EmptyStateWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: EmptyStateViewBinding =
        EmptyStateViewBinding.inflate(LayoutInflater.from(context), this)

    init
    {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.EmptyStateWidget,
            defStyleAttr,
            defStyleRes
        )

        binding.descriptionTextView.text = typedArray.getString(R.styleable.EmptyStateWidget_description)

        typedArray.recycle()
    }
}