package dev.hawk0f.itutor.features.finance.presentation.ui.fragments

import android.graphics.Color
import android.view.View
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
import dev.hawk0f.itutor.core.presentation.extensions.parentLoader
import dev.hawk0f.itutor.core.presentation.models.PaymentUI
import dev.hawk0f.itutor.features.finance.R
import dev.hawk0f.itutor.features.finance.databinding.FragmentDiagramBinding
import dev.hawk0f.itutor.features.finance.presentation.ui.custom.MyMarkerViewKt
import dev.hawk0f.itutor.features.finance.presentation.ui.viewmodels.DiagramViewModel
import java.time.LocalDate
import java.time.format.TextStyle.SHORT
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

        val mv = MyMarkerViewKt(requireContext(), R.layout.custom_marker_view)
        mv.chartView = chart
        chart.marker = mv

        val l: Legend = chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(true)
        l.yOffset = 0f
        l.xOffset = 10f
        l.yEntrySpace = 0f
        l.setTextSize(12f)

        val xAxis = chart.xAxis
        xAxis.granularity = 1f
        xAxis.setCenterAxisLabels(true)

        val leftAxis = chart.axisLeft

        // Столбец слева, значения
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
            it.setupViewVisibilityLinear(group, parentLoader(R.id.loader))
        }, onSuccess = { payments ->
            viewModel.setPayments(payments)
            buildMpChartForCurrentMonth(filterCurrentMonthPayments(payments))
        })
    }

    private fun buildMpChartForCurrentMonth(payments: List<PaymentUI>) = with(binding) {
        chart.visibility = View.VISIBLE
        val groupSpace = 0.06f
        val barSpace = 0.02f
        val barWidth = 0.1f
        // (0.2 + 0.03) * 4 + 0.08 = 1.00 -> interval per "group"

        val currentMonthProfit = payments.filter { it.hasPaid }.sumOf { it.price.toInt() }.toFloat()
        val currentMonthPotentialProfit = payments.sumOf { it.price.toInt() }.toFloat()

        val values1: ArrayList<BarEntry> = ArrayList()
        val values2: ArrayList<BarEntry> = ArrayList()

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
//                    if (value.toInt() == 0 || value.toInt() == 1)
//                    {
//                        return LocalDate.now().month.getDisplayName(SHORT, Locale.getDefault())
//                    }
//
//                    return ""
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
                if (value.toInt() == 0 || value.toInt() == 1)
                {
                    return LocalDate.now().month.getDisplayName(SHORT, Locale.getDefault())
                }

                return ""
            }
        }

        chart.xAxis.setAxisMinimum(0f)
        chart.xAxis.setAxisMaximum(0f + chart.barData.getGroupWidth(groupSpace, barSpace) * 1)
        chart.xAxis.setCenterAxisLabels(true)

        chart.groupBars(0f, groupSpace, barSpace)
        chart.invalidate()
    }

    private fun buildMpChartForPreviousThreeMonths(payments: List<List<PaymentUI>>) = with(binding) {
        chart.visibility = View.VISIBLE
        val groupSpace = 0.06f
        val barSpace = 0.02f
        val barWidth = 0.45f

        val currentMonthProfit = payments[2].filter { it.hasPaid }.sumOf { it.price.toInt() }.toFloat()
        val currentMonthPotentialProfit = payments[2].sumOf { it.price.toInt() }.toFloat()

        val prevMonthProfit = payments[1].filter { it.hasPaid }.sumOf { it.price.toInt() }.toFloat()
        val prevMonthPotentialProfit = payments[1].sumOf { it.price.toInt() }.toFloat()

        val prevPrevMonthProfit = payments[0].filter { it.hasPaid }.sumOf { it.price.toInt() }.toFloat()
        val prevPrevMonthPotentialProfit = payments[0].sumOf { it.price.toInt() }.toFloat()

        val entriesGroup1: ArrayList<BarEntry> = ArrayList()
        val entriesGroup2: ArrayList<BarEntry> = ArrayList()

        entriesGroup1.add(BarEntry(0f, prevPrevMonthProfit))
        entriesGroup2.add(BarEntry(0f, prevPrevMonthPotentialProfit))
        entriesGroup1.add(BarEntry(1f, prevMonthProfit))
        entriesGroup2.add(BarEntry(1f, prevMonthPotentialProfit))
        entriesGroup1.add(BarEntry(2f, currentMonthProfit))
        entriesGroup2.add(BarEntry(2f, currentMonthPotentialProfit))

        val set1: BarDataSet
        val set2: BarDataSet

        if (chart.data != null && chart.data.getDataSetCount() > 0)
        {
            set1 = chart.data.getDataSetByIndex(0) as BarDataSet
            set2 = chart.data.getDataSetByIndex(1) as BarDataSet

            set1.setValues(entriesGroup1)
            set2.setValues(entriesGroup2)
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        }
        else
        {
            set1 = BarDataSet(entriesGroup1, "Фактический доход")
            set1.setColor(Color.rgb(104, 241, 175))
            set2 = BarDataSet(entriesGroup2, "Ожидаемый доход")
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
                if (value.toInt() == 0)
                {
                    return LocalDate.now()
                        .minusMonths(2).month.getDisplayName(SHORT, Locale.getDefault())
                }
                else if (value.toInt() == 1)
                {
                    return LocalDate.now()
                        .minusMonths(1).month.getDisplayName(SHORT, Locale.getDefault())
                }

                return LocalDate.now().month.getDisplayName(SHORT, Locale.getDefault())
            }
        }

        chart.xAxis.setAxisMinimum(0f)
        chart.xAxis.setAxisMaximum(0f + chart.barData.getGroupWidth(groupSpace, barSpace) * 1)
        chart.xAxis.setCenterAxisLabels(true)

        chart.groupBars(0f, groupSpace, barSpace)
        chart.invalidate()
    }

    override fun setupListeners()
    {
        setupCurrentMonthChip()
        setupPreviousThreeMonthsChip()
    }

    private fun setupCurrentMonthChip() = with(binding) {
        currentMonth.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                buildMpChartForCurrentMonth(filterCurrentMonthPayments(viewModel.getPayments()))
            }
        }
    }

    private fun setupPreviousThreeMonthsChip() = with(binding) {
        previousThreeMonths.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                buildMpChartForPreviousThreeMonths(filterPreviousThreeMonthsPayments(viewModel.getPayments()))
            }
        }
    }

    private fun filterCurrentMonthPayments(payments: List<PaymentUI>): List<PaymentUI>
    {
        val currentMonthPayments = ArrayList<PaymentUI>()
        val currentMonth = LocalDate.now().month
        payments.forEach { payment ->
            if (payment.date.month.equals(currentMonth))
            {
                currentMonthPayments.add(payment)
            }
        }

        return currentMonthPayments
    }

    private fun filterPreviousThreeMonthsPayments(payments: List<PaymentUI>): List<List<PaymentUI>>
    {
        val currentMonthPayments = ArrayList<PaymentUI>()
        val prevMonthPayments = ArrayList<PaymentUI>()
        val prevPrevMonthPayments = ArrayList<PaymentUI>()

        val currentMonth = LocalDate.now().month
        val prevMonth = LocalDate.now().minusMonths(1).month
        val prevPrevMonth = LocalDate.now().minusMonths(2).month

        payments.forEach { payment ->
            when (payment.date.month)
            {
                currentMonth ->
                {
                    currentMonthPayments.add(payment)
                }

                prevMonth ->
                {
                    prevMonthPayments.add(payment)
                }

                prevPrevMonth ->
                {
                    prevPrevMonthPayments.add(payment)
                }

                else ->
                {
                }
            }
        }

        return listOf(prevPrevMonthPayments, prevMonthPayments, currentMonthPayments)

    }

    override fun onResume()
    {
        super.onResume()
        fetchPayments()
    }

    override fun onValueSelected(e: Entry?, h: Highlight?)
    {
    }

    override fun onNothingSelected()
    {
    }
}