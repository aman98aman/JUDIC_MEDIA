package com.exmpale.mimi.utils

import android.app.ProgressDialog
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

fun uploadImage(uri: Uri, folderName : String, progressDialog: ProgressDialog, callBack: (String?) -> Unit) {

    var imageUri : String? = null
    progressDialog.setTitle("Uploading .....")
    progressDialog.show()

    FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString()).putFile(uri).addOnSuccessListener { it ->

        it.storage.downloadUrl.addOnSuccessListener {

            imageUri = it.toString()
            progressDialog.dismiss()
            callBack(imageUri)
        }
    }.addOnProgressListener {

        val uploadedValue : Long = (it.bytesTransferred / it.totalByteCount)*100
        progressDialog.setTitle("Uploaded $uploadedValue %")

    }

}

fun uploadVideo(uri: Uri, folderName : String, progressDialog: ProgressDialog, callBack: (String?) -> Unit) {

    var videoUri : String? = null
    progressDialog.setTitle("Uploading .....")
    progressDialog.show()

    FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString()).putFile(uri).addOnSuccessListener { it ->

        it.storage.downloadUrl.addOnSuccessListener {

            videoUri = it.toString()
            progressDialog.dismiss()
            callBack(videoUri)

        }
    }.addOnProgressListener {

        val uploadedValue : Long = (it.bytesTransferred / it.totalByteCount)*100
        progressDialog.setTitle("Uploaded $uploadedValue %")

    }
}

