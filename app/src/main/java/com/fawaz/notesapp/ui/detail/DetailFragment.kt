package com.fawaz.notesapp.ui.detail

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fawaz.notesapp.MainActivity
import com.fawaz.notesapp.NotesViewModel
import com.fawaz.notesapp.R
import com.fawaz.notesapp.data.entity.Notes
import com.fawaz.notesapp.databinding.FragmentDetailBinding
import com.fawaz.notesapp.utils.ExtensionFunction.setActionBar

class DetailFragment : Fragment() {

    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding as FragmentDetailBinding

    private val navArgs by navArgs<DetailFragmentArgs>()

    private val detailViewModel by viewModels<NotesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        binding.safeArgs = navArgs
        binding.toolbarDetail.setActionBar(requireActivity())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_edit -> {
                val action = DetailFragmentDirections.actionDetailFragmentToUpdateFragment(
                    Notes(
                        navArgs.currentItem.id,
                        navArgs.currentItem.title,
                        navArgs.currentItem.priority,
                        navArgs.currentItem.description,
                        navArgs.currentItem.date
                    )
                )
                findNavController().navigate(action)
            }
            R.id.action_delete -> confirmDeleteNote()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmDeleteNote() {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete '${navArgs.currentItem.title}'?")
            .setMessage("Are you sure want to remove '${navArgs.currentItem.title}'?")
            .setPositiveButton("Yes") { _,_->
                detailViewModel.deleteNotes(navArgs.currentItem)
                findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
                Toast.makeText(requireContext(), "Succesfully delete note", Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("No") { _,_-> }
            .setNeutralButton("Cancel") { _, _->}
            .create()
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}