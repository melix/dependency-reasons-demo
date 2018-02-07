dependencies {
    constraints {
        add("implementation", "org.ow2.asm:asm") {
            version {
                prefer("6.0")
            }
            because("requires a JDK 9 compatible bytecode generator")
        }
    }

    "implementation"("org.ow2.asm:asm")
}

val focus by extra { "asm" }