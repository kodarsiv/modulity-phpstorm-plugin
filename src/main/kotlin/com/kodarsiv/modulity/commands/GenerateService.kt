package com.kodarsiv.modulity.commands

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.OSProcessHandler
import com.intellij.execution.process.ProcessAdapter
import com.intellij.execution.process.ProcessEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import java.io.File
import java.util.concurrent.CompletableFuture

class GenerateService {

    @Throws(IllegalStateException::class)
    private fun checkPhpAvailable() {
        val process = Runtime.getRuntime().exec("php -v")
        if (process.waitFor() != 0) {
            throw IllegalStateException("PHP is not available.")
        }
    }

    @Throws(IllegalStateException::class)
    private fun checkArtisanAvailable(project: Project) {
        val projectPath = project.basePath ?: throw IllegalStateException("Project base path is not available.")

        val artisanFile = File("$projectPath/artisan")
        if (!artisanFile.exists()) {
            throw IllegalStateException("Artisan is not available.")
        }
    }

    @Throws(IllegalStateException::class)
    private fun checkModulityInstalled(project: Project) {
        val composerJsonFile = File(project.basePath + "/composer.json")
        if (!composerJsonFile.exists()) {
            throw IllegalStateException("composer.json not found.")
        }

        val composerJson = composerJsonFile.readText()
        if (!composerJson.contains("\"tanerincode/modulity\"")) {
            throw IllegalStateException("Modulity package is not installed.")
        }
    }

    @Throws(IllegalStateException::class)
    fun runArtisanCommand(workingDirectory: String, modulityArgument: String, moduleName: String, fileName: String): CompletableFuture<String> {
        val future = CompletableFuture<String>()

        ApplicationManager.getApplication().executeOnPooledThread {
            try {
                val commandLine = GeneralCommandLine("php", "artisan", modulityArgument, moduleName, fileName)
                    .withWorkDirectory(workingDirectory)

                val processHandler = OSProcessHandler(commandLine)
                val output = StringBuilder()

                processHandler.addProcessListener(object : ProcessAdapter() {
                    override fun onTextAvailable(event: ProcessEvent, outputType: Key<*>) {
                        output.append(event.text)
                    }
                })

                processHandler.startNotify()
                processHandler.waitFor()

                if (output.toString().contains("Could not open input file: artisan")) {
                    future.completeExceptionally(IllegalStateException("Failed to create service. Could not open input file: artisan"))
                } else {
                    future.complete(output.toString())
                }
            } catch (e: Exception) {
                future.completeExceptionally(e)
            }
        }

        return future
    }

    @Throws(IllegalStateException::class)
    fun generateService(project: Project, moduleName: String, serviceName: String) {
        checkPhpAvailable()
        checkArtisanAvailable(project)
        checkModulityInstalled(project)

        val projectPath = project.basePath ?: throw IllegalStateException("Project base path is not available.")
        val modulityArgument = "modulity:service"

        val future = runArtisanCommand(projectPath, modulityArgument, moduleName, serviceName)

        future.handle { result: String?, exception: Throwable? ->
            if (exception != null) {
                throw IllegalStateException("Failed to run the command: ${exception.message}")
            } else {
                println("The command successfully ran with output: $result")
                null
            }
        }

    }

}
