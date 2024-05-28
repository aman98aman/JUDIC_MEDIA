package com.exmpale.mimi.Activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.exmpale.mimi.Adapters.SearchAdapter
import com.exmpale.mimi.Adapters.UserListAdapter
import com.exmpale.mimi.Models.SearchModel
import com.exmpale.mimi.Models.UserModel
import com.exmpale.mimi.R
import com.exmpale.mimi.databinding.ActivitySearchBinding
import com.exmpale.mimi.utils.User_Node
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class SearchActivity : AppCompatActivity() {

    val binding by lazy { ActivitySearchBinding.inflate(layoutInflater) }
    private lateinit var userList: ArrayList<UserModel>
    private lateinit var adapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        userList = ArrayList()

        binding.btSearchBack.setOnClickListener {
            startActivity(Intent(this@SearchActivity, MainActivity::class.java))
        }

        binding.serachRecycler.layoutManager = LinearLayoutManager(this)

        Firebase.firestore.collection(User_Node).get().addOnSuccessListener {
            userList.clear()
            for(doc in it.documents) {
                val user = doc.toObject<UserModel>()
                userList.add(user!!)
            }
            adapter = UserListAdapter(this, userList)
            binding.serachRecycler.adapter = adapter

        }

        searchView()

    }

    private fun searchView() {

        binding.searchview.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filteredList(newText)
                return true

            }

        })

    }

    private fun filteredList(newText: String?) {
        val filteredList = ArrayList<UserModel>()
        for (user in userList) {
            if (user.name!!.contains(newText.orEmpty(), ignoreCase = true))
                filteredList.add(user)

        }
        adapter.newList(filteredList)
        adapter.notifyDataSetChanged()
    }

}