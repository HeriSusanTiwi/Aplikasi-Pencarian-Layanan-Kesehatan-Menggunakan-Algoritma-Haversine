package com.herisusan.pencarianlayanankesehatan.activities

import android.app.ProgressDialog
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.herisusan.pencarianlayanankesehatan.R
import com.herisusan.pencarianlayanankesehatan.adapter.MainAdapter
import com.herisusan.pencarianlayanankesehatan.data.model.ModelResult
import com.herisusan.pencarianlayanankesehatan.viewmodel.ListResultViewModel
import kotlinx.android.synthetic.main.activity_location.*

class RumahSakitActivity : AppCompatActivity() {

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
        tvTitle.setText("Rumah Sakit Terdekat")

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
            ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                ListResultViewModel::class.java)
        intent.getStringExtra("lokasi")?.let { listResultViewModel.setHospitalLocation(it) }
        progressDialog.show()
        listResultViewModel.getHospitalLocation().observe(this) { modelResult: ArrayList<ModelResult> ->
            if (modelResult.size != 0) {
                mainAdapter.setResultAdapter(modelResult)
                progressDialog.dismiss()
                mainAdapter.onDetail = {
                    Toast.makeText(this, "Ini rs", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(
                    this,
                    "Opps, lokasi Rumah Sakit tidak di temukan", Toast.LENGTH_SHORT
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