package com.alancamargo.tubecalculator.search.di

import android.content.Context
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.alancamargo.tubecalculator.common.ui.model.UiStation
import com.alancamargo.tubecalculator.core.design.ads.AdLoader
import com.alancamargo.tubecalculator.core.design.di.CoreDesignModule
import com.alancamargo.tubecalculator.core.design.dialogue.DialogueHelper
import com.alancamargo.tubecalculator.navigation.FaresActivityNavigation
import com.alancamargo.tubecalculator.navigation.SettingsActivityNavigation
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CoreDesignModule::class]
)
object SearchTestModule {

    @Provides
    @Singleton
    fun provideMockAdLoader(): AdLoader = object : AdLoader {
        override fun loadBannerAds(target: View) {

        }

        override fun loadInterstitialAds(activity: AppCompatActivity, adIdRes: Int) {

        }
    }

    @Provides
    @Singleton
    fun provideMockDialogueHelper(): DialogueHelper = object : DialogueHelper {
        override fun showDialogue(context: Context, iconRes: Int, title: String, messageRes: Int) {

        }

        override fun showDialogue(
            context: Context,
            titleRes: Int,
            messageRes: Int,
            buttonTextRes: Int,
            onDismiss: (() -> Unit)?
        ) {

        }

        override fun showDialogue(context: Context, titleRes: Int, message: CharSequence) {

        }
    }

    @Provides
    @Singleton
    fun provideMockSettingsActivityNavigation(): SettingsActivityNavigation = object : SettingsActivityNavigation {
        override fun startActivity(context: Context) {

        }
    }

    @Provides
    @Singleton
    fun provideMockFaresActivityNavigation(): FaresActivityNavigation = object : FaresActivityNavigation {
        override fun startActivity(
            context: Context,
            origin: UiStation?,
            destination: UiStation?,
            busAndTramJourneyCount: Int
        ) {

        }
    }
}
