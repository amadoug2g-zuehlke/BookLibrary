package com.example.core.domain.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class IndustryIdentifierTest {
    @Test
    fun testIndustryIdentifierModel() {
        val type = "type"
        val identifier =  "identifier"

        val industryIdentifier = IndustryIdentifier(type, identifier)

        assertEquals("type", industryIdentifier.type)
        assertEquals("identifier", industryIdentifier.identifier)
    }
}