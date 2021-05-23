package minhdtm.example.unittest.todoapp.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import minhdtm.example.unittest.todoapp.BR
import minhdtm.example.unittest.todoapp.R
import minhdtm.example.unittest.todoapp.databinding.StatisticsFragBinding
import minhdtm.example.unittest.todoapp.util.setupRefreshLayout
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main UI for the statistics screen.
 */
@AndroidEntryPoint
class StatisticsFragment : Fragment() {
    private lateinit var binding: StatisticsFragBinding

    private val viewModel by viewModels<StatisticsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.statistics_frag, container, false)

        binding.setVariable(BR.viewModel, viewModel)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRefreshLayout(binding.refreshLayout)
    }
}
