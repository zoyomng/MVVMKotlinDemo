package com.zoyo.mvvmkotlindemo.ui.page.page

import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zoyo.mvvmkotlindemo.R
import com.zoyo.core.base.BaseFragment
import com.zoyo.core.base.BaseViewModel
import com.zoyo.mvvmkotlindemo.BR
import com.zoyo.mvvmkotlindemo.databinding.FragmentPageBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PageFragment : BaseFragment<FragmentPageBinding>(R.layout.fragment_page, BR.viewModel) {
    private val pageViewModel by viewModels<PageViewModel>()

    override fun getVM(): BaseViewModel {
        return pageViewModel
    }

    override fun initialize() {

        val adapter = PageAdapter()
        dataBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        dataBinding.recyclerView.adapter = adapter


        //将适配器订阅到ViewModel，当列表发生变化时以便刷新适配器中的项
        lifecycleScope.launch {
            pageViewModel.allCheeses.collectLatest { adapter.submitData(it) }
        }

        initSwipeToDelete()

        initEditorListener()
    }

    /**
     * 输入法键盘上的各种键,false:隐藏键盘,true:保留键盘
     */
    private fun initEditorListener() {
        dataBinding.textInputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                addCheese()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    /**
     * RecyclerView:条目触摸帮助类-左滑/右滑/拖拽
     */
    private fun initSwipeToDelete() {
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
        }).attachToRecyclerView(dataBinding.recyclerView)
    }

    private fun addCheese() {
        val newCheese = dataBinding.textInputEditText.text?.trim()
        if (!newCheese.isNullOrEmpty()) {
            pageViewModel.insert(newCheese)
        }
    }

}