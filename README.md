# r8-kotlin-reflection-on-nested-child-class-bug-demo

This sample demonstrates how an application shrunk with R8 crashes when accessing a Kotlin class `memberProperties` property via reflection if that is a nested child class inheriting from another nested class.

```
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
        @Suppress("UNUSED_VARIABLE")
        val properties = Wrapper.NestedChild::class.memberProperties
    }
}
```


## How to use this sample

1. Run the sample
1. Observe that the app crashes with the following stacktrace (deobfuscated):

```
2020-09-24 22:00:46.768 26890-26890/com.example.r8_kotlin_reflection_on_nested_child_class_bug_demo E/AndroidRuntime: FATAL EXCEPTION: main
    Process: com.example.r8_kotlin_reflection_on_nested_child_class_bug_demo, PID: 26890
    java.lang.RuntimeException: Unable to start activity ComponentInfo{com.example.r8_kotlin_reflection_on_nested_child_class_bug_demo/com.example.r8_kotlin_reflection_on_nested_child_class_bug_demo.MainActivity}: java.lang.IllegalStateException: Incomplete hierarchy for class NestedChild, unresolved classes [com.example.r8_kotlin_reflection_on_nested_child_class_bug_demo.Wrapper$NestedParent]
        at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:3270)
        at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:3409)
        at android.app.servertransaction.LaunchActivityItem.execute(LaunchActivityItem.java:83)
        at android.app.servertransaction.TransactionExecutor.executeCallbacks(TransactionExecutor.java:135)
        at android.app.servertransaction.TransactionExecutor.execute(TransactionExecutor.java:95)
        at android.app.ActivityThread$H.handleMessage(ActivityThread.java:2016)
        at android.os.Handler.dispatchMessage(Handler.java:107)
        at android.os.Looper.loop(Looper.java:214)
        at android.app.ActivityThread.main(ActivityThread.java:7356)
        at java.lang.reflect.Method.invoke(Native Method)
        at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:492)
        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:930)
     Caused by: java.lang.IllegalStateException: Incomplete hierarchy for class NestedChild, unresolved classes [com.example.r8_kotlin_reflection_on_nested_child_class_bug_demo.Wrapper$NestedParent]
        at kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.RuntimeErrorReporter.reportIncompleteHierarchy(:26)
        at kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedClassDescriptor$DeserializedClassTypeConstructor.computeSupertypes(:198)
        at kotlin.reflect.jvm.internal.impl.types.AbstractTypeConstructor$supertypes$1.invoke(:80)
        at kotlin.reflect.jvm.internal.impl.types.AbstractTypeConstructor$supertypes$1.invoke(:26)
        at kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager$LockBasedLazyValue.invoke(:370)
        at kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager$LockBasedLazyValueWithPostCompute.invoke(:443)
        at kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager$LockBasedNotNullLazyValueWithPostCompute.invoke(:474)
        at kotlin.reflect.jvm.internal.impl.types.AbstractTypeConstructor.getSupertypes(:27)
        at kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedClassDescriptor$DeserializedClassMemberScope.getNonDeclaredVariableNames(:306)
        at kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberScope$variableNamesLazy$2.invoke(:77)
        at kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberScope$variableNamesLazy$2.invoke(:40)
        at kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager$LockBasedLazyValue.invoke(:370)
        at kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager$LockBasedNotNullLazyValue.invoke(:489)
        at kotlin.reflect.jvm.internal.impl.storage.StorageKt.getValue(:42)
        at kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberScope.getVariableNamesLazy(Unknown Source:7)
        at kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberScope.getVariableNames(:91)
        at kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberScope.addFunctionsAndProperties(:216)
        at kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberScope.computeDescriptors(:187)
        at kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedClassDescriptor$DeserializedClassMemberScope$allDescriptors$1.invoke(:227)
        at kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedClassDescriptor$DeserializedClassMemberScope$allDescriptors$1.invoke(:220)
        at kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager$LockBasedLazyValue.invoke(:370)
        at kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager$LockBasedNotNullLazyValue.invoke(:489)
        at kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedClassDescriptor$DeserializedClassMemberScope.getContributedDescriptors(:237)
        at kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope$DefaultImpls.getContributedDescriptors$default(:52)
        at kotlin.reflect.jvm.internal.KDeclarationContainerImpl.getMembers(:57)
        at kotlin.reflect.jvm.internal.KClassImpl$Data$declaredNonStaticMembers$2.invoke(:161)
        at kotlin.reflect.jvm.internal.KClassImpl$Data$declaredNonStaticMembers$2.invoke(:46)
        at kotlin.reflect.jvm.internal.ReflectProperties$LazySoftVal.invoke(:92)
        at kotlin.reflect.jvm.internal.ReflectProperties$Val.getValue(:31)
        at kotlin.reflect.jvm.internal.KClassImpl$Data.getDeclaredNonStaticMembers(Unknown Source:8)
        at kotlin.reflect.jvm.internal.KClassImpl$Data$allNonStaticMembers$2.invoke(:170)
        at kotlin.reflect.jvm.internal.KClassImpl$Data$allNonStaticMembers$2.invoke(:46)
        at kotlin.reflect.jvm.internal.ReflectProperties$LazySoftVal.invoke(:92)
        at kotlin.reflect.jvm.internal.ReflectProperties$Val.getValue(:31)
        at kotlin.reflect.jvm.internal.KClassImpl$Data.getAllNonStaticMembers(Unknown Source:8)
        at kotlin.reflect.full.KClasses.getMemberProperties(:149)
        at com.example.r8_kotlin_reflection_on_nested_child_class_bug_demo.MainActivity.onCreate(:23)
        at android.app.Activity.performCreate(Activity.java:7802)
        at android.app.Activity.performCreate(Activity.java:7791)
        at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1299)
        at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:3245)
        at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:3409) 
        at android.app.servertransaction.LaunchActivityItem.execute(LaunchActivityItem.java:83) 
        at android.app.servertransaction.TransactionExecutor.executeCallbacks(TransactionExecutor.java:135) 
        at android.app.servertransaction.TransactionExecutor.execute(TransactionExecutor.java:95) 
        at android.app.ActivityThread$H.handleMessage(ActivityThread.java:2016) 
        at android.os.Handler.dispatchMessage(Handler.java:107) 
        at android.os.Looper.loop(Looper.java:214) 
        at android.app.ActivityThread.main(ActivityThread.java:7356) 
        at java.lang.reflect.Method.invoke(Native Method) 
        at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:492) 
        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:930) 
```

## Workarounds

### Workaround #1 : Use Kotlin 1.3.72

The crash does not occur when using Kotlin 1.3.72. It does on 1.4.0 and 1.4.10.


### Workaround #2 : Disable obfuscation

Adding the following line to the proguard config prevents the crash from happening:

```
-dontobfuscate
```