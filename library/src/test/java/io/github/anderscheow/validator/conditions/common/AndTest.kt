package io.github.anderscheow.validator.conditions.common

import androidx.annotation.StringRes
import io.github.anderscheow.validator.conditions.Condition
import io.github.anderscheow.validator.rules.common.MaxRule
import io.github.anderscheow.validator.rules.common.MinRule
import io.github.anderscheow.validator.rules.regex.DigitsRule
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AndTest {

    private lateinit var and: Condition

    @Before
    @Throws(Exception::class)
    fun setUp() {
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
    }

    @Test
    @Throws(Exception::class)
    fun validate_MatchThreeConditionsOutOfThree_ReturnTrue() {
        and = And()
                .add(MinRule(5)) // match
                .add(MaxRule(10)) // match
                .add(DigitsRule()) // match

        val sample = "1234567"

        assertTrue(and.validate(sample))
    }

    @Test
    @Throws(Exception::class)
    fun validate_MatchTwoConditionsOutOfThree_ReturnFalse() {
        and = And()
                .add(MinRule(5)) // A
                .add(MaxRule(10)) // B
                .add(DigitsRule()) // C

        val samples = arrayOf("abcde", // Match A,B
                "12345678901", // Match A,C
                "123")// Match B,C

        for (sample in samples) {
            assertFalse("This sample failed: $sample", and.validate(sample))
        }
    }

    @Test
    @Throws(Exception::class)
    fun validate_MatchOneConditionOutOfThree_ReturnFalse() {
        and = And()
                .add(MinRule(5)) // A
                .add(MaxRule(10)) // B
                .add(DigitsRule()) // C

        val samples = arrayOf("abc 12", // Match A
                "test-123", // Match B
                "123456789012345")// Match C

        for (sample in samples) {
            assertFalse("This sample failed: $sample", and.validate(sample))
        }
    }

    @Test
    @Throws(Exception::class)
    fun validate_MatchZeroConditionOutOfThree_ReturnFalse() {
        and = And()
                .add(MinRule(5)) // A
                .add(MaxRule(10)) // B
                .add(DigitsRule()) // C

        val samples = arrayOf("testing 123, how are you")

        for (sample in samples) {
            assertFalse("This sample failed: $sample", and.validate(sample))
        }
    }

    @Test
    @Throws(Exception::class)
    fun errorMessage_DefaultErrorMessage() {
        val errorMessage = "Does not match 'And' condition"

        and = And()

        assertEquals(errorMessage, and.getErrorMessage())
    }

    @Test
    @Throws(Exception::class)
    fun errorMessage_CustomErrorMessage() {
        val errorMessage = "This is a custom error message"

        and = And(errorMessage)

        assertEquals(errorMessage, and.getErrorMessage())
    }

    @Test
    @Throws(Exception::class)
    fun errorMessage_CustomErrorRes() {
        @StringRes val errorRes = 0

        and = And(errorRes)

        assertEquals(errorRes, and.getErrorRes())
    }
}