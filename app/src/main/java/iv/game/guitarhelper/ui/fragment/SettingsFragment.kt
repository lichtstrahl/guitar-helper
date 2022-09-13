package iv.game.guitarhelper.ui.fragment

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import iv.game.guitarhelper.R
import iv.game.guitarhelper.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

    // Views
    private lateinit var audioRecordButton: MaterialButton

    // Permissions
    private var permissionAudioRecordGranted = false

    // Utils
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private var shownRationaleAudioRecord = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
            .binding()
        initClickListeners()

        permissionAudioRecordGranted = (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)
            .apply { audioRecordButton.togglePermission(this) }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            // TODO Debug toasts
            if (isGranted) {
                Toast.makeText(context, "Permission granted!", Toast.LENGTH_SHORT).show()
                audioRecordButton.enablePermission()
            } else {
                audioRecordButton.disablePermission()
                Toast.makeText(context, "Permission not granted ((", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDetach() {
        super.onDetach()

        requestPermissionLauncher.unregister()
    }

    // ---
    // PRIVATE
    // ---

    private fun View.binding() = this.apply {
        FragmentSettingsBinding.bind(this)
            .apply { audioRecordButton = this.permissionAudioRecord }
    }

    private fun initClickListeners() {
        audioRecordButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO)) {
                    showAudioRecordPermissionExplanationDialog()
                } else if (shownRationaleAudioRecord) {
                    showAudioRecordPermissionDeniedDialog()
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                }
            }
        }
    }

    /**
     * Показ диалога с объяснением причин, по которым нужно выдать разрешение
     */
    private fun showAudioRecordPermissionExplanationDialog() = AlertDialog.Builder(requireContext())
        .setMessage("Объяснение")
        .setPositiveButton("OK") { dialog, _ ->
            requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            shownRationaleAudioRecord = true
            dialog.dismiss()
        }
        .setNegativeButton("NO") { dialog, _ ->
            audioRecordButton.disablePermission()
            dialog.dismiss()
        }
        .show()


    /**
     * Показ диалога с оповещением, что приложение не будет работать без данного разрешения.
     * Отправка пользователя в экран настроек
     */
    private fun showAudioRecordPermissionDeniedDialog() = AlertDialog.Builder(requireContext())
        .setMessage(R.string.permission_audio_record_denied)
        .setTitle(R.string.permission_audio_record_denied)
        .setPositiveButton(R.string.ok) { dialog, _ ->
            startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + requireContext().packageName)))
            dialog.dismiss()
        }
        .setNegativeButton(R.string.cancel) { dialog, _ ->
            dialog.dismiss()
        }
        .show()

    private fun MaterialButton.togglePermission(enabled: Boolean) = if (enabled)
        this.enablePermission()
    else
        this.disablePermission()

    private fun MaterialButton.enablePermission() {
        this.isEnabled = false
        this.setText(R.string.granted)
    }

    private fun MaterialButton.disablePermission() {
        this.isEnabled = true
        this.setText(R.string.not_granted)
    }

}