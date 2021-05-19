package com.example.pokeapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.pokeapp.adapters.FavoriteListAdapter
import com.example.pokeapp.databinding.FragmentFavoritesBinding
import com.example.pokeapp.viewmodels.FavoriteViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val disposable = io.reactivex.disposables.CompositeDisposable()

    private val viewModel: FavoriteViewModel by viewModels()
    private val adapter = FavoriteListAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.favoriteListRecyclerView.adapter=adapter

        disposable.add(
            viewModel.getFavorites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    if (it.size == 0) {
                        binding.emptyStateWidget.visibility = View.VISIBLE
                        binding.favoriteListRecyclerView.visibility = View.INVISIBLE
                    }else{
                        binding.emptyStateWidget.visibility = View.INVISIBLE
                        binding.favoriteListRecyclerView.visibility = View.VISIBLE
                        adapter.favoriteList = it
                    }
                }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.clear()
        _binding = null
    }
}