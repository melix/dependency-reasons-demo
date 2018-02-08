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

val focus by extra { "google" }

tasks {
    "resolveWithApi"() {
        doLast {
            val result = configurations.getByName("compileClasspath").incoming.resolutionResult
            result.allComponents {
                println("Component $id selection reasons:")
                selectionReason.descriptions.forEach {
                    println("   ${it.cause} : ${it.description}")
                }
            }
        }
    }
}