package com.alancamargo.tubecalculator.core.design.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import com.alancamargo.tubecalculator.core.design.R

@Composable
fun CustomFontText(
    text: String,
    modifier: Modifier = Modifier,
    colour: Color = Color.Unspecified,
    textAlign: TextAlign = TextAlign.Unspecified,
    textSize: TextUnit = TextUnit.Unspecified,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        modifier = modifier,
        text = text,
        color = colour,
        fontFamily = FontFamily(Font(R.font.johnston)),
        textAlign = textAlign,
        fontSize = textSize,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis
    )
}

@Preview
@Composable
private fun CustomFontTextPreview() {
    CustomFontText(text = "Sample text")
}
