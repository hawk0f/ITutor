package dev.hawk0f.itutor.features.finance.presentation.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Utils
import dev.hawk0f.itutor.features.finance.R

@SuppressLint("ViewConstructor")
class MyMarkerViewKt(context: Context?, layoutResource: Int) : MarkerView(context, layoutResource)
{
    private val tvContent: TextView = findViewById(R.id.tvContent)

    // runs every time the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    override fun refreshContent(e: Entry, highlight: Highlight)
    {
        if (e is CandleEntry)
        {
            tvContent.text = Utils.formatNumber(e.high, 0, true)
        }
        else
        {
            tvContent.text = Utils.formatNumber(e.y, 0, true)
        }

        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF
    {
        return MPPointF(-(width / 2).toFloat(), -height.toFloat())
    }
}