package com.alancamargo.tubecalculator.core.extensions

import android.os.Build
import android.os.Parcelable
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.alancamargo.tubecalculator.core.constants.EXTRA_ARGS

fun <F : Fragment, A : Parcelable> F.putArguments(args: A): F = apply {
    arguments = bundleOf(EXTRA_ARGS to args)
}

inline fun <reified A : Parcelable> Fragment.args(): Lazy<A> = lazy {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arguments?.getParcelable(EXTRA_ARGS, A::class.java)
    } else {
        arguments?.getParcelable(EXTRA_ARGS)!!
    } ?: throw IllegalArgumentException("Args not provided")
}
