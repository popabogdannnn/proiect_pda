package com.example.proiect_pda.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.example.proiect_pda.MainActivity
import com.example.proiect_pda.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_camera.*
import kotlinx.android.synthetic.main.fragment_camera.view.*
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [camera.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val FILE_NAME = "photo"
private const val TAKE_PICTURE_REQUEST_CODE = 30


class camera : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var take_photo_button: Button? = null
    private var image_view: ImageView? = null
    private lateinit var photo_file: File

    private lateinit var curr_activity: MainActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_camera, container, false)
        image_view = view.findViewById<ImageView>(R.id.image_view)
        take_photo_button = view.findViewById<Button>(R.id.take_picture)

        take_photo_button?.setOnClickListener {
            var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            curr_activity = activity as MainActivity

            photo_file = getPhotoFile(FILE_NAME)

            var file_provider = FileProvider.getUriForFile(curr_activity, "edu.stanford.rpkandey.fileprovider", photo_file)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, file_provider)
            if(intent.resolveActivity(curr_activity.packageManager) != null) {
                startActivityForResult(intent, TAKE_PICTURE_REQUEST_CODE)
            }
            else {
                Toast.makeText(curr_activity, "Camera could not be opened", Toast.LENGTH_SHORT).show()
            }
        }


        return view
    }

    private fun getPhotoFile(fileName: String): File {
        var storage_path = curr_activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storage_path)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == TAKE_PICTURE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            //val image_taken = data?.extras?.get("data") as Bitmap
            val image_taken = BitmapFactory.decodeFile(photo_file.absolutePath)
            Log.d("file_path", photo_file.absolutePath)



            image_view?.setImageBitmap(image_taken)
        }
        else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment camera.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            camera().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}