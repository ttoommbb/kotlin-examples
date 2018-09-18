package org.kotlin.test

import org.kotlin.annotationProcessor.TestAnnotation

@TestAnnotation("constants.yml")
class SimpleClass {



    companion object {

        fun main(args: Array<String>) {
            println(SimpleClass.simpleClassName)
        }
    }
}