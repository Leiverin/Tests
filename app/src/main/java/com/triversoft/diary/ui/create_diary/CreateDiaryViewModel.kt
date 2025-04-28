package com.triversoft.diary.ui.create_diary

import androidx.lifecycle.MutableLiveData
import com.triversoft.diary.data.models.ContentType
import com.triversoft.diary.data.models.DiaryModel
import com.triversoft.diary.ui.base.BaseViewModel
import com.triversoft.diary.util.Constants

class CreateDiaryViewModel: BaseViewModel() {

    val dairyCurrent = MutableLiveData(DiaryModel(id = System.currentTimeMillis()))
    val liveContents = MutableLiveData<List<DiaryModel.Content>>()
    val contents = mutableListOf<DiaryModel.Content>()
    val contentCurrent = MutableLiveData<DiaryModel.Content?>()

    private fun contentEmpty(type: ContentType): DiaryModel.Content
        = DiaryModel.Content(System.currentTimeMillis(), type, "", false, arrayListOf(Constants.DEFAULT), contents.size + 1)

    fun addEmptyContent(type: ContentType){
        contents.add(contentEmpty(type))
        liveContents.value = contents.sortedBy { it.priority }
    }

    fun removeContent(content: DiaryModel.Content){
        contents.remove(content)
        liveContents.value = contents.sortedBy { it.priority }
    }

    fun updateContentImage(path: String){
        contents.find { it.id == contentCurrent.value?.id }?.images?.add(path)
        val images = contents.find { it.id == contentCurrent.value?.id }?.images
        contentCurrent.value = contentCurrent.value?.copy(images = images ?: arrayListOf())
        liveContents.value = contents.sortedBy { it.priority }
    }

}
