package com.scorsi.calculator.parser

import java.lang.StringIndexOutOfBoundsException

enum class TokenType(val s: String) {
    INTEGER("Integer"),
    LPAREN("Left parenthesis"),
    RPAREN("Right parenthesis"),
    MINUS("Minus operator"),
    PLUS("Plus operator"),
    MUL("Mul operator"),
    DIV("Div operator"),
    MOD("Mod operator"),
    EOF("EOF");

    override fun toString(): String = s

}

data class Token constructor(val type: TokenType, val value: Any? = null)

class Lexer constructor(private val text: String) {

    var pos: Int = 0
    var current: Char? = text[0]


    private fun Char.isDigit(): Boolean = this.toString().matches("""[0-9]""".toRegex())
    private fun Char.isWhitespace(): Boolean = this.toString().matches("""[ \n\t]""".toRegex())

    private fun error(c: Char): Nothing = throw Error("Invalid character \"$c\" at pos $pos")

    private fun advance() = try {
        (++pos).let {
            current = when {
                it > text.length -> null
                else -> text[pos]
            }
        }
    } catch (_: StringIndexOutOfBoundsException) {
        current = null
    }

    private fun skipWhitespace() {
        while (current != null && current!!.isWhitespace())
            advance()
    }

    private fun readInteger(): Int {
        var res = ""
        while (current != null && current!!.isDigit()) {
            res += current
            advance()
        }
        return res.toInt()
    }

    fun readToken(): Token = when {
        current == null -> Token(TokenType.EOF)
        current!!.isDigit() -> Token(TokenType.INTEGER, readInteger())
        current!!.isWhitespace() -> { skipWhitespace(); readToken() }
        else -> when (current) {
            '+' -> { advance(); Token(TokenType.PLUS) }
            '-' -> { advance(); Token(TokenType.MINUS) }
            '/' -> { advance(); Token(TokenType.DIV) }
            '*' -> { advance(); Token(TokenType.MUL) }
            '%' -> { advance(); Token(TokenType.MOD) }
            '(' -> { advance(); Token(TokenType.LPAREN) }
            ')' -> { advance(); Token(TokenType.RPAREN) }
            else -> error(current as Char)
        }
    }

}