package org.tensorflow.lite.examples.detection.coronaCounter.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.coronacounter.adapter.ShopItemAdapter
import com.example.coronacounter.adapter.ShopPrimaryItemAdapter
import com.example.coronacounter.viewModel.AppViewModel
import org.tensorflow.lite.examples.detection.R
import org.tensorflow.lite.examples.detection.databinding.FragmentMyPageBinding
import org.tensorflow.lite.examples.detection.databinding.FragmentSelectPrimaryShopBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [SelectPrimaryShop.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val TAG = "SelectPrimaryShop"
class SelectPrimaryShop : Fragment() {
    // view binding
    private var _binding: FragmentSelectPrimaryShopBinding? = null
    private val binding get() = _binding!!
    private var _context : Context? = null
    private val mycontext get() = _context!!


    private val sharedViewModel: AppViewModel by activityViewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectPrimaryShopBinding.inflate(inflater, container, false)
        val view = binding.root
        _context = container?.context
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView: RecyclerView = binding.userPrimaryShopList

        sharedViewModel.shops.observe(viewLifecycleOwner,
            { shops ->
                recyclerView.adapter = ShopPrimaryItemAdapter(mycontext, shops)
            })

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}