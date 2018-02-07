dependencies {
    "implementation"("com.google.collections:google-collections:1.0")
    "implementation"("com.google.guava:guava:17.0")

    modules {
        module("com.google.collections:google-collections") {
            val details = this as ComponentModuleMetadataDetails
            replacedBy("com.google.guava:guava", "Prefer Guava over Google collections")
        }
    }
}

