package org.tensorflow.lite.examples.detection.coronaCounter.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.coronacounter.model.BusinessType
import com.example.coronacounter.model.Shop
import com.example.coronacounter.viewModel.AppViewModel
import kotlinx.coroutines.launch
import org.tensorflow.lite.examples.detection.R
import org.tensorflow.lite.examples.detection.databinding.FragmentEditStoreBinding
import org.tensorflow.lite.examples.detection.databinding.FragmentMainMenuBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val TAG = "EditStore"

/**
 * A simple [Fragment] subclass.
 * Use the [EditStore.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditStore : Fragment() {
    private var _binding: FragmentEditStoreBinding? = null
    private val binding get() = _binding!!
    private lateinit var shop: Shop
    private lateinit var deleteButton: Button

    private val sharedViewModel: AppViewModel by activityViewModels()

    // TODO: Rename and change types of parameters


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentEditStoreBinding.inflate(inflater, container, false)
        val view = binding.root
        shop = arguments?.getSerializable("shop") as Shop
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.shopName.text = shop.shopName
        deleteButton = binding.deleteButton
        deleteButton.setOnClickListener {
            if (shop.equals(sharedViewModel.mainShop.value)){
                Log.d(TAG,"현재 이용중인 상가는 삭제하실 수 없습니다")
            }
            else{
            // val shop = sharedViewModel.shop.value!!
            Log.d(TAG,"shop delete" + shop.toString())
            lifecycleScope.launch{
                val succeed = sharedViewModel.deleteShop(shop)
                if (succeed){
                    sharedViewModel.fetchShops()
                    val action = EditStoreDirections.actionEditStoreToMyPage()
                    view.findNavController().navigate(action)
                    Log.d(TAG,"${shop.toString()} deleted")
                }else{
                    Log.d(TAG,"delete failed ${shop.toString()}")
                }
            }
            }

        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditStore.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditStore().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}