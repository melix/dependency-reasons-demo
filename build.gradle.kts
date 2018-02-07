allprojects {

    apply {
        group = "com.acme"

        plugin("java-library")
        from("$rootDir/repositories.gradle.kts")
        from("${rootDir}/global-constraints.gradle.kts")

    }


    tasks {
        "report"(DependencyInsightReportTask::class) {
            val focus by project
            configuration = configurations.getByName("compileClasspath")
            setDependencySpec({ it.requested.displayName.contains(focus as String)})
        }
    }


}