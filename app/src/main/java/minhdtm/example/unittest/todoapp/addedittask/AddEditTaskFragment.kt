package minhdtm.example.unittest.todoapp.addedittask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import minhdtm.example.unittest.todoapp.BR
import minhdtm.example.unittest.todoapp.R
import minhdtm.example.unittest.todoapp.databinding.AddtaskFragBinding
import minhdtm.example.unittest.todoapp.tasks.ADD_EDIT_RESULT_OK
import minhdtm.example.unittest.todoapp.util.eventObserve
import minhdtm.example.unittest.todoapp.util.setupRefreshLayout
import minhdtm.example.unittest.todoapp.util.setupSnackbar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main UI for the add task screen. Users can enter a task title and description.
 */
@AndroidEntryPoint
class AddEditTaskFragment : Fragment() {
    private lateinit var binding: AddtaskFragBinding

    private val args: AddEditTaskFragmentArgs by navArgs()

    private val viewModel by viewModels<AddEditTaskViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.addtask_frag, container, false)

        binding.setVariable(BR.viewModel, viewModel)
        binding.lifecycleOwner = this.viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSnackbar(view)
        setupNavigation()
        setupRefreshLayout(binding.refreshLayout)
        viewModel.start(args.taskId)
    }

    private fun setupSnackbar(view: View) {
        view.setupSnackbar(viewLifecycleOwner, viewModel.snackbarText, Snackbar.LENGTH_SHORT)
    }

    private fun setupNavigation() {
        viewModel.taskUpdatedEvent.eventObserve(viewLifecycleOwner) {
            val action = AddEditTaskFragmentDirections
                .actionAddEditTaskFragmentToTasksFragment(ADD_EDIT_RESULT_OK)
            findNavController().navigate(action)
        }
    }
}
