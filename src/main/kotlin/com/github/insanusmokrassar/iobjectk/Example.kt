package com.github.insanusmokrassar.iobjectk

import java.io.FileInputStream
import java.util.*
import java.util.logging.LogManager

fun main(args: Array<String>) {
    println(Arrays.toString(args))
}

/**
 * Need "LOGGER_CONFIG_PATH" environment variable which set the place where was put .properties file with config of logger
 */
fun loadLoggerConfig() {
    FileInputStream(System.getenv("LOGGER_CONFIG_PATH")).use {
        LogManager.getLogManager().readConfiguration(it)
    }
}
