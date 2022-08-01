package com.example.room_db_rv


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.example.room_db_rv.db.UserEntity
import androidx.lifecycle.ViewModelProviders


class MainActivity : AppCompatActivity(), RecyclerViewAdapter.RowClickListener {
// lateinit var allUsers: MutableLiveData<List<UserEntity>>
//
//    var allUsers: MutableLiveData<List<UserEntity>> = MutableLiveData()


    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    // var recyclerViewAdapter: RecyclerViewAdapter = RecyclerViewAdapter()

    lateinit var viewModel: MainActivityViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        val nameInput = findViewById<View>(R.id.nameInput) as TextView
        val emailInput = findViewById<View>(R.id.emailInput) as TextView
        val saveButton = findViewById<View>(R.id.saveButton) as Button


        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerViewAdapter = RecyclerViewAdapter(this@MainActivity)
            adapter = recyclerViewAdapter
            val divider = DividerItemDecoration(applicationContext, VERTICAL)
            addItemDecoration(divider)
        }
        viewModel = ViewModelProviders
            .of(this).get(MainActivityViewModel::class.java)

        viewModel.getAllUsersObservers().observe(this, Observer {

            recyclerViewAdapter.setListData(
                ArrayList(it)
            )
            recyclerViewAdapter.notifyDataSetChanged()
        })
        saveButton.setOnClickListener {
            val name = nameInput.text.toString()
            val email = emailInput.text.toString()

            if (saveButton.text.equals("Save")) {
                val user = UserEntity(0, name, email)
                viewModel.insertUserInfo(user)
            } else {
                val user =
                    UserEntity(nameInput.getTag(nameInput.id).toString().toInt(), name, email)
                viewModel.updateUserInfo(user)
                saveButton.setText("Save ")
            }

            nameInput.setText("")
            emailInput.setText("")
        }
    }

    override fun onDeleteUserClickListener(user: UserEntity) {
        viewModel.deleteUserInfo((user))
    }

    override fun onItemClickListener(user: UserEntity) {
        val nameInput = findViewById<View>(R.id.nameInput) as TextView
        val emailInput = findViewById<View>(R.id.emailInput) as TextView
        val saveButton = findViewById<View>(R.id.saveButton) as Button

        nameInput.setText(user.name)
        emailInput.setText(user.email)
        nameInput.setTag(nameInput.id, user.id)
        saveButton.setText("Update")
    }
}

class MainActivityBinding {

}

