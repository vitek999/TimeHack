package ru.visdom.hackemptyapplication.ui


import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.visdom.hackemptyapplication.R
import ru.visdom.hackemptyapplication.databinding.FragmentLoginBinding
import ru.visdom.hackemptyapplication.viewmodel.LoginViewModel
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private var progressDialog: ProgressDialog? = null

    private val viewModel: LoginViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, LoginViewModel.Factory(activity.application))
            .get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        binding.signupButton.setOnClickListener {
            showDialog()
            viewModel.authUser(
                binding.phoneNumberEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }

        viewModel.authStatus.observe(this, Observer<LoginViewModel.AuthStatus> {
            hideDialog()
            when (it) {
                LoginViewModel.AuthStatus.OK -> findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainFragment())
                LoginViewModel.AuthStatus.CONNECTION_ERROR -> Snackbar.make(binding.root, "Ошибка соединения", Snackbar.LENGTH_SHORT).show()
                LoginViewModel.AuthStatus.WRONG_AUTH_INFORMATION -> Snackbar.make(binding.root, "Провеаьте введённые данные", Snackbar.LENGTH_SHORT).show()
                else -> Unit
            }
        })

        return binding.root
    }

    private fun showDialog() {
        progressDialog = ProgressDialog.show(this.activity, "", getString(R.string.please_wait))
        Timber.i("show")
    }

    private fun hideDialog() = progressDialog?.dismiss()


}
