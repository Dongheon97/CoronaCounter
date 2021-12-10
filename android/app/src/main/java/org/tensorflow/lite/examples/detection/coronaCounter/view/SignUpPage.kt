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
import org.tensorflow.lite.R
import org.tensorflow.lite.examples.detection.databinding.FragmentLoginPageBinding
import org.tensorflow.lite.examples.detection.databinding.FragmentSignUpPageBinding
import com.example.coronacounter.model.User
import com.example.coronacounter.viewModel.AppViewModel
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val TAG = "SignUpPageFragment"
/**
 * A simple [Fragment] subclass.
 * Use the [SignUpPage.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignUpPage : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentSignUpPageBinding? = null
    private val binding get() = _binding!!

    private lateinit var userName: TextView
    private lateinit var userPassword: TextView
    private lateinit var userKoreanName: TextView
    private lateinit var signUpButton: Button
    private lateinit var returnToSignInButton: Button

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
        _binding = FragmentSignUpPageBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
        // Inflate the layout for this fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userName = binding.userIdInput.editText!!
        userPassword = binding.userPasswordInput.editText!!
        userKoreanName = binding.userNameInput.editText!!
        returnToSignInButton = binding.returnToSignin
        signUpButton = binding.signupFinish

        returnToSignInButton.setOnClickListener {
            // do sign in logic
            Log.d(TAG,"return to sign in clicked")
            val action = SignUpPageDirections.actionSignUpPageToLoginPage()
            view.findNavController().navigate(action)
        }

        signUpButton.setOnClickListener {
            Log.d(TAG,"signUp clicked")
            val action = SignUpPageDirections.actionSignUpPageToLoginPage()
            lifecycleScope.launch {
                // Main
                val isNewUser = sharedViewModel.isNewUser(userName.text.toString())
                if (isNewUser){
                    Log.d(TAG,"회원가입 시도중")

                    val user = User(Integer(0),userName.text.toString(),userPassword.text.toString(),userKoreanName.text.toString())

                    val valid = sharedViewModel.addUser(user)
                    if (valid){
                        view.findNavController().navigate(action)
                        Toast.makeText(getActivity(), "회원가입 성공", Toast.LENGTH_SHORT).show();
                        Log.d(TAG,"${user.id} 회원가입 성공")
                    }else{
                        Toast.makeText(getActivity(), "회원가입 실패", Toast.LENGTH_SHORT).show();
                        Log.d(TAG,"네트워크 에러로 회원가입 실패 ${user.id}")
                    }
                }
                else{
                    Toast.makeText(getActivity(), "이미 존재하는 아이디입니다", Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"이미 존재하는 아이디입니다.")
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}