pluginManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        google()
        maven("https://jitpack.io")
        maven {
            url = uri("https://git.naijun.dev/api/packages/revanced/maven")

            credentials<HttpHeaderCredentials>(HttpHeaderCredentials::class.java) {
                name = "Authorization"
                value = "token ${extra["gitea.accessToken"] as String? ?: System.getenv("GITEA_TOKEN")}"
            }

            authentication {
                register("header", HttpHeaderAuthentication::class.java)
            }
        }
        maven {
            // A repository must be specified for some reason. "registry" is a dummy.
            url = uri("https://maven.pkg.github.com/revanced/registry")
            credentials {
                username = System.getenv("GITHUB_ACTOR") ?: extra["gpr.user"] as String?
                password = System.getenv("GITHUB_TOKEN") ?: extra["gpr.key"] as String?
            }
        }
    }
}

rootProject.name = "revanced-manager"
include(":app", ":api")
