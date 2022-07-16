package com.example.memories.utils

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri

object BitmapHelper
{

    fun getBitmapFromUri(ctx: Activity, uri: Uri): Bitmap?
    {
        val targetW = 600
        val targetH = 600
        val bmOptions = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeStream(ctx.contentResolver.openInputStream(uri), null, bmOptions)
        val photoW = bmOptions.outWidth
        val photoH = bmOptions.outHeight
        val scaleFactor = Math.min(photoW / targetW, photoH / targetH)
        bmOptions.inJustDecodeBounds = false
        bmOptions.inSampleSize = scaleFactor
        val  decodedBitmap  = BitmapFactory.decodeStream(
            ctx.contentResolver
                .openInputStream(uri), null, bmOptions
        )

        return decodedBitmap
     //   return getResizedBitmap(decodedBitmap!!,500)

    } // decodeBitmap URI



     private fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap?
    {
        var width = image.width
        var height = image.height
        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1)
        {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else
        {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)
    }




}