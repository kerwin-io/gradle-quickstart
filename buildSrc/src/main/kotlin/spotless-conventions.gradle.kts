plugins {
	id("com.diffplug.spotless")
}

//val license: License by rootProject.extra

spotless {

	format("misc") {
		target("*.gradle.kts", "buildSrc/**/*.gradle.kts", "*.gitignore")
		targetExclude("buildSrc/build/**")
		indentWithTabs()
		trimTrailingWhitespace()
		endWithNewline()
	}

	format("documentation") {
		target("*.adoc", "*.md", "src/**/*.adoc", "src/**/*.md")
		trimTrailingWhitespace()
		endWithNewline()
	}

	pluginManager.withPlugin("java") {
		// val configDir = rootProject.layout.projectDirectory.dir("tools/maven")
		//val javaFormatterConfigFile = configDir.file("spotless-formatter-settings.xml")
		java {
//			licenseHeaderFile(license.headerFile, "(package|import|open|module) ")
			removeUnusedImports()

			// eclipse().configFile(javaFormatterConfigFile)
			googleJavaFormat("1.16.0").aosp().reflowLongStrings()

			importOrder("io.kerwin.gradle.quick", "io.kerwin", "", "javax", "java", "scala", "\\#")
			trimTrailingWhitespace()
			endWithNewline()

			custom("noWildcardImports") {
				if (it.contains("*;\n")) {
					throw AssertionError("No wildcard imports allowed")
				}
				it
			}
			bumpThisNumberIfACustomStepChanges(1)
		}
	}
}
