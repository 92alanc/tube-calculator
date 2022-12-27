package com.alancamargo.tubecalculator.fares.ui.viewmodel

import com.alancamargo.tubecalculator.fares.ui.model.UiFaresError

internal sealed class FaresViewAction {

    object NavigateToSearch : FaresViewAction()

    data class ShowErrorDialogue(val error: UiFaresError) : FaresViewAction()

    object Finish : FaresViewAction()
}
