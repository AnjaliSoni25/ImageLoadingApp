// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    /*alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false*/


    id ("com.android.application") version  "7.4.2" apply false
    id ("com.android.library") version "7.4.2" apply false
    id ("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id ("com.google.dagger.hilt.android") version "2.48" apply false
}