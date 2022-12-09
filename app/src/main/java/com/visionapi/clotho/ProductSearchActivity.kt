/**
 * Copyright 2021 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.visionapi.clotho

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.visionapi.clotho.api.ProductSearchAPIClient
import com.visionapi.clotho.api.ProductSearchResult
import com.visionapi.clotho.databinding.ActivityProductSearchBinding
import kotlinx.android.synthetic.main.activity_product_search.*
import kotlinx.android.synthetic.main.activity_registration.*
import java.util.*

data class SaveDataModel(
    var imageuri: String? = null,
    var amazonLink: String? = null
)

class ProductSearchActivity : AppCompatActivity() {

    companion object {
        const val TAG = "ProductSearchActivity"
        const val CROPPED_IMAGE_FILE_NAME = "MLKitCroppedFile_"
        const val REQUEST_TARGET_IMAGE_PATH = "REQUEST_TARGET_IMAGE_PATH"

        val openURL = Intent(android.content.Intent.ACTION_VIEW)

    }


    private lateinit var viewBinding: ActivityProductSearchBinding
    private lateinit var apiClient: ProductSearchAPIClient



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityProductSearchBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initViews()
        //amazonLink = "http://www.amazon.com/gp/mas/dl/android?s=white%20shoe%20mens"

        // Receive the query image and show it on the screen
        intent.getStringExtra(REQUEST_TARGET_IMAGE_PATH)?.let { absolutePath ->
            viewBinding.ivQueryImage.setImageBitmap(BitmapFactory.decodeFile(absolutePath))
        }

        // Initialize an API client for Vision API Product Search
        apiClient = ProductSearchAPIClient(this)

        tvRedirect.setOnClickListener {
            Toast.makeText(this, "clicked on redirect.", Toast.LENGTH_SHORT).show()
            openURL.data =  Uri.parse(GlobalVars.AmazonLink)
            startActivity(openURL)
        }

        btnRetry.setOnClickListener {
            startActivity(Intent(this, ProductSearchAPIClient::class.java))
        }

    }

    private fun initViews() {
        // Setup RecyclerView
        with(viewBinding.recyclerView) {
            setHasFixedSize(true)
            adapter = ProductSearchAdapter()
            layoutManager =
                LinearLayoutManager(
                    this@ProductSearchActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )
        }

        // Events
        viewBinding.btnSearch.setOnClickListener {
            // Display progress
            viewBinding.progressBar.visibility = View.VISIBLE
            viewBinding.btnSearch.visibility = View.GONE
            (viewBinding.ivQueryImage.drawable as? BitmapDrawable)?.bitmap?.let {
                searchByImage(it)
            }
            viewBinding.btnSave.visibility = View.VISIBLE
        }

        viewBinding.btnSave.setOnClickListener {

            val savedData = SaveDataModel(GlobalVars.uri.toString(), GlobalVars.AmazonLink)
            GlobalVars.AmazonLink = null

            val databaseReference = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://clotho-a9c47-default-rtdb.firebaseio.com/")

            val timeEpoch = getTimeEpoch()

            databaseReference.child("users").child(GlobalVars.userNameTxt_Global)
                .child("Saved Searches").child(timeEpoch).setValue(savedData)
                .addOnCompleteListener {
                    Toast.makeText(this, "data insert success", Toast.LENGTH_SHORT)
                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG)
                }


            // will come back to later in tutorial
            databaseReference.child("users")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        // check if username and password exist in firebase database

                        //Get data in datasnapshot
                        for (dsp in snapshot.child(GlobalVars.userNameTxt_Global)
                            .child("Saved Searches").children) {
                            if (GlobalVars.savedCount < 5) {
                                GlobalVars.savedData.add(dsp)

                                GlobalVars.savedCount++
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
            Toast.makeText(this, GlobalVars.savedData.get(GlobalVars.savedCount-1).child("imageuri").value.toString(), Toast.LENGTH_LONG).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }
    }

    private fun getTimeEpoch(): String {
        return Date().time.toString();
    }

    /**
     * Use Product Search API to search with the given query image
     */
    private fun searchByImage(queryImage: Bitmap) {
        apiClient.annotateImage(queryImage)
            .addOnSuccessListener { showSearchResult(it) }
            .addOnFailureListener { error ->
                Log.e(TAG, "Error calling Vision API Product Search.", error)
                showErrorResponse(error.localizedMessage)
            }
    }

    /**
     * Show search result.
     */
    private fun showSearchResult(result: List<ProductSearchResult>) {
        viewBinding.progressBar.visibility = View.GONE
        viewBinding.tvSearchResults.visibility = View.VISIBLE
        viewBinding.searchDivider.visibility = View.VISIBLE
        viewBinding.searchDivider2.visibility = View.VISIBLE
        viewBinding.tvRedirect.visibility = View.VISIBLE

        // Update the recycler view to display the search result.
        (viewBinding.recyclerView.adapter as? ProductSearchAdapter)?.submitList(
            result
        )
    }

    /**
     * Show Error Response
     */
    private fun showErrorResponse(message: String?) {
        viewBinding.progressBar.visibility = View.GONE
        // Show the error when calling API.
        Toast.makeText(this, "Error: $message", Toast.LENGTH_SHORT).show()
    }


}

/**
 * Adapter RecyclerView
 */
class ProductSearchAdapter :
    ListAdapter<ProductSearchResult, ProductSearchAdapter.ProductViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<ProductSearchResult>() {
            override fun areItemsTheSame(
                oldItem: ProductSearchResult,
                newItem: ProductSearchResult
            ) = oldItem.imageId == newItem.imageId && oldItem.imageUri == newItem.imageUri

            override fun areContentsTheSame(
                oldItem: ProductSearchResult,
                newItem: ProductSearchResult
            ) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProductViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
    )

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    /**
     * ViewHolder to hold the data inside
     */
    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /**
         * Bind data to views
         */
        @SuppressLint("SetTextI18n")
        fun bind(product: ProductSearchResult) {
            with(itemView) {
                findViewById<TextView>(R.id.tvProductScore).text = String.format("Similarity Score: %.2f", (product.score * 100)) + "%" //"Similarity score: ${ceil(product.score * 100)}%"
                if (GlobalVars.AmazonLink == null){
                    var delimiter = "category - "

                    var size: String = String()
                    if (product.label.split(delimiter)[1] == "shoe"){
                        size = GlobalVars.shoeSize
                    } else if (product.label.split(delimiter)[1] == "dress") {
                        size = GlobalVars.topSize
                    }

                    GlobalVars.AmazonLink =
                        "http://www.amazon.com/gp/mas/dl/android?s=" + GlobalVars.itemColor + "%20" + GlobalVars.genderTxt_Global + "%20" + product.label.split(delimiter)[1] + "%20" + "Size =" + size
                }

                //findViewById<TextView>(R.id.tvProductLabel).text = "${product.label}"
                // Show the image using Glide
                Glide.with(itemView).load(product.imageUri).into(findViewById(R.id.ivProduct))
            }
        }
    }
}
