dependencies {
    "implementation"("com.google.collections:google-collections:1.0")
}

configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.name == "google-collections") {
            because("Guava supercedes Google collections").useTarget("com.google.guava:guava:17.0")
        }
    }
}

