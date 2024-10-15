package org.example;

import java.util.Stack;

public class HomeWork {

    private static final char DECIMAL_SEPARATOR = '.';

    /**
     * <h1>Задание 1.</h1>
     * Требуется реализовать метод, который по входной строке будет вычислять математические выражения.
     * <br/>
     * Операции: +, -, *, / <br/>
     * Функции: sin, cos, sqr, pow <br/>
     * Разделители аргументов в функции: , <br/>
     * Поддержка скобок () для описания аргументов и для группировки операций <br/>
     * Пробел - разделитель токенов, пример валидной строки: "1 + 2 * ( 3 - 4 )" с результатом -1.0 <br/>
     * <br/>
     * sqr(x) = x^2 <br/>
     * pow(x,y) = x^y
     */
    public double calculate(String expr) {
        return evaluateExpression(expr);
    }

    private double evaluateExpression(String expr) {
        // Удаляем пробелы
        expr = expr.replaceAll(" ", "");
        return parseExpression(expr);
    }

    private double parseExpression(String expr) {
        Stack<Double> values = new Stack<>();
        Stack<Character> ops = new Stack<>();

        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);

            // Если текущий символ - число, извлекаем его
            if (Character.isDigit(c) || c == DECIMAL_SEPARATOR) {
                StringBuilder sb = new StringBuilder();
                while (i < expr.length() && (Character.isDigit(expr.charAt(i)) || expr.charAt(i) == DECIMAL_SEPARATOR)) {
                    sb.append(expr.charAt(i++));
                }
                values.push(Double.parseDouble(sb.toString()));
                i--; // Уменьшаем индекс, так как цикл увеличивает его
            }
            // Если текущий символ - открывающая скобка
            else if (c == '(') {
                ops.push(c);
            }
            // Если текущий символ - закрывающая скобка
            else if (c == ')') {
                while (ops.peek() != '(') {
                    values.push(applyOperation(ops.pop(), values.pop(), values.pop()));
                }
                ops.pop(); // Удаляем '('
            }
            // Если текущий символ - оператор
            else if (isOperator(c)) {
                while (!ops.isEmpty() && precedence(ops.peek()) >= precedence(c)) {
                    values.push(applyOperation(ops.pop(), values.pop(), values.pop()));
                }
                ops.push(c);
            }
            // Обработка функций
            else if (Character.isLetter(c)) {
                StringBuilder func = new StringBuilder();
                while (i < expr.length() && Character.isLetter(expr.charAt(i))) {
                    func.append(expr.charAt(i++));
                }
                i--; // Уменьшаем индекс
                if (func.toString().equals("sqr")) {
                    i += 2; // Пропускаем '('
                    var arg = Double.parseDouble(expr.substring(i, expr.indexOf(')', i)));
                    values.push(arg * arg);
                    while (i < expr.length() && expr.charAt(i) != ')') i++;
                } else if (func.toString().equals("pow")) {
                    i += 2; // Пропускаем "("
                    var arg = Double.parseDouble(expr.substring(i, expr.indexOf(')', i)));
                    values.push(arg * arg);
                    while (i < expr.length() && expr.charAt(i) != ')') i++;
                } else if (func.toString().equals("sin")) {
                    i += 2; // Пропускаем "("
                    double arg = Double.parseDouble(expr.substring(i, expr.indexOf(')')));
                    values.push(Math.sin(arg));
                    while (i < expr.length() && expr.charAt(i) != ')') i++;
                } else if (func.toString().equals("cos")) {
                    i += 2; // Пропускаем "("
                    double arg = Double.parseDouble(expr.substring(i, expr.indexOf(')')));
                    values.push(Math.cos(arg));
                    while (i < expr.length() && expr.charAt(i) != ')') i++;
                }
            }
        }

        while (!ops.isEmpty()) {
            values.push(applyOperation(ops.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private int precedence(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0;
        }
    }

    private double applyOperation(char op, double b, double a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) throw new UnsupportedOperationException("Cannot divide by zero");
                return a / b;
        }
        return 0;
    }
}
