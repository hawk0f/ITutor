package dev.hawk0f.itutor.features.finance.presentation.ui.fragments

import android.graphics.Color
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import dev.hawk0f.itutor.core.presentation.base.BaseFragment
import dev.hawk0f.itutor.core.presentation.models.PaymentUI
import dev.hawk0f.itutor.features.finance.R
import dev.hawk0f.itutor.features.finance.databinding.FragmentDiagramBinding
import dev.hawk0f.itutor.features.finance.presentation.ui.custom.MyMarkerViewKt
import dev.hawk0f.itutor.features.finance.presentation.ui.viewmodels.DiagramViewModel
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import kotlin.math.ceil

@AndroidEntryPoint
class DiagramFragment : BaseFragment<DiagramViewModel, FragmentDiagramBinding>(R.layout.fragment_diagram), OnChartValueSelectedListener
{
    override val viewModel: DiagramViewModel by viewModels()
    override val binding: FragmentDiagramBinding by viewBinding(FragmentDiagramBinding::bind)

    override fun initialize()
    {
        setupDiagram()
    }

    private fun setupDiagram() = with(binding) {
        chart.setOnChartValueSelectedListener(this@DiagramFragment)
        chart.description.isEnabled = false
        chart.setPinchZoom(false);
        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);

        val mv: MyMarkerViewKt = MyMarkerViewKt(requireContext(), R.layout.custom_marker_view)
        mv.chartView = chart // For bounds control
        chart.marker = mv // Set the marker to the chart

        val l: Legend = chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(true)
        l.yOffset = 0f
        l.xOffset = 10f
        l.yEntrySpace = 0f
        l.setTextSize(8f)

        val xAxis = chart.xAxis
        xAxis.granularity = 1f
        xAxis.setCenterAxisLabels(true)

        val leftAxis = chart.axisLeft
        leftAxis.valueFormatter = object : ValueFormatter()
        {
            override fun getFormattedValue(value: Float): String
            {
                return ceil(value).toInt().toString()
            }
        }
        leftAxis.setDrawGridLines(false)
        leftAxis.spaceTop = 35f
        leftAxis.axisMinimum = 0f

        chart.axisRight.isEnabled = false
        //anyChart.clear()
        //anyChart.setChart(null)
    }

    override fun setupRequests()
    {
        fetchPayments()
    }

    private fun fetchPayments()
    {
        viewModel.fetchPayments()
    }

    override fun setupSubscribers()
    {
        subscribeToPayments()
    }

    private fun subscribeToPayments() = with(binding) {
        viewModel.paymentState.collectAsUIState(state = {
            it.setupViewVisibility(group, loader)
        }, onSuccess = {
            buildMpChart(it)
        })
    }

    private fun buildMpChart(payments: List<PaymentUI>) = with(binding) {

        val groupSpace = 0.08f
        val barSpace = 0.03f // x4 DataSet
        val barWidth = 0.2f // x4 DataSet
        // (0.2 + 0.03) * 4 + 0.08 = 1.00 -> interval per "group"

        val currentMonth =
            LocalDate.now().month.getDisplayName(TextStyle.SHORT, Locale.forLanguageTag("ru"))

        var currentMonthProfit = 0f
        var currentMonthPotentialProfit = 0f

        val values1: ArrayList<BarEntry> = ArrayList()
        val values2: ArrayList<BarEntry> = ArrayList()

        payments.forEach { payment ->
            if (payment.date.month.getDisplayName(TextStyle.SHORT, Locale.forLanguageTag("ru"))
                    .equals(currentMonth))
            {
                if (payment.hasPaid)
                {
                    currentMonthProfit += payment.price
                    currentMonthPotentialProfit += payment.price
                }
                else
                {
                    currentMonthPotentialProfit += payment.price
                }
            }
        }

        values1.add(BarEntry(0f, currentMonthProfit))
        values2.add(BarEntry(1f, currentMonthPotentialProfit))

        val set1: BarDataSet
        val set2: BarDataSet

        if (chart.data != null && chart.data.getDataSetCount() > 0)
        {
            set1 = chart.data.getDataSetByIndex(0) as BarDataSet
            set2 = chart.data.getDataSetByIndex(1) as BarDataSet
            set1.setValues(values1)
            set2.setValues(values2)
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        }
        else
        {
            set1 = BarDataSet(values1, "Фактический доход")
            set1.setColor(Color.rgb(104, 241, 175))
            set2 = BarDataSet(values2, "Ожидаемый доход")
            set2.setColor(Color.rgb(164, 228, 251))

            val data = BarData(set1, set2)
//            data.setValueFormatter(object : ValueFormatter()
//            {
//                override fun getFormattedValue(value: Float): String
//                {
//                    return "Май"
//                }
//            })

            data.setValueTextSize(12f)
            data.barWidth = barWidth
            chart.setData(data)
        }

        chart.xAxis.valueFormatter = object : ValueFormatter()
        {
            override fun getFormattedValue(value: Float): String
            {
                return "Месяц"
            }
        }

        chart.xAxis.setAxisMinimum(0f)
        chart.xAxis.setAxisMaximum(0f + chart.barData.getGroupWidth(groupSpace, barSpace) * 1)
        chart.xAxis.setCenterAxisLabels(true)

        chart.groupBars(0f, groupSpace, barSpace)
        chart.invalidate()
    }

    override fun onResume()
    {
        super.onResume()
        fetchPayments()
    }

//    private fun buildAnyChart(payments: List<PaymentUI>) = with(binding) {
//        val cartesian: Cartesian = AnyChart.column()
//
//        val currentMonth =
//            LocalDate.now().month.getDisplayName(TextStyle.SHORT, Locale.forLanguageTag("ru"))
//
//        var currentMonthProfit = 0f
//        var currentMonthPotentialProfit = 0f
//
//        payments.forEach { payment ->
//            if (payment.date.month.getDisplayName(TextStyle.SHORT, Locale.forLanguageTag("ru"))
//                    .equals(currentMonth))
//            {
//                if (payment.hasPaid)
//                {
//                    currentMonthProfit += payment.price
//                    currentMonthPotentialProfit += payment.price
//                }
//                else
//                {
//                    currentMonthPotentialProfit += payment.price
//                }
//            }
//        }
//
////        val previousMonth = LocalDate.now().month.minus(1).getDisplayName(TextStyle.SHORT, Locale.forLanguageTag("ru"))
////        var previousMonthProfit = 0f
////        successfulTransactions.forEach { transaction ->
////            if (LocalDate.parse(transaction.lesson!!.date, DateTimeFormatter.ofPattern("dd.MM.yyyy")).month.getDisplayName(TextStyle.SHORT, Locale.forLanguageTag("ru")).equals(previousMonth))
////            {
////                previousMonthProfit += transaction.price
////            }
////        }
////
////        val prepreviousMonth = LocalDate.now().month.minus(2).getDisplayName(TextStyle.SHORT, Locale.forLanguageTag("ru"))
////        var prepreviousMonthProfit = 0f
////        successfulTransactions.forEach { transaction ->
////            if (LocalDate.parse(transaction.lesson!!.date, DateTimeFormatter.ofPattern("dd.MM.yyyy")).month.getDisplayName(TextStyle.SHORT, Locale.forLanguageTag("ru")).equals(prepreviousMonth))
////            {
////                prepreviousMonthProfit += transaction.price
////            }
////        }
//
//        val data: MutableList<DataEntry> = ArrayList()
////        data.add(ValueDataEntry(prepreviousMonth, prepreviousMonthProfit))
////        data.add(ValueDataEntry(previousMonth, previousMonthProfit))
//        data.add(ValueDataEntry(currentMonth, currentMonthProfit))
//
//        val column: Column = cartesian.column(data)
//
//        column.tooltip().titleFormat("{%X}").position(Position.CENTER_BOTTOM)
//            .anchor(Anchor.CENTER_BOTTOM).offsetX(0.0).offsetY(5.0)
//            .format("{%Value}{groupsSeparator: } р.")
//
//        cartesian.animation(true)
//        cartesian.title("Доход: $currentMonthProfit Р")
//        cartesian.title().fontSize(25)
//
//        cartesian.yScale().minimum(0.0)
//
//        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: } р.")
//
//        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
//        cartesian.interactivity().hoverMode(HoverMode.BY_X)
//
//        cartesian.xAxis(0).title("Месяц")
//        cartesian.yAxis(0).title("Заработок")
//
//        anyChart.setChart(cartesian)
//
//        anyChart.invalidate()
//    }

    override fun onValueSelected(e: Entry?, h: Highlight?)
    {
    }

    override fun onNothingSelected()
    {
    }
}