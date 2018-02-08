# Demonstration of dependency reasons

This project demonstrates how to use custom dependency reasons.

## Simple dependency reasons

It's possible to declare reasons for all dependencies. Run:

`gradle 0-simple:dependencyInsight --dependency asm`

which will display:

```
> Task :0-simple:dependencyInsight 
org.ow2.asm:asm:6.0 (requires a JDK 9 compatible bytecode generator)
   variant "default" [
      Requested attributes not found in the selected variant:
         org.gradle.usage = java-api
   ]
\--- compileClasspath

```

See [0-simple/build.gradle.kts](0-simple/build.gradle.kts)

## Reasons on constraints

It is also possible to declare reaons on dependency constraints, as illustrated by the second project:

`gradle 1-with-constraint:dependencyInsight --dependency asm`

which shows:

```
> Task :1-with-constraint:dependencyInsight 
org.ow2.asm:asm:6.0 (via constraint, requires a JDK 9 compatible bytecode generator)
   variant "default" [
      Requested attributes not found in the selected variant:
         org.gradle.usage = java-api
   ]
\--- compileClasspath

org.ow2.asm:asm -> 6.0
   variant "default" [
      Requested attributes not found in the selected variant:
         org.gradle.usage = java-api
   ]
\--- compileClasspath
```

See [1-with-constraint/build.gradle.kts](1-with-constraint/build.gradle.kts)

## Improved error messages

If a dependency is rejected because of a constraint, the reason is displayed in the error message:

`gradle 2-with-error:dependencyInsight --dependency asm`

shows:

```
* What went wrong:
Execution failed for task ':with-error:dependencyInsight'.
> Could not resolve all dependencies for configuration ':with-error:compileClasspath'.
   > Module 'com.google.collections:google-collections' has been rejected:
        Dependency path 'com.acme:with-error:unspecified' --> 'com.google.collections:google-collections' prefers '1.0'
        Constraint path 'com.acme:with-error:unspecified' --> 'com.google.collections:google-collections' rejects all versions because of the following reason: Google collections is superceded by Guava

```

See [global-constraints.gradle.kts](global-constraints.gradle.kts) for the constraint definition
See [2-with-error/build.gradle.kts](2-with-error/build.gradle.kts) for the failing project

## Reasons provided by rules
### Using module replacement rules

The module replacement rule API has been improved to be able to supply custom reasons:

`gradle 3-with-module-replacement:dependencyInsight --dependency google`

renders:

```
> Task :3-with-module-replacement:dependencyInsight 
com.google.guava:guava:17.0 (Prefer Guava over Google collections)
   variant "default" [
      Requested attributes not found in the selected variant:
         org.gradle.usage = java-api
   ]

com.google.collections:google-collections -> com.google.guava:guava:17.0
   variant "default" [
      Requested attributes not found in the selected variant:
         org.gradle.usage = java-api
   ]
\--- compileClasspath

com.google.collections:google-collections:1.0 -> com.google.guava:guava:17.0
   variant "default" [
      Requested attributes not found in the selected variant:
         org.gradle.usage = java-api
   ]
\--- compileClasspath

com.google.guava:guava:17.0
   variant "default" [
      Requested attributes not found in the selected variant:
         org.gradle.usage = java-api
   ]
\--- compileClasspath
```

See [3-with-module-replacement/build.gradle.kts](3-with-module-replacement/build.gradle.kts)

### With dependency substitution rules

The dependency substitution rules can also provide custom reasons:

`gradle 4-with-dependency-substitution:dependencyInsight --dependency google`

```
> Task :4-with-dependency-substitution:dependencyInsight 
com.google.guava:guava:17.0 (Guava supercedes Google collections)
   variant "default" [
      Requested attributes not found in the selected variant:
         org.gradle.usage = java-api
   ]

com.google.collections:google-collections:1.0 -> com.google.guava:guava:17.0
   variant "default" [
      Requested attributes not found in the selected variant:
         org.gradle.usage = java-api
   ]
\--- compileClasspath

```

See [4-with-dependency-substitution/build.gradle.kts](4-with-dependency-substitution/build.gradle.kts)

### With component selection rules

Component selection rules now allow custom reasons too:

`gradle 5-with-component-selection:dependencyInsight --dependency google`

renders:

```
> Task :5-with-component-selection:dependencyInsight 
com.google.guava:guava:17.0 (Guava supercedes Google collections)
   variant "default" [
      Requested attributes not found in the selected variant:
         org.gradle.usage = java-api
   ]

com.google.collections:google-collections:1.0 -> com.google.guava:guava:17.0
   variant "default" [
      Requested attributes not found in the selected variant:
         org.gradle.usage = java-api
   ]
\--- compileClasspath
```

See [5-with-component-selection/build.gradle.kts](5-with-component-selection/build.gradle.kts)

### With experimental component metadata rules

Component metadata rules should supercede all previous rules at some point. They also allow, of course, specifying custom reasons:

`gradle 6-with-component-md-rule:dependencyInsight --dependency google`

renders:

```
> Task :6-with-component-md-rule:dependencyInsight 
com.google.code.gson:gson:1.4
   variant "runtime" [
      Requested attributes not found in the selected variant:
         org.gradle.usage = java-api
   ]
\--- org.reflections:reflections:0.9.5-RC2
     \--- compileClasspath

com.google.guava:guava:17.0 (Guava supercedes Google collections)
   variant "default" [
      Requested attributes not found in the selected variant:
         org.gradle.usage = java-api
   ]
\--- org.reflections:reflections:0.9.5-RC2
     \--- compileClasspath

```

See [6-with-component-md-rule/build.gradle.kts](6-with-component-md-rule/build.gradle.kts)

### Using published metadata

If Gradle metadata is used, reasons for both constraints and dependencies are published. It means that if
a published component contains a dependency for which a reason is set, the report will also show it:

`gradle 7-with-published-module:dependencyInsight --dependency google`

renders:

```
> Task :7-with-published-module:dependencyInsight 
com.google.guava:guava:20.0 (via constraint, Force upgrade to Guava 20)
   variant "default" [
      Requested attributes not found in the selected variant:
         org.gradle.usage = java-api
   ]
\--- org.dummy:dummy:1.0
     \--- compileClasspath

com.google.guava:guava:17.0 -> 20.0
   variant "default" [
      Requested attributes not found in the selected variant:
         org.gradle.usage = java-api
   ]
\--- compileClasspath
```

See [7-with-published-module/build.gradle.kts](7-with-published-module/build.gradle.kts)

## Access to all reasons

The dependency insight report only shows **one** reason, for readability. However, the
resolution result API gives access to all the selection reasons, including custom messages.

`gradle 8-with-resolution-result-api:resolveWithApi`

shows:

```
> Task :8-with-resolution-result-api:resolveWithApi 
Component project :8-with-resolution-result-api selection reasons:
   ROOT : root
Component com.google.guava:guava:17.0 selection reasons:
   REQUESTED : requested
   SELECTED_BY_RULE : Guava supercedes Google collections
   CONSTRAINT : Google collections is superceded by Guava
```