package com.example.coronacounter.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.coronacounter.model.Shop
import org.tensorflow.lite.examples.detection.databinding.FragmentMainMenuBinding
import com.example.coronacounter.viewModel.AppViewModel
import kotlinx.coroutines.launch
import org.tensorflow.lite.examples.detection.imageProcessor.IPActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [MainMenu.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val TAG = "MainMenuFragment"
class MainMenu : Fragment() {

    private var _binding: FragmentMainMenuBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: AppViewModel by activityViewModels()

    private lateinit var toCheckPeopleButton: Button
    private lateinit var toDistanceCheckButton: Button
    private lateinit var toStatisticButton: Button
    private lateinit var toMyPageButton: Button
    private lateinit var primaryShop: Shop
    private lateinit var primaryShopNameText: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        val view = binding.root
        primaryShop = arguments?.getSerializable("primaryShop") as Shop
        sharedViewModel.setPrimaryShop(primaryShop)
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        primaryShopNameText=binding.mainMenuText
        sharedViewModel.mainShop.observe(viewLifecycleOwner,{
            mainshop->
            primaryShopNameText.text=mainshop.shopName
        })

        toCheckPeopleButton = binding.checkPeopleButton
        toCheckPeopleButton.setOnClickListener {
            val intent = Intent(getActivity(), IPActivity::class.java)
            intent.putExtra("max", sharedViewModel.mainShop.value!!.maximumPeople?.toInt())
            intent.putExtra("limit", sharedViewModel.limitPeople)
            startActivity(intent)
            Log.d(TAG, "to checkPeople button clicked")
        }

        toDistanceCheckButton = binding.checkStageButton
        toDistanceCheckButton.setOnClickListener {
            val action = MainMenuDirections.actionMainMenuToDistanceStage()
            view.findNavController().navigate(action)
            Log.d(TAG,"to distance stage button clicked")
        }


        toStatisticButton = binding.seeStatisticButton
        toStatisticButton.setOnClickListener {
            lifecycleScope.launch {
                sharedViewModel.getStatistic(sharedViewModel.mainShop.value!!)
                val action = MainMenuDirections.actionMainMenuToStatisticPage()
                view.findNavController().navigate(action)
                Log.d(TAG,"to statistic button clicked")
            }
        }


        toMyPageButton = binding.myPageButton
        toMyPageButton.setOnClickListener {
            lifecycleScope.launch{
                sharedViewModel.fetchShops()
            }
            val action = MainMenuDirections.actionMainMenuToMyPage()
            view.findNavController().navigate(action)
            Log.d(TAG,"to myPage button clicked")
        }
        lifecycleScope.launch {
            // Main
            sharedViewModel.fetchStage()
        }



    }

}