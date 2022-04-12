package com.fawaz.notesapp.ui.update

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.fawaz.notesapp.MainActivity
import com.fawaz.notesapp.NotesViewModel
import com.fawaz.notesapp.R
import com.fawaz.notesapp.data.entity.Notes
import com.fawaz.notesapp.databinding.FragmentUpdateBinding
import com.fawaz.notesapp.utils.ExtensionFunction.setActionBar
import com.fawaz.notesapp.utils.HelperFunction.parseToPriority
import com.fawaz.notesapp.utils.HelperFunction.spinnerListener
import com.google.android.material.appbar.MaterialToolbar
import java.text.SimpleDateFormat
import java.util.*

class UpdateFragment : Fragment() {

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding as FragmentUpdateBinding

    private val saveArgs: UpdateFragmentArgs by navArgs()

    private val updateViewModel by viewModels<NotesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // inisialisasi variabel dataBinding yg ada di XML
        binding.safeArgs = saveArgs

        setHasOptionsMenu(true)

        binding.apply {
            toolbarUpdate.setActionBar(requireActivity())
            spinnerPrioritiesUpdate.onItemSelectedListener = spinnerListener(context, binding.priorityIndicator)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_save, menu)
        val action = menu.findItem(R.id.menu_save)
        action.actionView.findViewById<AppCompatImageButton>(R.id.btn_save).setOnClickListener {
            updateNote()
        }
    }

    private fun updateNote() {
        binding.apply {
            val title = edtTitleUpdate.text.toString()
            val priority = spinnerPrioritiesUpdate.selectedItem.toString()
            val desc = edtDescriptionUpdate.text.toString()
            val calendar = Calendar.getInstance().time
            val date = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(calendar)

            val note = Notes(
                saveArgs.currentItem.id,
                title,
                parseToPriority(priority, context),
                desc,
                date
            )
            if(edtTitleUpdate.text.isEmpty() || edtDescriptionUpdate.text.isEmpty()) {
                edtTitleUpdate.error = "Please fill fields."
                edtDescriptionUpdate.error = "Please fill fields."
            }else{
                updateViewModel.updateNotes(note)
                val action = UpdateFragmentDirections.actionUpdateFragmentToDetailFragment(note)
                Toast.makeText(context, "Succesfully update note", Toast.LENGTH_LONG).show()
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

