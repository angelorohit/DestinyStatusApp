plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.oss.licenses) apply false
}

task("addPreCommitGitHookOnBuild") {
    println("⚈ ⚈ ⚈ Running Add Pre-Commit Git Hook Script on Build ⚈ ⚈ ⚈")
    // Copy the pre-commit hook
    copy {
        from("./.scripts/pre-commit")
        into("./.git/hooks")
    }

    // Make the pre-commit hook executable
    val hookFile = file("./.git/hooks/pre-commit")
    hookFile.setExecutable(true)

    println("✅ Added Pre Commit Git Hook Script.")
}
