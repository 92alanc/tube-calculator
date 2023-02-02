package com.alancamargo.tubecalculator.core.extensions

import android.content.Intent
import android.os.Parcelable
import com.alancamargo.tubecalculator.core.constants.EXTRA_ARGS

fun <T : Parcelable> Intent.putArguments(args: T): Intent = putExtra(EXTRA_ARGS, args)
