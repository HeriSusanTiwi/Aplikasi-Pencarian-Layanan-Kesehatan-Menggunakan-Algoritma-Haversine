package com.herisusan.pencarianlayanankesehatan.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.herisusan.pencarianlayanankesehatan.R
import com.herisusan.pencarianlayanankesehatan.adapter.MainAdapter
import com.herisusan.pencarianlayanankesehatan.data.model.ModelResult
import com.herisusan.pencarianlayanankesehatan.databinding.ActivitySubMenuBinding
import com.herisusan.pencarianlayanankesehatan.viewmodel.ListResultViewModel
import kotlinx.android.synthetic.main.activity_location.rvListResult
import kotlinx.android.synthetic.main.activity_location.tvTitle

class SubMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySubMenuBinding
    lateinit var mainAdapter: MainAdapter
    lateinit var listResultViewModel: ListResultViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set data adapter
        mainAdapter = MainAdapter(
            this,
            intent.getDoubleExtra("lat",0.0)
            ,intent.getDoubleExtra("long",0.0)
        )
        binding.rvListResult.setLayoutManager(LinearLayoutManager(this))
        binding.rvListResult.setAdapter(mainAdapter)
        binding.rvListResult.setHasFixedSize(true)
        //viewmodel
        listResultViewModel =
            ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                ListResultViewModel::class.java)
        showData(intent.getStringExtra("label")!!,intent.getStringExtra("lokasi")!!)

    }

    private fun showData(label : String, lokasi: String){
        when (label){
            "Klinik" -> {
                binding.tvTitle.setText("Apotek Terdekat")
                lokasi.let { listResultViewModel.setClinicLocation(it) }
                binding.loading.isVisible = true
                listResultViewModel.getClinicLocation().observe(this) { modelResult: ArrayList<ModelResult> ->
                    if (modelResult.size != 0) {
                        mainAdapter.setResultAdapter(modelResult)
                        binding.loading.isVisible = false
                        mainAdapter.onDetail = {
                            val intent = Intent(this@SubMenuActivity, DetailLocationActivity::class.java)
                            intent.putExtra("id", it.placeId)
                            intent.putExtra("lat",it.modelGeometry?.modelLocation?.lat ?: 0.0)
                            intent.putExtra("long",it.modelGeometry?.modelLocation?.lng ?: 0.0)
                            startActivity(intent)
                        }
                    } else {
                        binding.loading.isVisible = false
                        Toast.makeText(
                            this,
                            "Opps, lokasi Apotek tidak di temukan", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            "Rumah Sakit" -> {
                binding.tvTitle.setText("Rumah Sakit Terdekat")
                lokasi.let { listResultViewModel.setHospitalLocation(it) }
                binding.loading.isVisible = true
                listResultViewModel.getHospitalLocation().observe(this) { modelResult: ArrayList<ModelResult> ->
                    if (modelResult.size != 0) {
                        mainAdapter.setResultAdapter(modelResult)
                        binding.loading.isVisible = false
                        mainAdapter.onDetail = {
                            val intent = Intent(this@SubMenuActivity, DetailLocationActivity::class.java)
                            intent.putExtra("id", it.placeId)
                            intent.putExtra("lat",it.modelGeometry?.modelLocation?.lat ?: 0.0)
                            intent.putExtra("long",it.modelGeometry?.modelLocation?.lng ?: 0.0)
                            startActivity(intent)
                        }
                    } else {
                        binding.loading.isVisible = false
                        Toast.makeText(
                            this,
                            "Opps, lokasi Rumah Sakit tidak di temukan", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            else -> {
                binding.tvTitle.setText("Dokter Terdekat")
                lokasi.let { listResultViewModel.setDoctorLocation(it) }
                binding.loading.isVisible = true
                listResultViewModel.getDoctorLocation().observe(this) { modelResult: ArrayList<ModelResult> ->
                    if (modelResult.size != 0) {
                        mainAdapter.setResultAdapter(modelResult)
                        binding.loading.isVisible = false
                        mainAdapter.onDetail = {
                            val intent = Intent(this@SubMenuActivity, DetailLocationActivity::class.java)
                            intent.putExtra("id", it.placeId)
                            intent.putExtra("lat",it.modelGeometry?.modelLocation?.lat ?: 0.0)
                            intent.putExtra("long",it.modelGeometry?.modelLocation?.lng ?: 0.0)
                            startActivity(intent)
                        }
                    } else {
                        binding.loading.isVisible = false
                        Toast.makeText(
                            this,
                            "Opps, lokasi Dokter tidak di temukan", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    }
}