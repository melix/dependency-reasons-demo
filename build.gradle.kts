allprojects {

    apply {
        group = "com.acme"

        plugin("java-library")
        from("$rootDir/repositories.gradle.kts")
        from("${rootDir}/global-constraints.gradle.kts")

    }

}