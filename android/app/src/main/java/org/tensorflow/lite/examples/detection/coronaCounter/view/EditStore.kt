package org.tensorflow.lite.examples.detection.coronaCounter.view

import android.app.AlertDialog
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
    private var _context : Context? = null
    private val mycontext get() = _context!!
    private lateinit var shop: Shop
    private lateinit var deleteButton: Button
    private lateinit var name:TextView
    private lateinit var max:TextView
    private lateinit var businessTypeSpinner : Spinner
    private lateinit var locationSpinner : Spinner
    private lateinit var editButton: Button
    private lateinit var backButton: Button
    private val sharedViewModel: AppViewModel by activityViewModels()

    // TODO: Rename and change types of parameters


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentEditStoreBinding.inflate(inflater, container, false)
        val view = binding.root
        _context = container?.context
        shop = arguments?.getSerializable("shop") as Shop
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        //private method of your class
        fun getIndex(spinner:Spinner, myString:String):Int{
            for (i:Int in 0..spinner.count){
            if (spinner.getItemAtPosition(i).toString().equals(myString)){
                return i;
            }
        }
            return 0;
        }
        backButton = binding.backButton
        backButton.setOnClickListener {
            val action = EditStoreDirections.actionEditStoreToMyPage()
            view.findNavController().navigate(action)}
        editButton = binding.editButton
        max = binding.maxEdit
        name = binding.nameEdit
        max.setText(shop.maximumPeople.toString());

        name.setText(shop.shopName);

        binding.shopName.text = shop.shopName
        deleteButton = binding.deleteButton
        deleteButton.setOnClickListener {

            val builder = AlertDialog.Builder(mycontext)
            builder.setMessage("정말로 지우시겠습니까?").setCancelable(false).setPositiveButton("Yes"){dialog, id ->
                if (shop.equals(sharedViewModel.mainShop.value)){
                    Toast.makeText(getActivity(), "현재 이용중인 상가는 삭제하실 수 없습니다", Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"현재 이용중인 상가는 삭제하실 수 없습니다")
                }
                else{

                    // val shop = sharedViewModel.shop.value!!
                    Log.d(TAG,"shop delete" + shop.toString())
                    lifecycleScope.launch{
                        val succeed = sharedViewModel.deleteShop(shop)
                        if (succeed){
                            Toast.makeText(getActivity(), "상가 삭제 성공", Toast.LENGTH_SHORT).show();
                            sharedViewModel.fetchShops()
                            val action = EditStoreDirections.actionEditStoreToMyPage()
                            view.findNavController().navigate(action)
                            Log.d(TAG,"${shop.toString()} deleted")
                        }else{
                            Toast.makeText(getActivity(), "상가 삭제 실패", Toast.LENGTH_SHORT).show();
                            Log.d(TAG,"delete failed ${shop.toString()}")
                        }
                    }
                }
            }.setNegativeButton("No") { dialog, id ->
                // Dismiss the dialog
                dialog.dismiss()
            }
            val alert = builder.create()
            alert.show()



        }




        businessTypeSpinner = binding.businessTypeSpinnerEdit
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
        businessTypeSpinner.setSelection(getIndex(businessTypeSpinner, shop.businessType.toString()));


        locationSpinner = binding.locationSpinnerEdit
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
        locationSpinner.setSelection(getIndex(locationSpinner, shop.location.toString()));


        editButton.setOnClickListener {
            val isMain = shop.equals(sharedViewModel.mainShop.value)
            val newshop = Shop(shop.onum as? Integer,shop.sid, name.text.toString(),locationSpinner.selectedItem.toString(),max.text.toString().toInt() as? Integer,BusinessType.valueOf(businessTypeSpinner.selectedItem.toString()))
            Log.d(TAG,"shop edit:" + newshop.toString())
            lifecycleScope.launch{
                val succeed = sharedViewModel.editShop(mapOf<String,Shop>(shop.sid!! to newshop))
                if (succeed){
                    sharedViewModel.fetchShops()
                    if (isMain){
                        val action = EditStoreDirections.actionEditStoreToSelectPrimaryShop()
                        Toast.makeText(getActivity(), "메인 상가를 변경하셨으므로 다시 선택해 주십시오", Toast.LENGTH_SHORT).show();
                        view.findNavController().navigate(action)
                        Log.d(TAG,"${newshop.toString()} edited")
                    }
                    else{
                        Toast.makeText(getActivity(), "상가 편집 성공", Toast.LENGTH_SHORT).show();
                        val action = EditStoreDirections.actionEditStoreToMyPage()
                        view.findNavController().navigate(action)
                        Log.d(TAG,"${newshop.toString()} edited")
                    }

                }else{
                    Toast.makeText(getActivity(), "상가 변경 실패", Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"edit failed ${newshop.toString()}")
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