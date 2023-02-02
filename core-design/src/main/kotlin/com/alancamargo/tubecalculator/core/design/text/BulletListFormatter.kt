package com.alancamargo.tubecalculator.core.design.text

interface BulletListFormatter {

    fun getBulletList(strings: List<String>): CharSequence
}

