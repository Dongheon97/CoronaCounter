package org.tensorflow.lite.examples.detection.coronaCounter.view

import android.content.Context
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
import com.example.coronacounter.model.BusinessType
import com.example.coronacounter.model.Shop
import com.example.coronacounter.view.LoginPageDirections
import com.example.coronacounter.viewModel.AppViewModel
import kotlinx.coroutines.launch
import org.tensorflow.lite.examples.detection.R
import org.tensorflow.lite.examples.detection.databinding.FragmentAddShopPageBinding
import org.tensorflow.lite.examples.detection.databinding.FragmentDistanceStageBinding
import kotlin.math.max

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val TAG = "AddShopPage"
/**
 * A simple [Fragment] subclass.
 * Use the [DistanceStage.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddShopPage : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentAddShopPageBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: AppViewModel by activityViewModels()
    private var _context : Context? = null
    private val mycontext get() = _context!!
    private lateinit var businessTypeSpinner : Spinner
    private lateinit var locationSpinner : Spinner
    private lateinit var maxPeople :EditText
    private lateinit var shopName:EditText
    private lateinit var addButton:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddShopPageBinding.inflate(inflater, container, false)
        val view = binding.root
        _context = container?.context
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addButton = binding.addShopButton
        maxPeople = binding.maxPeople
        shopName = binding.shopName

        businessTypeSpinner = binding.businessTypeSpinner
        ArrayAdapter.createFromResource(
            mycontext,
            R.array.business_type_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            businessTypeSpinner.adapter = adapter
        }


        locationSpinner = binding.locationSpinner
        ArrayAdapter.createFromResource(
            mycontext,
            R.array.location_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            locationSpinner.adapter = adapter
        }

        addButton.setOnClickListener {
            val shop = Shop(sharedViewModel.user.value!!.onum as? Integer,"0", shopName.text.toString(),locationSpinner.selectedItem.toString(),maxPeople.text.toString().toInt() as? Integer,BusinessType.valueOf(businessTypeSpinner.selectedItem.toString()))
            Log.d(TAG,"shop made:" + shop.toString())
            lifecycleScope.launch{
                val succeed = sharedViewModel.addShop(shop)
                if (succeed){
                    sharedViewModel.fetchShops()
                    Log.d(TAG,"${shop.toString()} added")
                }else{
                    Log.d(TAG,"add failed ${shop.toString()}")
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}