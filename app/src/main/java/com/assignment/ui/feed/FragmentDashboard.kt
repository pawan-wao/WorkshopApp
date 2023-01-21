package com.assignment.ui.feed

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assignment.R
import com.assignment.data.database.DbHelper
import com.assignment.databinding.FragmentDashboardBinding
import com.assignment.repository.DatabaseRepository

class FragmentDashboard : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences: SharedPreferences? =
            activity?.getSharedPreferences("SESSION", Context.MODE_PRIVATE)
        val isUser = sharedPreferences?.getBoolean("USER", false) == true

        if (!isUser) {
            findNavController().popBackStack(R.id.fragmentDashboard,false)
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_fragmentDashboard_to_fragmentLogin)
        }
        val dbHelper = DbHelper(requireContext())
        val repository = DatabaseRepository(dbHelper)
        setUpRecyclerView(repository)
        binding.goToWorkShop.setOnClickListener {
            findNavController().popBackStack(R.id.fragmentDashboard,false)
            Navigation.findNavController(binding.root).navigate(R.id.action_fragmentDashboard_to_fragmentWorkshops)
        }
        binding.btnDelete.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            builder.setCancelable(false)
            builder.setTitle("Reset")
            builder.setMessage("Are you sure you want to delete all the applied workshops?")
            builder.setPositiveButton(
                "Yes"
            ) { dialog, _ ->
                run {
                    repository.deleteAppliedWorkshops()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        parentFragmentManager.beginTransaction().detach(this).commitNow();
                        parentFragmentManager.beginTransaction().attach(this).commitNow();
                    } else {
                        parentFragmentManager.beginTransaction().detach(this).attach(this).commit();
                    }
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
    }

    private fun setUpRecyclerView(repository: DatabaseRepository) {
        if (repository.appliedWorkshops().size > 0) {
            binding.recyclerView.visibility = View.VISIBLE
            val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
            binding.recyclerView.layoutManager = layoutManager
            val adapter = WorkshopAdapter(repository.appliedWorkshops(), object : ViewListeners {
                override fun onApply(btn: Button, id: Int) {
                }
            })
            binding.recyclerView.adapter = adapter
        } else {
            binding.tvEmpty.visibility = View.VISIBLE
        }
    }

}