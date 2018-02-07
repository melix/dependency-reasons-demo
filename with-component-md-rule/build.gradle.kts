dependencies {
    "implementation"("org.reflections:reflections:0.9.5-RC2")

    components {
        withModule("org.reflections:reflections") {
            allVariants {
                withDependencies {
                    removeIf { it.group == "com.google.collections" }
                    add("com.google.guava:guava:17.0") {
                        because("Guava supercedes Google collections")
                    }
                }
            }
        }
    }
}
