import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import com.example.myapplication.R

class WhatsAppHelper {

    companion object {

        fun openWhatsAppChat(
            context: Context,
            phoneNumber: String?,
            message: String = ""
        ) {
            try {
                if (phoneNumber.isNullOrBlank()) {
                    Toast.makeText(context, context.getString(R.string.error_phone_missing), Toast.LENGTH_SHORT).show()
                    return
                }

                if (phoneNumber.isEmpty()) {
                    Toast.makeText(context, context.getString(R.string.error_phone_invalid), Toast.LENGTH_SHORT).show()
                    return
                }

                if (!isWhatsAppInstalled(context)) {
                    Toast.makeText(context, context.getString(R.string.error_whatsapp_not_installed), Toast.LENGTH_LONG).show()
                    openPlayStoreForWhatsApp(context)
                    return
                }

                val url = if (message.isNotEmpty()) {
                    "https://api.whatsapp.com/send?phone=$phoneNumber&text=${Uri.encode(message)}"
                } else {
                    "https://api.whatsapp.com/send?phone=$phoneNumber"
                }

                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                intent.setPackage("com.whatsapp")

                if (intent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(intent)
                } else {
                    val fallbackIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(fallbackIntent)
                }

            } catch (e: Exception) {
                Toast.makeText(context, context.getString(R.string.error_opening_whatsapp, e.localizedMessage), Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }

        fun openWhatsApp(context: Context) {
            try {
                if (!isWhatsAppInstalled(context)) {
                    Toast.makeText(context, context.getString(R.string.error_whatsapp_not_installed), Toast.LENGTH_LONG).show()
                    return
                }

                val intent = context.packageManager.getLaunchIntentForPackage("com.whatsapp")
                if (intent != null) {
                    context.startActivity(intent)
                } else {
                    Toast.makeText(context, context.getString(R.string.error_cannot_open_whatsapp), Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                Toast.makeText(context, context.getString(R.string.error_opening_whatsapp, e.localizedMessage), Toast.LENGTH_SHORT).show()
            }
        }

        private fun isWhatsAppInstalled(context: Context): Boolean {
            return try {
                context.packageManager.getPackageInfo("com.whatsapp", 0)
                true
            } catch (e: PackageManager.NameNotFoundException) {
                false
            }
        }

        private fun openPlayStoreForWhatsApp(context: Context) {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.whatsapp"))
                context.startActivity(intent)
            } catch (e: Exception) {
                val intent = Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.whatsapp"))
                context.startActivity(intent)
            }
        }
    }
}
