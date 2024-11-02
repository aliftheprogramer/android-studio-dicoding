package com.example.myapplication.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.database.FavoriteEvent
import com.example.myapplication.data.response.Event
import com.example.myapplication.databinding.FragmentEventDetailBinding
import com.example.myapplication.ui.favorite.FavoriteEventViewModel
import com.example.myapplication.util.ToastUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EventDetailFragment : Fragment() {

    private var _binding: FragmentEventDetailBinding? = null
    private val binding get() = _binding!!
    private val favoriteEventViewModel: FavoriteEventViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val event: Event? = arguments?.getParcelable("event")
        Log.d("EventDetailFragment", "Event: $event")
        event?.let { bindEventDetails(it) }

        binding.loveButton.setOnClickListener { button ->
            button.isEnabled = false // Disable the button to prevent multiple clicks
            event?.let { event ->
                lifecycleScope.launch {
                    val isFavorite = favoriteEventViewModel.isFavoriteEvent(event.id)
                    if (!isFavorite) {
                        val newFavoriteEvent = FavoriteEvent(
                            id = event.id,
                            eventName = event.name,
                            eventSummary = event.summary,
                            imageLogo = event.imageLogo,
                            eventDate = event.beginTime,
                            isFavorite = true,
                            createdAt = System.currentTimeMillis()
                        )
                        favoriteEventViewModel.insertFavoriteEvent(newFavoriteEvent)
                        ToastUtil.showToast(requireContext(), "Event added to favorites")
                    } else {
                        favoriteEventViewModel.deleteFavoriteEventById(event.id)
                        ToastUtil.showToast(requireContext(), "Event removed from favorites")
                    }
                    updateFavoriteStatus(event.id)
                    button.isEnabled = true // Re-enable the button after the action is completed
                }
            }
        }


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

    private fun updateFavoriteStatus(eventId: Int) {
        favoriteEventViewModel.getFavoriteEventById(eventId).observe(viewLifecycleOwner, Observer { favoriteEvent ->
            if (favoriteEvent != null) {
                binding.loveButton.setImageResource(R.drawable.baseline_favorite_24) // Set a different icon
            } else {
                binding.loveButton.setImageResource(R.drawable.baseline_favorite_444) // Set a different icon
            }
        })
    }
}