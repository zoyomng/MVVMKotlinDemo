package com.zoyo.mvvmkotlindemo.ui.page

import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zoyo.mvvmkotlindemo.R
import com.zoyo.mvvmkotlindemo.core.base.BaseFragment
import com.zoyo.mvvmkotlindemo.core.base.BaseViewModel
import com.zoyo.mvvmkotlindemo.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_page.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PageFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_page) {
    private val pageViewModel by viewModels<PageViewModel>()

    override fun getVM(): BaseViewModel {
        return pageViewModel
    }

    override fun initData() {

        val adapter = PageAdapter()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        lifecycleScope.launch {
            pageViewModel.allCheeses.collectLatest { adapter.submitData(it) }
        }

        //RecyclerView:条目触摸帮助类-左滑/右滑/拖拽
        ItemTouchHelper(object : ItemTouchHelper.Callback() {

            //动作
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int = makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            //滑动处理
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                (viewHolder as PageViewHolder).cheese?.let {
                    pageViewModel.remove(it)
                }
            }
        })

        //输入法
        textInputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                addCheese()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun addCheese() {
        val newCheese = textInputEditText.text?.trim()
        if (!newCheese.isNullOrEmpty()) {
            pageViewModel.insert(newCheese)
        }
    }

}