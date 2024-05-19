package dev.hawk0f.itutor.features.finance.presentation.ui.fragments

import android.graphics.Color
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis.XAxisPosition.TOP
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import dev.hawk0f.itutor.core.presentation.base.BaseFragment
import dev.hawk0f.itutor.core.presentation.models.LessonStudentUI
import dev.hawk0f.itutor.features.finance.R
import dev.hawk0f.itutor.features.finance.databinding.FragmentDiagramBinding
import dev.hawk0f.itutor.features.finance.presentation.ui.custom.MyMarkerViewKt
import dev.hawk0f.itutor.features.finance.presentation.ui.viewmodels.DiagramViewModel
import java.time.LocalDate
import java.time.format.TextStyle.SHORT_STANDALONE
import java.util.Locale
import kotlin.math.ceil

private const val groupSpace = 0.06f
private const val barSpace = 0.02f
private const val barWidth = 0.45f

@AndroidEntryPoint
class DiagramFragment : BaseFragment<DiagramViewModel, FragmentDiagramBinding>(R.layout.fragment_diagram), OnChartValueSelectedListener
{
    override val viewModel: DiagramViewModel by viewModels()
    override val binding: FragmentDiagramBinding by viewBinding(FragmentDiagramBinding::bind)

    override fun onResume()
    {
        super.onResume()
        fetchPayments()
    }

    override fun initialize()
    {
        setupDiagram()
    }

    private fun setupDiagram() = with(binding) {
        chart.setOnChartValueSelectedListener(this@DiagramFragment)
        chart.description.isEnabled = false
        chart.setPinchZoom(false)
        chart.setDrawBarShadow(false)
        chart.setDrawGridBackground(false)
        chart.setDrawValueAboveBar(true)

        val mv = MyMarkerViewKt(requireContext(), R.layout.custom_marker_view)
        mv.chartView = chart
        chart.marker = mv

        val l: Legend = chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(true)
        l.yOffset = 2f
        l.xOffset = 10f
        l.yEntrySpace = 0f
        l.setTextSize(12f)

        val xAxis = chart.xAxis
        xAxis.isGranularityEnabled = true
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
            it.setupViewVisibilityLinear(group, loader)
        }, onSuccess = { payments ->
            viewModel.setPayments(payments)

            when (chipGroup.checkedChipId)
            {
                R.id.previous_three_months ->
                {
                    buildMpChartForPreviousThreeMonths(filterPreviousThreeMonthsPayments(payments))
                }

                R.id.previous_six_months ->
                {
                    buildMpChartForPreviousSixMonths(filterPreviousSixMonthsPayments(payments))
                }

                else ->
                {
                    buildMpChartForCurrentMonth(filterCurrentMonthPayments(payments))
                }
            }
        })
    }

    private fun buildMpChartForCurrentMonth(payments: List<LessonStudentUI>) = with(binding) {
        val currentMonthProfit = payments.filter { it.hasPaid }.sumOf { it.price.toInt() }.toFloat()
        val currentMonthPotentialProfit = payments.sumOf { it.price.toInt() }.toFloat()

        val entriesGroup1: ArrayList<BarEntry> = ArrayList()
        val entriesGroup2: ArrayList<BarEntry> = ArrayList()

        entriesGroup1.add(BarEntry(0f, currentMonthProfit))
        entriesGroup2.add(BarEntry(0f, currentMonthPotentialProfit))

        setData(entriesGroup1, entriesGroup2)

        val months = getMonthsNamesList(1)
        chart.xAxis.valueFormatter = IndexAxisValueFormatter(months)
        chart.xAxis.position = TOP
        chart.xAxis.setAxisMinimum(0f)
        chart.xAxis.setAxisMaximum(0f + chart.barData.getGroupWidth(groupSpace, barSpace))

        chart.setVisibleXRange(0f, 1f)

        chart.groupBars(0f, groupSpace, barSpace)
        chart.invalidate()
    }

    private fun filterCurrentMonthPayments(payments: List<LessonStudentUI>): List<LessonStudentUI>
    {
        val currentMonthPayments = ArrayList<LessonStudentUI>()
        val currentMonthYear = LocalDate.now().year
        val currentMonth = LocalDate.now().month
        payments.forEach { payment ->
            if (payment.date.month == currentMonth && payment.date.year == currentMonthYear)
            {
                currentMonthPayments.add(payment)
            }
        }

        return currentMonthPayments
    }

    private fun buildMpChartForPreviousThreeMonths(payments: List<List<LessonStudentUI>>) = with(binding) {
        val firstMonthProfit =
            payments[0].filter { it.hasPaid }.sumOf { it.price.toInt() }.toFloat()
        val firstMonthPotentialProfit = payments[0].sumOf { it.price.toInt() }.toFloat()

        val secondMonthProfit =
            payments[1].filter { it.hasPaid }.sumOf { it.price.toInt() }.toFloat()
        val secondMonthPotentialProfit = payments[1].sumOf { it.price.toInt() }.toFloat()

        val thirdMonthProfit =
            payments[2].filter { it.hasPaid }.sumOf { it.price.toInt() }.toFloat()
        val thirdMonthPotentialProfit = payments[2].sumOf { it.price.toInt() }.toFloat()

        val entriesGroup1: ArrayList<BarEntry> = ArrayList()
        val entriesGroup2: ArrayList<BarEntry> = ArrayList()

        entriesGroup1.add(BarEntry(0f, firstMonthProfit))
        entriesGroup1.add(BarEntry(1f, secondMonthProfit))
        entriesGroup1.add(BarEntry(2f, thirdMonthProfit))

        entriesGroup2.add(BarEntry(0f, firstMonthPotentialProfit))
        entriesGroup2.add(BarEntry(1f, secondMonthPotentialProfit))
        entriesGroup2.add(BarEntry(2f, thirdMonthPotentialProfit))

        setData(entriesGroup1, entriesGroup2)

        val months = getMonthsNamesList(3)

        chart.xAxis.valueFormatter = IndexAxisValueFormatter(months)
        chart.xAxis.position = TOP
        chart.xAxis.setAxisMinimum(0f)
        chart.xAxis.setAxisMaximum(0f + chart.barData.getGroupWidth(groupSpace, barSpace) * 1)

        chart.setVisibleXRange(0f, 3f)

        chart.groupBars(0f, groupSpace, barSpace)
        chart.invalidate()
    }

    private fun filterPreviousThreeMonthsPayments(payments: List<LessonStudentUI>): List<List<LessonStudentUI>>
    {
        val thirdMonthPayments = ArrayList<LessonStudentUI>()
        val secondMonthPayments = ArrayList<LessonStudentUI>()
        val firstMonthPayments = ArrayList<LessonStudentUI>()

        val thirdMonthYear = LocalDate.now().year
        val secondMonthYear = LocalDate.now().minusMonths(1).year
        val firstMonthYear = LocalDate.now().minusMonths(2).year

        val thirdMonth = LocalDate.now().month
        val secondMonth = LocalDate.now().minusMonths(1).month
        val firstMonth = LocalDate.now().minusMonths(2).month

        payments.forEach { payment ->
            if (payment.date.month == thirdMonth && payment.date.year == thirdMonthYear)
            {
                thirdMonthPayments.add(payment)
            }
            else if (payment.date.month == secondMonth && payment.date.year == secondMonthYear)
            {
                secondMonthPayments.add(payment)
            }
            else if (payment.date.month == firstMonth && payment.date.year == firstMonthYear)
            {
                firstMonthPayments.add(payment)
            }
        }

        return listOf(firstMonthPayments, secondMonthPayments, thirdMonthPayments)
    }

    private fun buildMpChartForPreviousSixMonths(payments: List<List<LessonStudentUI>>) = with(binding) {
        val firstMonthProfit =
            payments[0].filter { it.hasPaid }.sumOf { it.price.toInt() }.toFloat()
        val firstMonthPotentialProfit = payments[3].sumOf { it.price.toInt() }.toFloat()

        val secondMonthProfit =
            payments[1].filter { it.hasPaid }.sumOf { it.price.toInt() }.toFloat()
        val secondMonthPotentialProfit = payments[4].sumOf { it.price.toInt() }.toFloat()

        val thirdMonthProfit =
            payments[2].filter { it.hasPaid }.sumOf { it.price.toInt() }.toFloat()
        val thirdMonthPotentialProfit = payments[5].sumOf { it.price.toInt() }.toFloat()

        val fourthPrevMonthProfit =
            payments[3].filter { it.hasPaid }.sumOf { it.price.toInt() }.toFloat()
        val fourthMonthPotentialProfit = payments[3].sumOf { it.price.toInt() }.toFloat()

        val fifthMonthProfit =
            payments[4].filter { it.hasPaid }.sumOf { it.price.toInt() }.toFloat()
        val fifthMonthPotentialProfit = payments[4].sumOf { it.price.toInt() }.toFloat()

        val sixthMonthProfit =
            payments[5].filter { it.hasPaid }.sumOf { it.price.toInt() }.toFloat()
        val sixthMonthPotentialProfit = payments[5].sumOf { it.price.toInt() }.toFloat()

        val entriesGroup1: ArrayList<BarEntry> = ArrayList<BarEntry>().apply {
            add(BarEntry(0f, firstMonthProfit))
            add(BarEntry(1f, secondMonthProfit))
            add(BarEntry(2f, thirdMonthProfit))
            add(BarEntry(3f, fourthPrevMonthProfit))
            add(BarEntry(4f, fifthMonthProfit))
            add(BarEntry(5f, sixthMonthProfit))
        }

        val entriesGroup2: ArrayList<BarEntry> = ArrayList<BarEntry>().apply {
            add(BarEntry(0f, firstMonthPotentialProfit))
            add(BarEntry(1f, secondMonthPotentialProfit))
            add(BarEntry(2f, thirdMonthPotentialProfit))
            add(BarEntry(3f, fourthMonthPotentialProfit))
            add(BarEntry(4f, fifthMonthPotentialProfit))
            add(BarEntry(5f, sixthMonthPotentialProfit))
        }

        setData(entriesGroup1, entriesGroup2)

        val months = getMonthsNamesList(6)

        chart.xAxis.valueFormatter = IndexAxisValueFormatter(months)
        chart.xAxis.position = TOP
        chart.xAxis.setAxisMinimum(0f)
        chart.xAxis.setAxisMaximum(0f + chart.barData.getGroupWidth(groupSpace, barSpace))

        chart.setVisibleXRange(0f, 6f)

        chart.groupBars(0f, groupSpace, barSpace)
        chart.invalidate()
    }

    private fun filterPreviousSixMonthsPayments(payments: List<LessonStudentUI>): List<List<LessonStudentUI>>
    {
        val sixthMonthPayments = ArrayList<LessonStudentUI>()
        val fifthMonthPayments = ArrayList<LessonStudentUI>()
        val fourthMonthPayments = ArrayList<LessonStudentUI>()
        val thirdMonthPayments = ArrayList<LessonStudentUI>()
        val secondMonthPayments = ArrayList<LessonStudentUI>()
        val firstMonthPayments = ArrayList<LessonStudentUI>()

        val sixthMonth = LocalDate.now().month
        val fifthMonth = LocalDate.now().minusMonths(1).month
        val fourthMonth = LocalDate.now().minusMonths(2).month
        val thirdMonth = LocalDate.now().minusMonths(3).month
        val secondMonth = LocalDate.now().minusMonths(4).month
        val firstMonth = LocalDate.now().minusMonths(5).month

        val sixthMonthYear = LocalDate.now().year
        val fifthMonthYear = LocalDate.now().minusMonths(1).year
        val fourthMonthYear = LocalDate.now().minusMonths(2).year
        val thirdMonthYear = LocalDate.now().minusMonths(3).year
        val secondMonthYear = LocalDate.now().minusMonths(4).year
        val firstMonthYear = LocalDate.now().minusMonths(5).year

        payments.forEach { payment ->
            if (payment.date.month == sixthMonth && payment.date.year == sixthMonthYear)
            {
                sixthMonthPayments.add(payment)
            }
            else if (payment.date.month == fifthMonth && payment.date.year == fifthMonthYear)
            {
                fifthMonthPayments.add(payment)
            }
            else if (payment.date.month == fourthMonth && payment.date.year == fourthMonthYear)
            {
                fourthMonthPayments.add(payment)
            }
            else if (payment.date.month == thirdMonth && payment.date.year == thirdMonthYear)
            {
                thirdMonthPayments.add(payment)
            }
            else if (payment.date.month == secondMonth && payment.date.year == secondMonthYear)
            {
                secondMonthPayments.add(payment)
            }
            else if (payment.date.month == firstMonth && payment.date.year == firstMonthYear)
            {
                firstMonthPayments.add(payment)
            }
        }

        return listOf(firstMonthPayments, secondMonthPayments, thirdMonthPayments, fourthMonthPayments, fifthMonthPayments, sixthMonthPayments)
    }

    private fun setData(entriesGroup1: List<BarEntry>, entriesGroup2: List<BarEntry>) = with(binding) {
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
            data.setValueTextSize(12f)
            data.barWidth = barWidth
            chart.setData(data)
        }
    }

    private fun getMonthsNamesList(monthsValue: Int): List<String>
    {
        var i = monthsValue - 1
        val list = ArrayList<String>()
        while (i >= 0)
        {
            list.add(LocalDate.now()
                .minusMonths(i.toLong()).month.getDisplayName(SHORT_STANDALONE, Locale.getDefault()))
            i--
        }
        return list
    }

    override fun setupListeners()
    {
        setupCurrentMonthChip()
        setupPreviousThreeMonthsChip()
        setupPreviousSixMonthsChip()
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

    private fun setupPreviousSixMonthsChip() = with(binding) {
        previousSixMonths.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                buildMpChartForPreviousSixMonths(filterPreviousSixMonthsPayments(viewModel.getPayments()))
            }
        }
    }

    override fun onValueSelected(e: Entry?, h: Highlight?)
    {
    }

    override fun onNothingSelected()
    {
    }
}