package com.scorsi.calculator

import com.scorsi.calculator.parser.Lexer
import com.scorsi.calculator.parser.Parser

class Calculator {

    fun interpret(input: String = ""): String =
            Lexer(input).let { lexer -> Parser(lexer).let { parser -> Interpreter(parser).interpret() } }

}