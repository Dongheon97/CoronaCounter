package com.example.coronacounter.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.coronacounter.adapter.ShopItemAdapter
import org.tensorflow.lite.examples.detection.databinding.FragmentMyPageBinding
import com.example.coronacounter.viewModel.AppViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [MyPage.newInstance] factory method to
 * create an instance of this fragment.
 */

private const val TAG = "MyPageFragment"
class MyPage : Fragment() {
    // view binding
    private var _binding: FragmentMyPageBinding? = null
    private val binding get() = _binding!!
    private var _context : Context? = null
    private val mycontext get() = _context!!

    private lateinit var addShopButton: Button

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
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        val view = binding.root
        _context = container?.context
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView:RecyclerView = binding.userShopList
        addShopButton = binding.addShopButton
        addShopButton.setOnClickListener {
            Log.d(TAG, "addShopButton Clicked")
            val action = MyPageDirections.actionMyPageToAddShopPage()
            view.findNavController().navigate(action)
            Log.d(TAG,"addShopButtonClicked")

        }
        sharedViewModel.shops.observe(viewLifecycleOwner,
            { shops ->
                    recyclerView.adapter = ShopItemAdapter(mycontext, shops)
            })

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}