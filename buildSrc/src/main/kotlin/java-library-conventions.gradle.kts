plugins {
	`java-library`
	eclipse
	idea
	checkstyle
	id("base-conventions")
	id("io.freefair.lombok")
//	id("jacoco-java-conventions")
}

val javaVersion = JavaVersion.current()


// add test
tasks.test {
	useJUnitPlatform()
}

dependencies {
	testImplementation(bundleFromLibs("junit-jupiter"))
}

java {
	modularity.inferModulePath.set(false)
}


tasks.withType<AbstractArchiveTask>().configureEach {
	isPreserveFileTimestamps = false
	isReproducibleFileOrder = true
	dirMode = Integer.parseInt("0755", 8)
	fileMode = Integer.parseInt("0644", 8)
}

tasks.withType<JavaCompile>().configureEach {
	options.encoding = "UTF-8"
}

tasks.compileJava {
	// See: https://docs.oracle.com/en/java/javase/12/tools/javac.html
	options.compilerArgs.addAll(listOf(
			"-Xlint:all", // Enables all recommended warnings.
//			"-Werror" // Terminates compilation when warnings occur.
	))
}

tasks.compileTestJava {
	// See: https://docs.oracle.com/en/java/javase/12/tools/javac.html
	options.compilerArgs.addAll(listOf(
			"-Xlint", // Enables all recommended warnings.
			"-Xlint:-overrides", // Disables "method overrides" warnings.
//			"-Werror", // Terminates compilation when warnings occur.
			"-parameters" // Generates metadata for reflection on method parameters.
	))
}

// checkstyle
checkstyle {
	toolVersion = requiredVersionFromLibs("checkstyle")
	configDirectory.set(rootProject.layout.projectDirectory.dir("tools/maven"))
}

tasks {
	checkstyle {
		config = resources.text.fromFile(checkstyle.configDirectory.file("checkstyle.xml"))
	}
}
