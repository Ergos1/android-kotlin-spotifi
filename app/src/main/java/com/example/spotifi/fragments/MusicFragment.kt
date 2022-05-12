package com.example.spotifi.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.data.remote.models.MusicApi
import com.example.data.remote.providers.implementations.MusicProviderImpl
import com.example.data.remote.providers.interfaces.MusicProvider
import com.example.domain.repositories.implementations.MusicRepositoryImpl
import com.example.domain.repositories.interfaces.MusicRepository
import com.example.domain.view_models.MusicViewModel
import com.example.domain.view_models.MusicViewModelFactory
import com.example.spotifi.R
import com.example.spotifi.adapters.MusicListAdapter
import com.example.spotifi.adapters.MusicListSelectListener
import kotlinx.android.synthetic.main.fragment_music.*

class MusicFragment : Fragment(), MusicListSelectListener {

    private lateinit var adapter: MusicListAdapter
    private lateinit var viewModel: MusicViewModel
    private lateinit var viewModelFactory: MusicViewModelFactory
    private lateinit var repository: MusicRepository
    private lateinit var provider: MusicProvider

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_music, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(activity)
        music_list.layoutManager = layoutManager
        adapter = MusicListAdapter(this)
        music_list.adapter = adapter

        provider = MusicProviderImpl(view.context)
        repository = MusicRepositoryImpl(provider)
        viewModelFactory = MusicViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MusicViewModel::class.java]

        viewModel.getMusicList()

        viewModel.musicList.observe(viewLifecycleOwner) { musicList ->
            adapter.submitList(musicList)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getMusic(music: MusicApi, musics: ArrayList<MusicApi>) {
        val musicIds = ArrayList<Int>()
        musics.forEach { musicItem ->
            musicIds.add(musicItem.id)
        }
        var bundle = bundleOf("id" to music.id, "ids" to musicIds)
        findNavController().navigate(R.id.action_mainFragment_to_musicPlayerFragment, bundle)
    }
}