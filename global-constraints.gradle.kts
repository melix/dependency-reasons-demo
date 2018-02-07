if (project.name != "with-component-selection") {
    dependencies {
        constraints {
            add("implementation", "com.google.collections:google-collections") {
                version {
                    rejectAll()
                }
                because("Google collections is superceded by Guava")
            }
        }
    }
}