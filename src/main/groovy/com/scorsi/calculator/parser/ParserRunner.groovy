package com.scorsi.calculator.parser

import com.scorsi.calculator.Interpreter


class ParserRunner implements Runnable {

    String input

    ParserRunner(String input) {
        this.input = input
    }

    void run() {
        println(new Interpreter(input).interpret())
    }

}
