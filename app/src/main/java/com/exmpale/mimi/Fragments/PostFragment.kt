package com.exmpale.mimi.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.exmpale.mimi.Adapters.GalleryAdapter
import com.exmpale.mimi.Models.GalleryModel
import com.exmpale.mimi.R
import com.exmpale.mimi.databinding.FragmentPostBinding


class PostFragment : Fragment() {

    val binding by lazy { FragmentPostBinding.inflate(layoutInflater) }
//    private val GalleryList = ArrayList<GalleryModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

//        setAllPost()
//        val adapter = GalleryAdapter(GalleryList)
//        binding.PostRecyclerview.adapter = adapter
//        binding.PostRecyclerview.setHasFixedSize(true)
//        binding.PostRecyclerview.layoutManager = GridLayoutManager(requireContext(),4)


        return binding.root
    }

//    private fun setAllPost() {
//        GalleryList.add(GalleryModel(R.drawable.image2))
//        GalleryList.add(GalleryModel(R.drawable.image3))
//        GalleryList.add(GalleryModel(R.drawable.image4))
//        GalleryList.add(GalleryModel(R.drawable.image5))
//        GalleryList.add(GalleryModel(R.drawable.image6))
//        GalleryList.add(GalleryModel(R.drawable.image7))
//        GalleryList.add(GalleryModel(R.drawable.image1))
//        GalleryList.add(GalleryModel(R.drawable.image8))
//        GalleryList.add(GalleryModel(R.drawable.image9))
//        GalleryList.add(GalleryModel(R.drawable.image10))
//        GalleryList.add(GalleryModel(R.drawable.profile1))
//        GalleryList.add(GalleryModel(R.drawable.profile2))
//        GalleryList.add(GalleryModel(R.drawable.profile3))
//        GalleryList.add(GalleryModel(R.drawable.profile4))
//        GalleryList.add(GalleryModel(R.drawable.profile5))
//        GalleryList.add(GalleryModel(R.drawable.profile6))
//        GalleryList.add(GalleryModel(R.drawable.profile7))
//        GalleryList.add(GalleryModel(R.drawable.profile8))
//        GalleryList.add(GalleryModel(R.drawable.post1))
//        GalleryList.add(GalleryModel(R.drawable.post2))
//        GalleryList.add(GalleryModel(R.drawable.post3))
//        GalleryList.add(GalleryModel(R.drawable.post4))
//        GalleryList.add(GalleryModel(R.drawable.post5))
//        GalleryList.add(GalleryModel(R.drawable.post6))
//        GalleryList.add(GalleryModel(R.drawable.image9))
//        GalleryList.add(GalleryModel(R.drawable.image10))
//        GalleryList.add(GalleryModel(R.drawable.profile1))
//        GalleryList.add(GalleryModel(R.drawable.profile2))
//        GalleryList.add(GalleryModel(R.drawable.profile3))
//        GalleryList.add(GalleryModel(R.drawable.profile4))
//
//    }

}