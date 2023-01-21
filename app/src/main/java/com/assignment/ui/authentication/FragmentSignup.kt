package com.assignment.ui.authentication

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.assignment.R
import com.assignment.databinding.FragmentSignupBinding
import com.assignment.util.Utils
import com.google.firebase.auth.FirebaseAuth


class FragmentSignup : Fragment() {

    private lateinit var binding : FragmentSignupBinding
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences: SharedPreferences? =
            activity?.getSharedPreferences("SESSION", Context.MODE_PRIVATE)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.bLogin.setOnClickListener {
            findNavController().navigate(R.id.fragmentLogin)
        }

        binding.bSignUp.setOnClickListener {
            val progressBar = Utils.dialog(requireContext())
            progressBar.show()
            val email = binding.etEmail.text.toString()
            val pass = binding.etPass.text.toString()
            val confirmPass = binding.etPass2.text.toString()

            if(email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()){
                if(pass == confirmPass){
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if(it.isSuccessful){
                            sharedPreferences?.edit()?.putBoolean("USER",true)?.apply()
                            Toast.makeText(this.context,"Successfully Signed Up, Now you can apply",Toast.LENGTH_SHORT).show()
                            progressBar.dismiss()
                            findNavController().popBackStack(R.id.fragmentSignup,false)
                            Navigation.findNavController(binding.root).navigate(R.id.action_fragmentSignup_to_fragmentDashboard)
                        }
                        else{
                            Toast.makeText(this.context, it.exception.toString(), Toast.LENGTH_SHORT).show()
                            progressBar.dismiss()
                        }
                    }
                }
                else{
                    Toast.makeText(this.context, "Password did not matched", Toast.LENGTH_SHORT).show()
                    progressBar.dismiss()
                }
            }
            else{
                Toast.makeText(this.context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                progressBar.dismiss()
            }
        }
        binding.btbBack.setOnClickListener {
            findNavController().navigate(R.id.fragmentWorkshops)
        }
    }
}