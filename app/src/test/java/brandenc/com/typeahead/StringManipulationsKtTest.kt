package brandenc.com.typeahead

import brandenc.com.typeahead.Models.Performer
import brandenc.com.typeahead.StringManipulations.createEventName
import org.junit.Test

import org.junit.Assert.*

class StringManipulationsKtTest {

    @Test
    fun createEventNameBase() {
        // Given
        val performer1 = Performer.create("performer1", "test")
        val performerList = listOf(performer1)

        // When
        val eventName = createEventName(performerList)

        // Then
        assertEquals("performer1", eventName)
    }

    @Test
    fun createEventName2Events() {
        // Given
        val performer1 = Performer.create("performer1", "test")
        val performer2 = Performer.create("performer2", "test")
        val performerList = listOf(performer1, performer2)

        // When
        val eventName = createEventName(performerList)

        // Then
        assertEquals("performer1, performer2", eventName)
    }

    @Test
    fun createEventName3Events() {
        // Given
        val performer1 = Performer.create("performer1", "test")
        val performer2 = Performer.create("performer2", "test")
        val performer3 = Performer.create("performer3", "test")
        val performerList = listOf(performer1, performer2, performer3)

        // When
        val eventName = createEventName(performerList)

        //Then
        assertEquals("performer1, performer2, performer3", eventName)
    }

    @Test
    fun createEventName3EventsBadOrder() {
        // Given
        val performer1 = Performer.create("performer1", "test")
        val performer2 = Performer.create("performer2", "test")
        val performer3 = Performer.create("performer3", "test")
        val performerList = listOf(performer1, performer2, performer3)

        // When
        val eventName = createEventName(performerList)

        // Then
        assertFalse("performer1, performer3, performer2" == eventName)
    }

}