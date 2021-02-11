import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Scanner;
import java.util.regex.MatchResult;

public class ComplexNumberCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your expression to be calculated: ");
        String expression = scanner.nextLine();
        boolean matchExists = Pattern.compile("\\(-?\\d+(\\+|\\-)\\d+[i]\\)(\\+|\\-|\\*|\\/)\\(-?\\d+(\\+|\\-)\\d+[i]\\)", Pattern.CASE_INSENSITIVE).matcher(expression).find();

        Pattern operatorPattern = Pattern.compile("\\)(\\+|\\-|\\*|\\/)\\(");
        Pattern numberPattern = Pattern.compile("-?\\d+");

        if (!matchExists) {
            System.out.println("Please input a valid calculation expression of two complex numbers in rectangular form! For example: (1+2i)*(2+1i).\n");
            scanner.close();
            return;
        }

        Matcher operatorMatcher = operatorPattern.matcher(expression);

        if (operatorMatcher.find()) {
            int operatorSpan = operatorMatcher.start();
            char operator = expression.charAt(operatorSpan + 1);
            String[] numbers = numberPattern.matcher(expression).results().map(MatchResult::group).toArray(String[]::new);
            ComplexNumber firstNum = new ComplexNumber((double) Integer.parseInt(numbers[0]), (double) Integer.parseInt(numbers[1]));
            ComplexNumber secondNum = new ComplexNumber((double) Integer.parseInt(numbers[2]), (double) Integer.parseInt(numbers[3]));
            ComplexNumber output = new ComplexNumber(0.0, 0.0);

            if (operator == '+') {
                output = firstNum.add(secondNum);
            }
            else if (operator == '-') {
                output = firstNum.subtract(secondNum);
            }
            else if (operator == '*') {
                output = firstNum.multiply(secondNum);
            }
            else if (operator == '/') {
                output = firstNum.divide(secondNum);
            }

            System.out.println("Result: " + output.toString());
        }
        else {
            System.out.println("Invalid input syntax has been detected. Please enter a valid input expression!");
        }

        scanner.close();
    }
}

class ComplexNumber {
    private double a = 0.0; // Real part
    private double b = 0.0; // Imaginary part

    ComplexNumber(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public double getReal() {
        return this.a;
    }

    public double getImaginary() {
        return this.b;
    }

    // Pretty-print and format the string output
    public String toString() {
        if (this.getImaginary() > 0) {
            if (this.getReal() == 0)
                return this.getImaginary() + "i";
            else return this.getReal() + " + " + this.getImaginary() + "i";
        } else if (this.getImaginary() == 0) {
            return this.getReal() + "";
        } else {
            if(this.getReal() == 0)
                return "-" + -this.getImaginary() + "i";
            else return this.getReal() + " - " + -this.getImaginary() + "i";
        }
    }

    public ComplexNumber add(ComplexNumber other) {
        return new ComplexNumber(this.getReal() + other.getReal(), this.getImaginary() + other.getImaginary());
    }

    public ComplexNumber subtract(ComplexNumber other) {
        return new ComplexNumber(this.getReal() - other.getReal(), this.getImaginary() - other.getImaginary());
    }
    
    public ComplexNumber multiply(ComplexNumber other) {
        return new ComplexNumber((this.getReal() * other.getReal() - this.getImaginary() * other.getImaginary()), (this.getReal() * other.getImaginary() + this.getImaginary() * other.getReal()));
    }

    public ComplexNumber divide(ComplexNumber other) {
        return new ComplexNumber((this.getReal() * other.getReal() + this.getImaginary() * other.getImaginary()) / (other.getReal() * other.getReal() + other.getImaginary() * other.getImaginary()), (this.getImaginary() * other.getReal() - this.getReal() * other.getImaginary()) / (other.getReal() * other.getReal() + other.getImaginary() * other.getImaginary()));
    }
}