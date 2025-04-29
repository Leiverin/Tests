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
    var oldYPosBtnAdd = 0f

    private fun contentEmpty(type: ContentType): DiaryModel.Content
        = DiaryModel.Content(System.currentTimeMillis(), type, "", false, arrayListOf(Constants.DEFAULT), contents.size + 1)

    fun addEmptyContent(type: ContentType){
        contents.add(contentEmpty(type))
         liveContents.value = contents
    }

    fun removeContent(content: DiaryModel.Content){
        contents.remove(content)
        liveContents.value = contents
    }

    fun updateContentImage(path: String){
        val content = contents.find { it.id == contentCurrent.value?.id }
        val index = contents.indexOf(content)
        val images = content?.images?.toMutableList()
        images?.add(path)
        if (index >= 0){
            contents.removeAt(index)
            contents.add(index, content!!.copy(images = images ?: arrayListOf(Constants.DEFAULT)))
        }
        contentCurrent.value = contentCurrent.value?.copy(images = images ?: arrayListOf())
//        liveContents.value = contents
    }

}
