package com.example.r8_kotlin_reflection_on_nested_child_class_bug_demo

import android.os.Bundle
import androidx.annotation.Keep
import androidx.appcompat.app.AppCompatActivity
import kotlin.reflect.full.memberProperties

@Keep
class Wrapper {
    @Keep
    open class NestedParent

    @Keep
    class NestedChild : NestedParent()
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // This will cause the application to crash
        val properties = Wrapper.NestedChild::class.memberProperties
    }
}
