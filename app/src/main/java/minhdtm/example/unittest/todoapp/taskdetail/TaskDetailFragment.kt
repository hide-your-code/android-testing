package minhdtm.example.unittest.todoapp.taskdetail

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import minhdtm.example.unittest.todoapp.BR
import minhdtm.example.unittest.todoapp.R
import minhdtm.example.unittest.todoapp.databinding.TaskdetailFragBinding
import minhdtm.example.unittest.todoapp.tasks.DELETE_RESULT_OK
import minhdtm.example.unittest.todoapp.util.eventObserve
import minhdtm.example.unittest.todoapp.util.setupRefreshLayout
import minhdtm.example.unittest.todoapp.util.setupSnackbar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main UI for the task detail screen.
 */
@AndroidEntryPoint
class TaskDetailFragment : Fragment() {
    private lateinit var binding: TaskdetailFragBinding

    private val args: TaskDetailFragmentArgs by navArgs()

    private val viewModel by viewModels<TaskDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.taskdetail_frag, container, false)

        binding.setVariable(BR.viewModel, viewModel)
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.start(args.taskId)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFab()
        view.setupSnackbar(viewLifecycleOwner, viewModel.snackbarText, Snackbar.LENGTH_SHORT)
        setupNavigation()
        setupRefreshLayout(binding.refreshLayout)
    }

    private fun setupNavigation() {
        viewModel.run {
            deleteTaskEvent.eventObserve(viewLifecycleOwner) {
                val action = TaskDetailFragmentDirections
                    .actionTaskDetailFragmentToTasksFragment(DELETE_RESULT_OK)
                findNavController().navigate(action)
            }

            editTaskEvent.eventObserve(viewLifecycleOwner) {
                val action = TaskDetailFragmentDirections
                    .actionTaskDetailFragmentToAddEditTaskFragment(
                        args.taskId,
                        resources.getString(R.string.edit_task)
                    )
                findNavController().navigate(action)
            }
        }
    }

    private fun setupFab() {
        activity?.findViewById<View>(R.id.edit_task_fab)?.setOnClickListener {
            viewModel.editTask()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_delete -> {
                viewModel.deleteTask()
                true
            }
            else -> false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.taskdetail_fragment_menu, menu)
    }
}
