package com.scorsi.calculator

import com.scorsi.calculator.parser.*

class Interpreter constructor(private val parser: Parser) {

    private fun compute(node: AST): Int = when (node) {
        is Integer -> node.value
        is Op -> when (node.op) {
            Operator.ADD -> compute(node.left) + compute(node.right)
            Operator.SUB -> compute(node.left) - compute(node.right)
            Operator.DIV -> compute(node.left) / compute(node.right)
            Operator.MUL -> compute(node.left) * compute(node.right)
            Operator.MOD -> compute(node.left) % compute(node.right)
        }
    }

    fun interpret(): String = compute(parser.parse()).toString()

}