package com.alancamargo.tubecalculator.core.design.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alancamargo.tubecalculator.core.design.R
import com.valentinilk.shimmer.shimmer

@Composable
fun ShimmerBox(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .shimmer()
            .background(
                color = colorResource(R.color.grey_light),
                shape = RoundedCornerShape(dimensionResource(R.dimen.spacing_8))
            )
    )
}

@Preview
@Composable
private fun ShimmerBoxPreview() {
    ShimmerBox(modifier = Modifier.width(240.dp).height(30.dp))
}
