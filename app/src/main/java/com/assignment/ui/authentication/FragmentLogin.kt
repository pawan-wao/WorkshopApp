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
import com.assignment.databinding.FragmentLoginBinding
import com.assignment.util.Utils
import com.google.firebase.auth.FirebaseAuth

class FragmentLogin : Fragment() {
    private lateinit var binding : FragmentLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences: SharedPreferences? =
            activity?.getSharedPreferences("SESSION", Context.MODE_PRIVATE)
        firebaseAuth = FirebaseAuth.getInstance()
        val registered = FirebaseAuth.getInstance().currentUser

        if(registered != null){
            findNavController().navigate(R.id.fragmentDashboard)
        }

        binding.bSignUp.setOnClickListener {
            findNavController().popBackStack(R.id.fragmentLogin,false)
            Navigation.findNavController(binding.root).navigate(R.id.action_fragmentLogin_to_fragmentSignup)
        }
        binding.bLogin.setOnClickListener {
            val progressBar = Utils.dialog(requireContext())
            progressBar.show()
            val email = binding.etEmail.text.toString()
            val pass = binding.etPass.text.toString()

            if(email.isNotEmpty() && pass.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if(it.isSuccessful){
                        sharedPreferences?.edit()?.putBoolean("USER",true)?.apply()
                        Toast.makeText(this.context,"Successfully Logged In, Now you can apply",Toast.LENGTH_SHORT).show()
                        progressBar.dismiss()
                        findNavController().popBackStack(R.id.fragmentLogin,false)
                        Navigation.findNavController(binding.root).navigate(R.id.action_fragmentLogin_to_fragmentDashboard)
                    }
                    else{
                        Toast.makeText(this.context,"Incorrect Email or Password", Toast.LENGTH_SHORT).show()
                        progressBar.dismiss()
                    }
                }
            }
            else{
                Toast.makeText(this.context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                progressBar.dismiss()
            }
        }
        binding.btbBack.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentLogin_to_fragmentWorkshops)
        }
    }

}