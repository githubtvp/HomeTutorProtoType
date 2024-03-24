plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.login_page1"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.login_page1"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures{
        viewBinding=true
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

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity:1.8.0")
<<<<<<< HEAD

=======
<<<<<<< HEAD
    implementation("androidx.cardview:cardview:1.0.0")
//added by tvp
    implementation("androidx.cardview:cardview:1.0.0")
=======
<<<<<<< HEAD
>>>>>>> 683d9cc6dc59a923b4fdee781e2f0a569daa4d0f
    implementation("androidx.cardview:cardview:1.0.0")
//added by tvp
    implementation("androidx.cardview:cardview:1.0.0")


<<<<<<< HEAD
    implementation("androidx.cardview:cardview:1.0.0")

//added by tvp
    implementation("androidx.cardview:cardview:1.0.0")



=======
=======
>>>>>>> f2c77db349dc47c3631d7b6a69f5d95691799670
>>>>>>> 7ad808199bf26cbfa2b4d2f924ec6c50426ae664
>>>>>>> 786bed812b3e68aa3b48b794aa6fdb2a227ddb3c
>>>>>>> 683d9cc6dc59a923b4fdee781e2f0a569daa4d0f
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}