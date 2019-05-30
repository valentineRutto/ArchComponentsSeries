package com.example.background.workers

import android.content.Context
import android.graphics.BitmapFactory.decodeResource
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.background.R

/**
 * Created by ValentineRutto on 5/30/19 2:40 PM
 */
class BlurWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
	
	private val TAG by lazy { BlurWorker::class.java.simpleName }
	
	override fun doWork(): Result {
		val appContext = applicationContext
		
		makeStatusNotification("Blurring an image", appContext)
		return try {
			val picture = decodeResource(
				appContext.resources,
				R.drawable.test
			)
			
			val output = blurBitmap(picture, appContext)
			
			val outputUri = writeBitmapToFile(appContext, output)
			
			makeStatusNotification("Output is $outputUri", appContext)
			
			Result.success()
		} catch (throwable: Throwable) {
			Log.e(TAG, "Error appying blue", throwable)
			Result.failure()
		}
	}
	
}