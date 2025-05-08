package com.triversoft.diary.ui.dialog.text_input

import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.TypefaceSpan
import android.util.Log
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.triversoft.diary.R
import com.triversoft.diary.data.caching.MMKVCache
import com.triversoft.diary.data.mmkv.MMKVUtils
import com.triversoft.diary.data.models.DiaryModel
import com.triversoft.diary.data.models.text.FeatureExpandType
import com.triversoft.diary.data.models.text.FontStyle
import com.triversoft.diary.data.models.text.TextAlign
import com.triversoft.diary.data.models.text.TextColorMode
import com.triversoft.diary.data.models.text.TextFont
import com.triversoft.diary.data.models.text.TextType
import com.triversoft.diary.databinding.DialogTextInputBinding
import com.triversoft.diary.extension.beGone
import com.triversoft.diary.extension.beVisible
import com.triversoft.diary.extension.dp
import com.triversoft.diary.extension.makeDelay
import com.triversoft.diary.extension.observeKeyboardVisibility
import com.triversoft.diary.extension.onGlobalLayout
import com.triversoft.diary.extension.setBackPressListener
import com.triversoft.diary.extension.setPreventDoubleClick
import com.triversoft.diary.extension.visibleAnimTranslate
import com.triversoft.diary.itemColorTextInput
import com.triversoft.diary.itemFontTextInput
import com.triversoft.diary.ui.base.BaseDialogFragment
import com.triversoft.diary.util.help.CustomTypefaceSpan
import com.triversoft.diary.util.visible
import com.triversoft.diary.util.visibleAnimAlpha
import com.triversoft.diary.util.visibleAnimTranslate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TextInputDialog: BaseDialogFragment<DialogTextInputBinding>() {

    private val viewModel: TextInputViewModel by viewModels()
    private var isModifying = false
    private var previousTextLength = 0
    private var removeCharCount = 0

    override fun onViewReady() {
        setLayoutFull()
        observerData()
        initData()
        initEvents()
    }

    private fun observerData() {
        binding.viewModel = viewModel
        viewModel.fontCurrent.value = MMKVCache.fonts.firstOrNull { it.id == MMKVUtils.fontCurrent }
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
                delay(50)
                binding.viewEdit.visibleAnimTranslate(true, 200L)
                delay(100)
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
        MMKVUtils.fontCurrent = textFont.id
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
        binding.btnCloseTools.setPreventDoubleClick {
            binding.btnCloseTools.beGone()
            binding.viewTools.visibleAnimTranslate(false)
        }

        // Events in popup expand option
        binding.btnPlusColor.setOnClickListener {
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
        binding.btnFont.setOnClickListener {
            viewModel.toggleFontOpt()
        }
        binding.btnTextColor.setOnClickListener {
            viewModel.toggleTextColorOpt()
            binding.rvColor.requestModelBuild()
        }
        binding.btnTextHighLightColor.setOnClickListener {
            viewModel.toggleTextHighlightOpt()
            binding.rvColor.requestModelBuild()
        }

        // Events text align
        binding.btnAlignStart.setOnClickListener {
            viewModel.alignCurrent.value = TextAlign.START
        }
        binding.btnAlignCenter.setOnClickListener {
            viewModel.alignCurrent.value = TextAlign.CENTER
        }
        binding.btnAlignEnd.setOnClickListener {
            viewModel.alignCurrent.value = TextAlign.END
        }

        // Event font styles
        binding.btnBold.setOnClickListener {
            viewModel.toggleStyle(FontStyle.BOLD)
        }
        binding.btnItalic.setOnClickListener {
            viewModel.toggleStyle(FontStyle.ITALIC)
        }
        binding.btnUnderline.setOnClickListener {
            viewModel.toggleStyle(FontStyle.UNDERLINE)
        }
        binding.btnStrikeThrough.setOnClickListener {
            viewModel.toggleStyle(FontStyle.STRIKETHROUGH)
        }

        // Event text type
        binding.btnTitle.setOnClickListener {
            viewModel.textType.value = TextType.TITLE
        }
        binding.btnSubTitle.setOnClickListener {
            viewModel.textType.value = TextType.SUBTITLE
        }
        binding.btnContent.setOnClickListener {
            viewModel.textType.value = TextType.CONTENT
        }
        binding.hueView.setOnHueChangedListener { hue, argbColor ->
            binding.hueOpacityView.selectedColor = argbColor
            binding.cvColorCustom.setCardBackgroundColor(viewModel.calculateColor(argbColor, binding.hueOpacityView.alphaValue))
        }
        binding.hueOpacityView.setOnAlphaChangedListener {
            binding.cvColorCustom.setCardBackgroundColor(viewModel.calculateColor(binding.hueOpacityView.selectedColor, binding.hueOpacityView.alphaValue))
        }

        // Event edit text
        binding.edContent.doBeforeTextChanged { text, start, count, after ->
            previousTextLength = text?.length ?: 0
            viewModel.selStart = binding.edContent.selectionStart - 1
            removeCharCount = count
        }
        binding.edContent.doAfterTextChanged { s ->
            doOnTextChanged(s)
        }
        binding.edContent.onSelectionChanged = { selStart, selEnd ->
            viewModel.selStartByUser = selStart
            viewModel.selEndByUser = selEnd
        }

        binding.root.observeKeyboardVisibility { isVisible ->
            if (isVisible){
                binding.viewSubEdit.beGone()
                binding.viewSmallFeature.visibleAnimTranslate(true)
                binding.btnCloseTools.beGone()
                binding.viewTools.visibleAnimTranslate(false)
            }else{
                binding.btnCloseTools.visibleAnimAlpha(true)
                binding.viewTools.visibleAnimTranslate(true)
                binding.viewSmallFeature.visibleAnimTranslate(false)
                viewModel.featureExpandType.value = viewModel.featureExpandType.value
            }
        }
    }

    private fun doOnTextChanged(s: Editable?) {
        runCatching {
            if (isModifying || s.isNullOrEmpty()) return
            if (previousTextLength >= s.length) {
                removeChar()
                return
            }
            isModifying = true
            val spannable = SpannableStringBuilder(s)
            val colorSpan = ForegroundColorSpan(viewModel.textColorCurrent.value ?: Color.parseColor("#171717"))
            val tp = Typeface.createFromAsset(context?.assets, viewModel.fontCurrent.value?.getPathRegular())
            val typefaceSpan = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) TypefaceSpan(tp) else CustomTypefaceSpan(tp)
            val bgSpan = BackgroundColorSpan(viewModel.textHighlightColorCurrent.value ?: Color.parseColor("#FFFFFF"))

            spannable.setSpan(colorSpan, viewModel.selStart, viewModel.selStart + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannable.setSpan(typefaceSpan, viewModel.selStart, viewModel.selStart + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannable.setSpan(bgSpan, viewModel.selStart, viewModel.selStart + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            // Save char
            viewModel.saveCharacter(s.toString())

            binding.edContent.text = spannable
            binding.edContent.setSelection(binding.edContent.selectionStart)
            isModifying = false
        }
    }

    private fun removeChar() {
        for (i in 0 until removeCharCount){
            if (viewModel.selStart < viewModel.textStyleList.size){
                viewModel.textStyleList.removeAt(viewModel.selStart)
            }
        }
    }

    private fun onDoneCustomColor() {
        val colorSelected = viewModel.calculateColor(binding.hueOpacityView.selectedColor, binding.hueOpacityView.alphaValue)
        val colors = MMKVCache.colors.toMutableList()
        colors.add(0, colorSelected)
        MMKVCache.colors = colors as ArrayList<Int>
        viewModel.colors.value = MMKVCache.colors
        if (viewModel.textColorMode.value == TextColorMode.TEXT_COLOR) viewModel.textColorCurrent.value = colorSelected
        if (viewModel.textColorMode.value == TextColorMode.TEXT_HIGHLIGHT_COLOR) viewModel.textHighlightColorCurrent.value = colorSelected
        viewModel.featureExpandType.value = FeatureExpandType.PICK_COLOR
        binding.rvColor.requestModelBuild()
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