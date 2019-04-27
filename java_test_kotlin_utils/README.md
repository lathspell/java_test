Kotlin Stdlib in Java
=====================

The Kotlin Stdlib can be used in Java projects as well.

Extension Functions
-------------------

Kotlin Extension Functions cannot be accessed directly on the object as in
Kotlin though as extending classes is not possible within the Java Language.
Those functions are available in classes called "StringKt" (for "String") and can be called like

    String s = StringKt.trimIndent("  foo");
 
