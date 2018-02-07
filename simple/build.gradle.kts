dependencies {
    "implementation"("org.ow2.asm:asm:6.0") {
        because("requires a JDK 9 compatible bytecode generator")
    }
}

val focus by extra { "asm" }