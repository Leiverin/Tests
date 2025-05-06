package com.triversoft.diary.ui.dialog.text_input

import android.util.Log
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.triversoft.diary.R
import com.triversoft.diary.data.caching.MMKVCache
import com.triversoft.diary.data.models.text.FeatureExpandType
import com.triversoft.diary.data.models.text.FontStyle
import com.triversoft.diary.data.models.text.TextAlign
import com.triversoft.diary.data.models.text.TextColorMode
import com.triversoft.diary.data.models.text.TextFont
import com.triversoft.diary.data.models.text.TextType
import com.triversoft.diary.databinding.DialogTextInputBinding
import com.triversoft.diary.extension.beGone
import com.triversoft.diary.extension.beVisible
import com.triversoft.diary.extension.makeDelay
import com.triversoft.diary.extension.onGlobalLayout
import com.triversoft.diary.extension.setBackPressListener
import com.triversoft.diary.extension.setPreventDoubleClick
import com.triversoft.diary.extension.visibleAnimTranslate
import com.triversoft.diary.itemColorTextInput
import com.triversoft.diary.itemFontTextInput
import com.triversoft.diary.ui.base.BaseDialogFragment
import com.triversoft.diary.util.visibleAnimAlpha
import com.triversoft.diary.util.visibleAnimTranslate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TextInputDialog: BaseDialogFragment<DialogTextInputBinding>() {

    private val viewModel: TextInputViewModel by viewModels()

    override fun onViewReady() {
        setLayout(1f, 1f, false)
        observerData()
        initData()
        initEvents()
    }

    private fun observerData() {
        binding.viewModel = viewModel
        viewModel.fontStyles.observe(viewLifecycleOwner){ fontStyles ->

        }
    }

    private fun initData() {
        initDisplayView()
        initRvColor()
        initRvFont()
    }

    private fun initDisplayView() {
        binding.viewEdit.onGlobalLayout {
            binding.btnCloseTools.beGone()
            binding.viewEdit.visibleAnimTranslate(false, 1L)
            binding.viewTools.visibleAnimTranslate(false, 1L)
            lifecycleScope.launch(Dispatchers.Main) {
                delay(100)
                binding.viewEdit.visibleAnimTranslate(true, 300L)
                delay(300)
                binding.viewTools.visibleAnimTranslate(true, 300L){
                    binding.btnCloseTools.beVisible()
                }
            }
        }
    }

    private fun initRvFont() {
        binding.rvFont.withModels {
            viewModel.fonts.value?.forEachIndexed { index, textFont ->
                itemFontTextInput {
                    id(index)
                    text(textFont.text)
                    fontPath(textFont.getPathRegular())
                    isSelected(viewModel.fontCurrent.value == textFont)
                    onClick { _ ->
                        onClickFont(textFont)
                    }
                }
            }
        }
    }

    private fun initRvColor() {
        binding.rvColor.withModels {
            viewModel.colors.value?.forEachIndexed { _, colorCode ->
                itemColorTextInput {
                    id(colorCode)
                    color(colorCode)
                    isSelected(viewModel.colorSelected(colorCode))
                    onClick { _ ->
                        onClickColor(colorCode)
                    }
                }
            }
        }
    }

    private fun onClickFont(textFont: TextFont) {
        viewModel.fontCurrent.value = textFont
        binding.rvFont.requestModelBuild()
    }

    private fun onClickColor(colorCode: Int) {
        if (viewModel.textColorMode.value == TextColorMode.TEXT_COLOR) viewModel.textColorCurrent.value = colorCode
        if (viewModel.textColorMode.value == TextColorMode.TEXT_HIGHLIGHT_COLOR) viewModel.textHighlightColorCurrent.value = colorCode
        binding.rvColor.requestModelBuild()
    }

    private fun initEvents() {
        setBackPressListener(binding.btnClose, binding.viewContainer) {
            dismissDialog()
        }
        binding.btnDone.setPreventDoubleClick {
            dismissDialog()
        }
        binding.edContent.setOnFocusChangeListener { view, b ->
        }
        binding.btnCloseTools.setPreventDoubleClick {
            binding.btnCloseTools.beGone()
            binding.viewTools.visibleAnimTranslate(false)
        }

        // Events in popup expand option
        binding.btnPlusColor.setPreventDoubleClick {
            viewModel.featureExpandType.value = FeatureExpandType.ADD_COLOR
        }
        binding.btnBackPopupFeature.setPreventDoubleClick {
            viewModel.textColorMode.value = TextColorMode.NORMAL
            viewModel.featureExpandType.value = FeatureExpandType.PICK_COLOR
        }
        binding.btnDoneColor.setPreventDoubleClick {
            onDoneCustomColor()
        }

        // Events popup default
        binding.btnFont.setPreventDoubleClick {
            viewModel.toggleFontOpt()
        }
        binding.btnTextColor.setPreventDoubleClick {
            viewModel.toggleTextColorOpt()
            binding.rvColor.requestModelBuild()
        }
        binding.btnTextHighLightColor.setPreventDoubleClick {
            viewModel.toggleTextHighlightOpt()
            binding.rvColor.requestModelBuild()
        }

        // Events text align
        binding.btnAlignStart.setPreventDoubleClick {
            viewModel.alignCurrent.value = TextAlign.START
        }
        binding.btnAlignCenter.setPreventDoubleClick {
            viewModel.alignCurrent.value = TextAlign.CENTER
        }
        binding.btnAlignEnd.setPreventDoubleClick {
            viewModel.alignCurrent.value = TextAlign.END
        }

        // Event font styles
        binding.btnBold.setPreventDoubleClick {
            viewModel.toggleStyle(FontStyle.BOLD)
        }
        binding.btnItalic.setPreventDoubleClick {
            viewModel.toggleStyle(FontStyle.ITALIC)
        }
        binding.btnUnderline.setPreventDoubleClick {
            viewModel.toggleStyle(FontStyle.UNDERLINE)
        }
        binding.btnStrikeThrough.setPreventDoubleClick {
            viewModel.toggleStyle(FontStyle.STRIKETHROUGH)
        }

        // Event text type
        binding.btnTitle.setPreventDoubleClick {
            viewModel.textType.value = TextType.TITLE
        }
        binding.btnSubTitle.setPreventDoubleClick {
            viewModel.textType.value = TextType.SUBTITLE
        }
        binding.btnContent.setPreventDoubleClick {
            viewModel.textType.value = TextType.CONTENT
        }
        binding.hueView.setOnHueChangedListener { hue, argbColor ->
            binding.hueOpacityView.selectedColor = argbColor
            binding.cvColorCustom.setCardBackgroundColor(argbColor)
        }
        binding.hueOpacityView.setOnAlphaChangedListener {

        }
    }

    private fun onDoneCustomColor() {
        val colors = MMKVCache.colors.toMutableList()
        colors.add(0, binding.hueOpacityView.selectedColor)
        MMKVCache.colors = colors as ArrayList<Int>
        viewModel.colors.value = MMKVCache.colors
        viewModel.textColorCurrent.value = binding.hueOpacityView.selectedColor
        viewModel.featureExpandType.value = FeatureExpandType.PICK_COLOR
    }

    override fun layoutRes(): Int = R.layout.dialog_text_input

    fun showDialog(fragmentManager: FragmentManager){
        if (!isAdded && this.dialog?.isShowing != true){
            show(fragmentManager, this::class.toString())
        }
    }

    fun dismissDialog(){
        if (isAdded && this.dialog?.isShowing != false){
            if (binding.viewSubEdit.isVisible) binding.viewSubEdit.beGone()
            binding.viewEdit.visibleAnimTranslate(false)
            this.binding.viewTools.visibleAnimTranslate(false){
                dismiss()
            }
        }
    }

}