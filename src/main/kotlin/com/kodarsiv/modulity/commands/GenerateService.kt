package com.kodarsiv.modulity.commands

import java.io.BufferedReader
import java.io.InputStreamReader

class GenerateService {

    @Throws(IllegalStateException::class)
    private fun checkPhpAvailable() {
        val process = Runtime.getRuntime().exec("php -v")
        if (process.waitFor() != 0) {
            throw IllegalStateException("PHP is not available.")
        }
    }

    @Throws(IllegalStateException::class)
    private fun checkArtisanAvailable() {
        val process = Runtime.getRuntime().exec("php artisan --version")
        if (process.waitFor() != 0) {
            throw IllegalStateException("Artisan is not available.")
        }
    }

    @Throws(IllegalStateException::class)
    private fun checkCommandAvailable(command: String) {
        val process = Runtime.getRuntime().exec(arrayOf("sh", "-c", "php artisan list --raw | grep $command"))
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val output = reader.readLine()
        if (process.waitFor() != 0 || output == null || !output.contains(command)) {
            throw IllegalStateException("The command '$command' is not available.")
        }
    }

    @Throws(IllegalStateException::class)
    private fun runArtisanCommand(command: String) {
        val process = Runtime.getRuntime().exec("php artisan $command")
        if (process.waitFor() != 0) {
            throw IllegalStateException("Failed to run the command '$command'.")
        }
    }

    @Throws(IllegalStateException::class)
    fun generateService() {
        checkPhpAvailable()
        checkArtisanAvailable()

        val command = "modulity:service"
        checkCommandAvailable(command)
        runArtisanCommand(command)

        println("The command '$command' ran successfully.")
    }
}
