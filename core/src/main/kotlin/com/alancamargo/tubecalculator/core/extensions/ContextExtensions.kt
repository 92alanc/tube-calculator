package com.alancamargo.tubecalculator.core.extensions

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import kotlin.reflect.KClass

fun <T : AppCompatActivity> Context.createIntent(activityClass: KClass<T>): Intent {
    return Intent(this, activityClass.java)
}
