package com.alancamargo.tubecalculator.fares.ui.viewmodel

import com.alancamargo.tubecalculator.fares.ui.model.UiFaresError

internal sealed class FaresViewAction {

    object NavigateToHome : FaresViewAction()

    data class ShowErrorDialogue(val error: UiFaresError) : FaresViewAction()

    data class ShowMessagesDialogue(val text: CharSequence) : FaresViewAction()
}
