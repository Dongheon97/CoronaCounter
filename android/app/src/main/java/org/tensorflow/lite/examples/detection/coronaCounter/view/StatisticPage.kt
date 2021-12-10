package org.tensorflow.lite.examples.detection.coronaCounter.view

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.coronacounter.viewModel.AppViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.coroutines.launch
import org.tensorflow.lite.examples.detection.R
import org.tensorflow.lite.examples.detection.databinding.FragmentMyPageBinding
import org.tensorflow.lite.examples.detection.databinding.FragmentSignUpPageBinding
import org.tensorflow.lite.examples.detection.databinding.FragmentStatisticPageBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [StatisticPage.newInstance] factory method to
 * create an instance of this fragment.
 */
class StatisticPage : Fragment() {
    // TODO: Rename and change types of parameters

    private var _binding: FragmentStatisticPageBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: AppViewModel by activityViewModels()
    private lateinit var chart: BarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatisticPageBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        chart = binding.chart
        val entries = sharedViewModel.statsEntry
        val barDataSet = BarDataSet(entries, "출입 인원 수");

        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS.toList())
        barDataSet.setValueTextColor(Color.BLACK)
        barDataSet.valueTextSize = 16f

        val barData = BarData(barDataSet);

        chart.setFitBars(true)
        chart.setData(barData);
        chart.description.text = "출입 인원 변화 수"
        chart.animateY(1000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}