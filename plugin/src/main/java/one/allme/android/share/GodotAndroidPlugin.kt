package one.allme.android.share

//import org.godotengine.godot.plugin.SignalInfo

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import org.godotengine.godot.Godot
import org.godotengine.godot.plugin.GodotPlugin
import org.godotengine.godot.plugin.UsedByGodot
import java.io.File


class GodotAndroidPlugin(godot: Godot): GodotPlugin(godot) {

    companion object {
        //        val TAG: String = BuildConfig.GODOT_PLUGIN_NAME
        const val TAG: String = "GodotAndroidShare"
    }

    //    override fun getPluginName() = BuildConfig.GODOT_PLUGIN_NAME
    override fun getPluginName() = "GodotAndroidShare"

    override fun getPluginMethods(): MutableList<String> {
        return mutableListOf("helloWorld", "shareText")
    }


//  Not use signals now ...
//    private val ShareError = SignalInfo("error")
//    private val ShareCompleted = SignalInfo("completed")
//
//    override fun getPluginSignals(): Set<SignalInfo> {
//        Log.i(TAG, "Registering plugin signals")
//        return setOf(
//            ShareError,
//            ShareCompleted,
//        )
//    }


    @UsedByGodot
    private fun shareText(title: String?, subject: String?, text: String?) {
        Log.d(TAG, "shareText called")
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.setType("text/plain")
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        sharingIntent.putExtra(Intent.EXTRA_TEXT, text)
        activity!!.startActivity(Intent.createChooser(sharingIntent, title))
    }

    @UsedByGodot
    fun sharePic(path: String, title: String?, subject: String?, text: String?) {
        Log.d(TAG, "sharePic called")
        val f = File(path)
        val uri: Uri
        uri = try {
            FileProvider.getUriForFile(activity!!, activity!!.packageName, f)
        } catch (e: IllegalArgumentException) {
            Log.e(TAG, "The selected file can't be shared: $path")
            return
        }
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("image/*")
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        activity!!.startActivity(Intent.createChooser(shareIntent, title))
    }

}

