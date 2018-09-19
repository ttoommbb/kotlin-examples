package org.kotlin.test

import io.mymex.cloud.constantsgen.ConstantsGen
import javax.xml.ws.Action

@ConstantsGen("constants.yml")
class SimpleClass {

    companion object {

        @Action(input = SomeObj.some )
        fun main(args: Array<String>) {
            println(SimpleClass::class.java)
            println(args)

        }
    }
}

object SomeObj {
    const val some = "value"

}