dependencies {
    "implementation"("com.google.collections:google-collections:1.0")
}

configurations.all {
    resolutionStrategy.dependencySubstitution {
        substitute(module("com.google.collections:google-collections:1.0"))
                .because("Guava supercedes Google collections")
                .with(module("com.google.guava:guava:17.0"))
    }
}

val focus by extra { "google" }