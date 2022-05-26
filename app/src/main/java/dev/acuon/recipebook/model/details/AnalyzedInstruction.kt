package dev.acuon.recipebook.model.details

data class AnalyzedInstruction(
    val name: String,
    val steps: List<Step>
)