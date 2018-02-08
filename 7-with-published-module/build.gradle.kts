repositories {
    maven {
        setUrl("../repo")
    }
}
dependencies {
    "implementation"("com.google.guava:guava:17.0")
    "implementation"("org.dummy:dummy:1.0")
}

val focus by extra { "google" }