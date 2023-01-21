package com.assignment.ui.feed

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assignment.R
import com.assignment.data.database.DbHelper
import com.assignment.databinding.FragmentWorkshopsBinding
import com.assignment.repository.DatabaseRepository
import com.assignment.util.Utils
import com.google.firebase.auth.FirebaseAuth


class FragmentWorkshops : Fragment() {
    private var _binding: FragmentWorkshopsBinding? = null
    private val binding get() = _binding!!

    private lateinit var progressDialog: Dialog

    private var isUser: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWorkshopsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dbHelper = DbHelper(requireContext())
        val repository = DatabaseRepository(dbHelper)

        val sharedPreferences: SharedPreferences? =
            activity?.getSharedPreferences("SESSION", MODE_PRIVATE)
        isUser = sharedPreferences?.getBoolean("USER", false) == true

        val notFirstTime = sharedPreferences?.getBoolean("FIRST_TIME", false)
        Log.e("AVAILABLEWORKSHOP", notFirstTime.toString())
        if (!notFirstTime!!) {
            repository.addWorkShops(Utils.workShop())
            sharedPreferences.edit().putBoolean("FIRST_TIME", true).apply()
        }
        initView(repository)
        binding.goToDashboard.setOnClickListener {
            findNavController().popBackStack(R.id.fragmentWorkshops,false)
            Navigation.findNavController(binding.root).navigate(R.id.action_fragmentWorkshops_to_fragmentDashboard)
        }
        binding.btnLogout.setOnClickListener {
            showLogOutPrompt()
        }
    }

    private fun initView(repository: DatabaseRepository) {
        context?.let { progressDialog = Utils.dialog(it) }
        setUpRecyclerView(repository)
    }

    private fun setUpRecyclerView(repository: DatabaseRepository) {

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        binding.recyclerviewWorkshops.layoutManager = layoutManager

        val list = repository.getWorkShops()
        Log.i("VIBHUTI", "setUpRecyclerView: $list")
        val adapter = WorkshopAdapter(repository.getWorkShops(), object : ViewListeners {
            override fun onApply(btn: Button, id: Int) {
                if (isUser) {
                    btn.isEnabled = false
                    btn.text = "Applied"
                    repository.apply(id)
                    Toast.makeText(requireContext(),"Applied to workshop", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(),"Sign in to apply for workshop", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack(R.id.fragmentWorkshops,false)
                    Navigation.findNavController(binding.root).navigate(R.id.action_fragmentWorkshops_to_fragmentLogin)
                }
            }
        })

        binding.recyclerviewWorkshops.adapter = adapter
        progressDialog.dismiss()
    }
    private fun showLogOutPrompt() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setCancelable(false)
        builder.setTitle("Logout")
        builder.setMessage("Are you sure you want to logout?")
        builder.setPositiveButton(
            "Yes"
        ) { dialog, p1 ->
            run {
                logout()
                dialog.dismiss()
            }
        }
        builder.setNegativeButton(
            "No"
        ) { dialog, p1 ->
            run {
                dialog.dismiss()
            }
        }

        DialogInterface.OnCancelListener { }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        val dbHelper = DbHelper(requireContext())
        val repository = DatabaseRepository(dbHelper)
        repository.deleteAppliedWorkshops()
        val sharedPreferences: SharedPreferences? = activity?.getSharedPreferences("SESSION", Context.MODE_PRIVATE)
        sharedPreferences?.edit()?.putBoolean("USER", false)?.apply()
        findNavController().navigate(R.id.action_fragmentWorkshops_to_fragmentLogin)
    }
}