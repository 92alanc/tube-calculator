package com.alancamargo.tubecalculator.core.design.view

import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.alancamargo.tubecalculator.core.design.R
import com.alancamargo.tubecalculator.core.design.model.SwitchData

@Composable
fun CustomSwitch(switchData: SwitchData) {
    Switch(
        checked = switchData.isEnabled,
        onCheckedChange = switchData.onChanged,
        colors = SwitchDefaults.colors(
            checkedTrackColor = colorResource(R.color.red),
            uncheckedTrackColor = colorResource(R.color.white),
            uncheckedBorderColor = colorResource(R.color.black),
            uncheckedThumbColor = colorResource(R.color.black)
        )
    )
}

@Preview
@Composable
private fun PreviewEnabledCustomSwitch() {
    CustomSwitch(
        switchData = SwitchData(
            isEnabled = true,
            onChanged = {}
        )
    )
}

@Preview
@Composable
private fun PreviewDisabledCustomSwitch() {
    CustomSwitch(
        switchData = SwitchData(
            isEnabled = false,
            onChanged = {}
        )
    )
}
