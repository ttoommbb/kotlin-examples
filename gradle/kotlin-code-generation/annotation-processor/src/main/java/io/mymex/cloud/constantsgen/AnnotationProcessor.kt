package io.mymex.cloud.constantsgen

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import org.yaml.snakeyaml.Yaml
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("io.mymex.cloud.constantsgen.ConstantsGen")
@SupportedOptions(AnnotationProcessor.KAPT_KOTLIN_GENERATED_OPTION_NAME)
class AnnotationProcessor : AbstractProcessor() {
    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment): Boolean {
        val annotatedWithConstantsGen = roundEnv.getElementsAnnotatedWith(ConstantsGen::class.java)
        if (annotatedWithConstantsGen.isEmpty()) return false

        annotatedWithConstantsGen.forEach {
            val ymlFile = it.getAnnotation(ConstantsGen::class.java).value
            val yml = Yaml().load<Map<String, Any>>(File(ymlFile).readText())
            val packageName: String = yml["package"].toString()
//            val classes = yml["classes"]
            generateClass("GenConstants", packageName)
         }
        return true
    }

    private fun generateClass(className: String, pack: String) {
        val java = PropertySpec.builder("java", String::class,
                KModifier.CONST)
                .initializer(""""value"""")
                .build()

        val file = FileSpec.builder(pack, className)
                .addType(TypeSpec.objectBuilder(className)
                        .addProperty(java)
                        .build())

                .build()

        val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
        file.writeTo(File(kaptKotlinGeneratedDir))
    }

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }
}
