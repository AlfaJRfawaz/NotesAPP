package com.fawaz.notesapp.ui.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fawaz.notesapp.NotesViewModel
import com.fawaz.notesapp.R
import com.fawaz.notesapp.ViewModelFactory
import com.fawaz.notesapp.data.entity.Notes
import com.fawaz.notesapp.data.entity.Priority
import com.fawaz.notesapp.databinding.FragmentAddBinding
import com.fawaz.notesapp.utils.ExtensionFunction.setActionBar
import com.fawaz.notesapp.utils.HelperFunction
import com.fawaz.notesapp.utils.HelperFunction.parseToPriority
import java.text.SimpleDateFormat
import java.util.*

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding as FragmentAddBinding

//    private val addViewModel by viewModels<NotesViewModel>()

    private var _addViewModel: NotesViewModel? = null
    private val addViewModel get() = _addViewModel as NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        _addViewModel = activity?.let { obtainViewModel(it) }

        binding.toolbarAdd.setActionBar(requireActivity())

        binding.spinnerPriorities.onItemSelectedListener = HelperFunction.spinnerListener(context, binding.priorityIndicator)
    }

    private fun obtainViewModel(activity: FragmentActivity): NotesViewModel? {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[NotesViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_save, menu)
        val action = menu.findItem(R.id.menu_save)
        action.actionView.findViewById<AppCompatImageButton>(R.id.btn_save).setOnClickListener {
            insertNotes()
        }
    }

    private fun insertNotes() {
        binding.apply {
            val title = edtTitleAdd.text.toString()
            val priority = spinnerPriorities.selectedItem.toString()
            val description = edtDescriptionAdd.text.toString()
            val calendar = Calendar.getInstance().time
            val date = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(calendar)

            val note = Notes(
                0,
                title,
                parseToPriority(priority, context),
                description,
                date
            )
            if(edtTitleAdd.text.isEmpty() || edtDescriptionAdd.text.isEmpty()) {
                edtTitleAdd.error = "Please fill fields."
                edtDescriptionAdd.error = "Please fill fields."
            }else{
                addViewModel.insertData(note)
                Toast.makeText(context, "Succesfully add note", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_addFragment_to_homeFragment)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()

    }
}
