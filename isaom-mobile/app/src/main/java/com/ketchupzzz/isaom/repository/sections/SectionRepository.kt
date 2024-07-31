package com.ketchupzzz.isaom.repository.sections

import com.ketchupzzz.analytical.utils.UiState
import com.ketchupzzz.isaom.models.Sections

interface SectionRepository {
    fun getAllSections(result :( UiState<List<Sections>>) -> Unit)
}