plugins {
    // Plugins that are used in more than one Gradle module must be defined with their version number
    // in the toplevel build file and once again without their version number in the modules that use them.
    // Cf. https://docs.gradle.org/current/userguide/plugins.html#sec:subprojects_plugins_dsl
    id("com.commercehub.gradle.plugin.avro") version "0.19.1" apply false
}
