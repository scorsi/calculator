package com.scorsi.calculator

import com.scorsi.calculator.parser.ParserRunner

class Main {

    static void main(String[] args) {
        def input = new Scanner(System.in)
        while (input.hasNext()) {
            new Thread(new ParserRunner(input.nextLine())).start()
        }
    }

}
