buildscript {
	repositories {
		google()
		jcenter()
		maven(url = "https://plugins.gradle.org/m2/")
		maven(url="http://repo1.maven.org/maven2/")

	}
	dependencies {
		classpath(Dependencies.detektPlugin)
//		classpath("org.kt3k.gradle.plugin:coveralls-gradle-plugin:1.0.2")
	}
}
plugins {
	kotlin("jvm") version Versions.kotlin
	id("jacoco")
	id(Dependencies.detekt) version Versions.detekt
//	id("org.kt3k.gradle.plugin") version "1.0.2"
}

group = "com.ikarimeister.katas"
version = "1.0-SNAPSHOT"

repositories {
	mavenCentral()
}

tasks {
	compileKotlin {
		kotlinOptions.jvmTarget = "1.8"
	}
	compileTestKotlin {
		kotlinOptions.jvmTarget = "1.8"
	}
	jacocoTestReport {
		reports {
			xml.isEnabled = false
			csv.isEnabled = false
			html.isEnabled = true
			html.destination = file("$buildDir/reports/coverage")
		}
	}
	jacocoTestCoverageVerification {
		violationRules {
			rule {
				limit {
					minimum = "0.9".toBigDecimal()
				}
			}
		}
	}
}

val ktlint by configurations.creating

dependencies {
	ktlint(Dependencies.ktlint)
}

tasks.register<JavaExec>("ktlint") {
	group = "verification"
	description = "Check Kotlin code style."
	classpath = ktlint
	main = "com.pinterest.ktlint.Main"
	args("*/src/**/*.kt")
}

tasks.register<JavaExec>("ktlintFormat") {
	group = "formatting"
	description = "Fix Kotlin code style deviations."
	classpath = ktlint
	main = "com.pinterest.ktlint.Main"
	args("-F", "*/src/**/*.kt")
}

detekt {
	toolVersion = Versions.detekt
	parallel = false
	config = files("$projectDir/config/detekt/config.yml")
	buildUponDefaultConfig = false
	baseline = file("$projectDir/config/detekt/baseline.xml")
	disableDefaultRuleSets = false
	debug = false
	ignoreFailures = false
	reports {
		xml {
			enabled = true
			destination = file("build/reports/detekt.xml")
		}
		html {
			enabled = true
			destination = file("build/reports/detekt.html")
		}
		txt {
			enabled = true
			destination = file("build/reports/detekt.txt")
		}
		custom {
			reportId = "CustomJsonReport"
			destination = file("build/reports/detekt.json") // Path where report will be stored
		}
	}
}
task<io.gitlab.arturbosch.detekt.Detekt>("detektFailFast") {
	description = "Runs a failfast detekt build."
	reports {
		xml {
			destination = file("build/reports/failfast.xml")
		}
		html.destination = file("build/reports/failfast.html")
	}
	include("**/*.kt")
	include("**/*.kts")
	exclude("resources/")
	exclude("build/")
}

val detektFF by configurations.creating

val testCoverage by tasks.registering {
	group = "verification"
	description = "Runs the unit tests with coverage."

	dependsOn(":test", ":jacocoTestReport", ":jacocoTestCoverageVerification")
	val jacocoTestReport = tasks.findByName("jacocoTestReport")
	jacocoTestReport?.mustRunAfter(tasks.findByName("test"))
	tasks.findByName("jacocoTestCoverageVerification")?.mustRunAfter(jacocoTestReport)
}


tasks.named("check") {
	dependsOn(ktlint)
	dependsOn(detektFF)
}
