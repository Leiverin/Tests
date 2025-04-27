package com.triversoft.diary.ui.create_diary

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.triversoft.diary.data.models.DiaryModel
import com.triversoft.diary.ui.base.BaseViewModel

class CreateDiaryViewModel: BaseViewModel() {

    val dairyCurrent = MutableLiveData<DiaryModel>()
    val contents = MutableLiveData<List<DiaryModel.Content>>()

}