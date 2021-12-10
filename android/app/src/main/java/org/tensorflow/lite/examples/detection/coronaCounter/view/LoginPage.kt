package com.example.coronacounter.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.coronacounter.model.User
import com.example.coronacounter.viewModel.AppViewModel
import kotlinx.coroutines.*
import org.tensorflow.lite.examples.detection.databinding.FragmentLoginPageBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

private const val TAG = "LoginPageFragment"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginPage.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginPage : Fragment() {
    private var _binding: FragmentLoginPageBinding? = null
    private val binding get() = _binding!!

    private lateinit var userName: TextView
    private lateinit var userPassword: TextView
    private lateinit var signInButton: Button
    private lateinit var signUpButton: Button

    private val sharedViewModel: AppViewModel by activityViewModels()

    lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginPageBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userName = binding.userIdInput.editText!!
        userPassword = binding.userPasswordInput.editText!!
        signInButton = binding.signinButton
        signUpButton = binding.signupButton

        signInButton.setOnClickListener {
            // do sign in logic
            Log.d(TAG,"sign in clicked")
            var user = User(Integer(0),userName.text.toString(),userPassword.text.toString(),"")

            lifecycleScope.launch {
                // Main
                val valid = sharedViewModel.signin(user)
                if (valid){
                    sharedViewModel.fetchShops()
                    val action = LoginPageDirections.actionLoginPageToSelectPrimaryShop()
                    view.findNavController().navigate(action)
                    Toast.makeText(getActivity(), "로그인 성공", Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"${user.id} logged in")
                }else{
                    Toast.makeText(getActivity(), "로그인 실패하셨습니다. 아이디 패스워드를 확인하세요.", Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"log in failed ${user.id}")
                }
            }
        }

        signUpButton.setOnClickListener {
            val action = LoginPageDirections.actionLoginPageToSignUpPage()
            view.findNavController().navigate(action)
            Log.d(TAG,"sign up clicked")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()    // 좀비 쓰레드
    }


}