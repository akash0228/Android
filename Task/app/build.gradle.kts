plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.task"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.task"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    flavorDimensions+="paidMode"
    productFlavors{
        create("free"){
            applicationIdSuffix=".free"
            versionNameSuffix="-free"
            dimension="paidMode"
//            buildConfigField("String","BASE_URL_FREE","\"https://www.example_Free.com/\"")
        }
        create("paid"){
            applicationIdSuffix=".paid"
            versionNameSuffix="-paid"
            dimension="paidMode"
        }
    }



    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        create("qa"){
            isMinifyEnabled = false
            isDebuggable=false
            android.buildFeatures.buildConfig = true
            buildConfigField("String","BASE_URL","\"https://www.example_qa.com/\"")
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

tasks.register("helloWorld"){
    println("Hello World")
}

tasks.register("task1"){
    dependsOn("helloWorld")
    doLast{
        println("Hello from task 1")
    }
}

//tasks.register("copyApk",Copy::class.java){
//
//}

tasks.register("task2"){
//    dependsOn("helloWorld")
    doLast{
        println("Hello from task 2")
    }
}

tasks.whenTaskAdded{
    if (this.name=="task2"){
        println("task task registered")
    }
}



//tasks.named("task1"){
//    dependsOn("task4","task3")
//}