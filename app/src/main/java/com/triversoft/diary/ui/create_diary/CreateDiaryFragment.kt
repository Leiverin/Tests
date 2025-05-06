package com.triversoft.diary.ui.create_diary

import android.animation.LayoutTransition
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyTouchHelper
import com.triversoft.diary.R
import com.triversoft.diary.data.models.ContentType
import com.triversoft.diary.data.models.DiaryModel
import com.triversoft.diary.databinding.FragmentCreateDairyBinding
import com.triversoft.diary.extension.makeDelay
import com.triversoft.diary.extension.onGlobalLayout
import com.triversoft.diary.extension.setBackPressListener
import com.triversoft.diary.extension.setPreventDoubleClick
import com.triversoft.diary.extension.toPath
import com.triversoft.diary.itemCheckbox
import com.triversoft.diary.itemListPhotoSelected
import com.triversoft.diary.itemTextbox
import com.triversoft.diary.ui.base.BaseFragment
import com.triversoft.diary.ui.dialog.text_input.TextInputDialog
import com.triversoft.diary.ui.popup.AddContentPopup
import com.triversoft.diary.ui.popup.StatusPopup
import com.triversoft.diary.util.Constants
import com.triversoft.diary.util.loadDrawableWithAnim

class CreateDiaryFragment: BaseFragment<FragmentCreateDairyBinding>(R. layout.fragment_create_dairy),
    AddContentPopup.IOnEventAddContentListener {

    private val viewModel: CreateDiaryViewModel by viewModels()

    private var statusPopup: StatusPopup? = null
    private var addContentPopup: AddContentPopup? = null
    private var textInputDialog: TextInputDialog? = null

    private val launchPhoto = registerForActivityResult(ActivityResultContracts.GetContent()){ uri ->
        if (uri != null){
            context?.let { context ->
                uri.toPath(context)?.let { path ->
                    viewModel.updateContentImage(path)
                    controller.requestDelayedModelBuild(150)
                }
            }
        }
    }

    override fun initView(view: View) {
        binding.viewModel = viewModel
        observerData()
        initData()
        initEvents()
    }

    private fun observerData() {
        viewModel.liveContents.observe(viewLifecycleOwner){ list ->
            binding.rvContent.requestModelBuild()
        }
    }

    private fun initData() {
        initRvContent(controller)
    }

    private fun initRvContent(controller: EpoxyController) {
        EpoxyTouchHelper.initDragging(controller)
            .withRecyclerView(binding.rvContent)
            .forVerticalList()
            .forAllModels()
            .andCallbacks(dragAndDropCallback)
        binding.rvContent.setController(controller)
    }

    private val dragAndDropCallback = object : EpoxyTouchHelper.DragCallbacks<EpoxyModel<*>>() {
        override fun onModelMoved(
            fromPosition: Int,
            toPosition: Int,
            modelBeingMoved: EpoxyModel<*>?,
            itemView: View?
        ) {
            val removed = viewModel.contents.removeAt(fromPosition)
            viewModel.contents.add(toPosition, removed)
            binding.rvContent.requestModelBuild()
        }
    }

    private val controller: EpoxyController = object : EpoxyController() {
            override fun buildModels() {
                viewModel.contents.forEachIndexed { index, content ->
                    when (content.type) {
                        ContentType.TEXT -> {
                            itemTextbox {
                                id(content.id)
                                description(content.text)
                                onCancel {
                                    onCancelContent(content)
                                }
                                onClick {
                                    onFillText(content)
                                }
                            }
                        }

                        ContentType.CHECKBOX -> {
                            itemCheckbox {
                                id(content.id)
                                isChecked(content.isChecked)
                                description(content.text)
                                onCancel {
                                    onCancelContent(content)
                                }
                                onCheckedChange { _, isChecked ->
                                    onCheckedChange(content, isChecked)
                                }
                                onClick {
                                    onFillText(content)
                                }
                            }
                        }

                        ContentType.IMAGE -> {
                            itemListPhotoSelected {
                                id(content.id)
                                list(viewModel.contents[index].images)
                                onClickPhoto { path ->
                                    if (path == Constants.DEFAULT) selectPhoto(content)
                                    else handleClickPhoto(path)
                                }
                                onRemovePhoto{ path ->
                                    removePhoto(content, path)
                                }
                                onCancel { v ->
                                    onCancelContent(content)
                                }
                            }
                        }

                        else -> {}
                    }
                }
            }
        }

    private fun onCheckedChange(content: DiaryModel.Content, isChecked: Boolean){

    }

    private fun onFillText(content: DiaryModel.Content) {
        showDialogTextInput()
    }

    private fun removePhoto(content: DiaryModel.Content, path: String) {

    }

    private fun selectPhoto(content: DiaryModel.Content) {
        viewModel.contentCurrent.value = content
        launchPhoto.launch("image/*")
    }

    private fun onCancelContent(content: DiaryModel.Content) {
        if (viewModel.contentCurrent.value == content)
            viewModel.contentCurrent.value = null
        viewModel.removeContent(content)
    }

    private fun handleClickPhoto(path: String) {

    }

    private fun initEvents() {
        setBackPressListener(binding.btnBack) {
            if (textInputDialog?.isAdded == true){
                textInputDialog?.dismissDialog()
            }else{
                popUpBackStack()
            }
        }
        binding.viewMood.setPreventDoubleClick {
            showPopupStatus(StatusPopup.StatusType.MOOD, it)
        }
        binding.viewWeather.setPreventDoubleClick {
            showPopupStatus(StatusPopup.StatusType.WEATHER, it)
        }
        binding.btnAddContent.setPreventDoubleClick {
            showAddContentPopup(it)
        }
        binding.btnEdit.setPreventDoubleClick {

        }
        binding.btnAddImage.setPreventDoubleClick {
            addImage()
        }
        binding.btnAddText.setPreventDoubleClick {
            addTextbox()
        }
        binding.btnAddCbx.setPreventDoubleClick {
            addCheckbox()
        }
    }

    private fun showPopupStatus(type: StatusPopup.StatusType, view: View){
        statusPopup?.dismiss()
        context?.let { context ->
            statusPopup = StatusPopup(context).apply {
                setType(type)
                onSelectMood = { mood ->
                    viewModel.dairyCurrent.value = viewModel.dairyCurrent.value?.copy(mood = mood.first)
                    binding.imgMood.loadDrawableWithAnim(viewModel.dairyCurrent.value?.getIconMood() ?: R.drawable.ic_normal_mood)
                    statusPopup?.dismiss()
                }
                onSelectWeather = { weather ->
                    viewModel.dairyCurrent.value = viewModel.dairyCurrent.value?.copy(weather = weather.first)
                    binding.imgWeather.loadDrawableWithAnim(viewModel.dairyCurrent.value?.getIconWeather() ?: R.drawable.ic_sunny)
                    statusPopup?.dismiss()
                }
            }
            if (statusPopup?.isShowing != true){
                statusPopup?.showAsDropDown(view)
            }
        }
    }

    private fun showAddContentPopup(view: View){
        addContentPopup?.dismiss()
        context?.let { context ->
            addContentPopup = AddContentPopup(context).apply {
                onEventAddContentListener = this@CreateDiaryFragment
            }
            if (addContentPopup?.isShowing != true) addContentPopup?.showAsDropDown(view)
        }
    }

    override fun addTextbox() {
        viewModel.addEmptyContent(ContentType.TEXT)
        addContentPopup?.dismiss()
    }

    override fun addImage() {
        viewModel.addEmptyContent(ContentType.IMAGE)
        addContentPopup?.dismiss()
    }

    override fun addCheckbox() {
        viewModel.addEmptyContent(ContentType.CHECKBOX)
        addContentPopup?.dismiss()
    }

    private fun showDialogTextInput(){
        textInputDialog?.dismissImmediately()
        textInputDialog = TextInputDialog()
        if(isAdded) textInputDialog?.showDialog(childFragmentManager)
    }

    override fun screenName(): String = ""

}