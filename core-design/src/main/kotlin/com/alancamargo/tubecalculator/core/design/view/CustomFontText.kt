package com.alancamargo.tubecalculator.core.design.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.alancamargo.tubecalculator.core.design.R
import com.alancamargo.tubecalculator.core.design.model.TextStyle

@Composable
fun CustomFontText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Unspecified,
    textStyle: TextStyle = TextStyle.BODY,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        modifier = modifier,
        text = text,
        color = colorResource(textStyle.textColourRes),
        fontFamily = FontFamily(Font(R.font.johnston)),
        textAlign = textAlign,
        fontSize = textStyle.fontSize,
        fontWeight = textStyle.fontWeight,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis
    )
}

@Preview
@Composable
private fun CustomFontTextPreview() {
    CustomFontText(text = "Sample text")
}
