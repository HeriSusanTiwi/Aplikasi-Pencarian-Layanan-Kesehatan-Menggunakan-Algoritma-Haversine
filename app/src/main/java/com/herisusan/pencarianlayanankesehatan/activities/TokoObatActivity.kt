package com.herisusan.pencarianlayanankesehatan.activities

import android.app.ProgressDialog
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import androidx.recyclerview.widget.LinearLayoutManager
import com.herisusan.pencarianlayanankesehatan.R
import com.herisusan.pencarianlayanankesehatan.adapter.MainAdapter
import com.herisusan.pencarianlayanankesehatan.data.model.ModelResult
import com.herisusan.pencarianlayanankesehatan.viewmodel.ListResultViewModel
import im.delight.android.location.SimpleLocation
import kotlinx.android.synthetic.main.activity_location.*
import java.util.ArrayList

class TokoObatActivity : AppCompatActivity() {

    lateinit var progressDialog: ProgressDialog
    lateinit var mainAdapter: MainAdapter
    lateinit var listResultViewModel: ListResultViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        setSupportActionBar(toolbar)
        assert(supportActionBar != null)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Mohon tunggu...")
        progressDialog.setCancelable(false)
        progressDialog.setMessage("sedang menampilkan lokasi")

        //set title
        tvTitle.setText("Toko Obat Terdekat")

        //set data adapter
        mainAdapter = MainAdapter(
            this,
            intent.getDoubleExtra("lat",0.0)
            ,intent.getDoubleExtra("long",0.0)
        )
        rvListResult.setLayoutManager(LinearLayoutManager(this))
        rvListResult.setAdapter(mainAdapter)
        rvListResult.setHasFixedSize(true)

        //viewmodel
        listResultViewModel =
            ViewModelProvider(this,NewInstanceFactory()).get(ListResultViewModel::class.java)
        intent.getStringExtra("lokasi")?.let { listResultViewModel.setDrugStoreLocation(it) }
        progressDialog.show()
        listResultViewModel.getDrugStoreLocation().observe(this) { modelResult: ArrayList<ModelResult> ->
            if (modelResult.size != 0) {
                mainAdapter.setResultAdapter(modelResult)
                progressDialog.dismiss()
            } else {
                Toast.makeText(
                    this,
                    "Opps, lokasi Toko Obat tidak di temukan",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}