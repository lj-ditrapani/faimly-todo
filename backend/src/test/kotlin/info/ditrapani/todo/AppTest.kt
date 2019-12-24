package info.ditrapani.todo

import io.kotlintest.specs.FreeSpec
import kotlin.test.assertNotNull

class AppTest : FreeSpec({
    "testAppHasAGreeting" {
        val classUnderTest = App()
        assertNotNull(classUnderTest.greeting, "app should have a greeting")
    }
})
