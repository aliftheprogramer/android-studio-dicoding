@file:Suppress("DEPRECATION")

package com.example.myapplication.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.response.Event
import com.example.myapplication.databinding.FragmentEventDetailBinding

class EventDetailFragment : Fragment() {

    private var _binding: FragmentEventDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading(true) // Show loading indicator when view is created

        val event = arguments?.getParcelable<Event>("event")
        event?.let {
            bindEventDetails(it)
            showLoading(false) // Hide loading indicator after data is bound
        }


        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun bindEventDetails(event: Event) {
        Glide.with(this)
            .load(event.imageLogo)
            .error(R.drawable.ic_error)
            .into(binding.imageLogo)

        binding.eventName.text = event.name
        binding.ownerName.text = "Organizer: ${event.ownerName}"
        binding.beginTime.text = "Time: ${event.beginTime}"
        binding.quota.text = "Quota: ${event.quota - event.registrants}"
//        binding.description.text = HtmlCompat.fromHtml(event.description, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
        binding.description.text = HtmlCompat.fromHtml(event.description ?: "No description available", HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.linkButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.link))
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}