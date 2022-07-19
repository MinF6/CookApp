package com.zongmin.cook.follow

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.zongmin.cook.databinding.FragmentFollowBinding
import com.zongmin.cook.ext.getVmFactory


class FollowFragment : Fragment() {

    private val viewModel by viewModels<FollowViewModel> { getVmFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFollowBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
//        Log.d(
//            "hank1",
//            "我收到了資料 -> ${FollowFragmentArgs.fromBundle(requireArguments()).userList}"
//        )
//        val userList = FollowFragmentArgs.fromBundle(requireArguments()).userList

        val adapter = FollowAdapter(FollowAdapter.OnClickListener{
            Log.d("hank1","點到了 $it")
        })


        viewModel.user.observe(viewLifecycleOwner){

            adapter.submitList(it)
        }

        binding.recyclerviewFollow.adapter = adapter





        // Inflate the layout for this fragment
        return binding.root
    }


}