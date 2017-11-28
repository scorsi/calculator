package com.scorsi.calculator.parser

enum class Operator {
    ADD,
    SUB,
    DIV,
    MUL,
    MOD;

    companion object {
        fun from(token: Token): Operator = when (token.type) {
            TokenType.MOD -> MOD
            TokenType.MUL -> MUL
            TokenType.DIV -> DIV
            TokenType.MINUS -> SUB
            TokenType.PLUS -> ADD
            else -> throw Error("Invalid operator")
        }
    }
}

sealed class AST
data class Op constructor(val left: AST, val op: Operator, val right: AST) : AST()
data class Integer constructor(val value: Int) : AST()

class Parser constructor(private val lexer: Lexer) {

    private var current: Token? = lexer.readToken()


    private fun error(): Nothing = throw Error("Invalid syntax")

    private fun eat(type: TokenType) {
        if (current != null && current?.type == type)
            current = lexer.readToken()
        else error()
    }

    private fun factor(): AST = when (current) {
        null -> error()
        else -> when (current?.type) {
            TokenType.INTEGER -> {
                val token: Token = current as Token
                eat(TokenType.INTEGER)
                Integer(token.value as Int)
            }
            TokenType.LPAREN -> {
                eat(TokenType.LPAREN)
                val node = expr()
                eat(TokenType.RPAREN)
                node
            }
            else -> error()
        }
    }

    private fun term(): AST {
        var node = factor()
        while (current?.type == TokenType.MUL || current?.type == TokenType.MOD || current?.type == TokenType.DIV) {
            val token: Token = current as Token
            eat(token.type)
            node = Op(left = node, op = Operator.from(token), right = factor())
        }
        return node
    }

    private fun expr(): AST {
        var node = term()
        while (current?.type == TokenType.MINUS || current?.type == TokenType.PLUS) {
            val token: Token = current as Token
            eat(token.type)
            node = Op(left = node, op = Operator.from(token), right = term())
        }
        return node
    }

    fun parse(): AST = expr()

}
