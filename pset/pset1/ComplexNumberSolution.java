public class ComplexNumberSolution {
    public static void main(String[] args) {
        ComplexNumber c1 = new ComplexNumber(7.2, 3.0);
        ComplexNumber c2 = new ComplexNumber(12.1, 6.7);
        System.out.println(ComplexNumber.add(c1, c2).toString());
        System.out.println(ComplexNumber.subtract(c1, c2).toString());
        System.out.println(ComplexNumber.multiply(c1, c2).toString());
        System.out.println(ComplexNumber.divide(c1, c2).toString());
    }
}


class ComplexNumber {
    private double realValue;
    private double imagValue;
    private static ComplexNumber z = new ComplexNumber();

    public ComplexNumber() {
        this.realValue = 0;
        this.imagValue = 0;
    }

    public ComplexNumber(double realValue, double imagValue) {
        this.realValue = realValue;
        this.imagValue = imagValue;
    }

    @Override
    public String toString() {
        if (this.imagValue > 0) {
            if (this.realValue == 0)
                return this.imagValue + "i";
            else
                return this.realValue + " + " + this.imagValue + "i";
        } else if (this.imagValue == 0) {
            return this.realValue + "";
        } else {
            if (this.realValue == 0)
                return "-" + -this.imagValue + "i";
            else
                return this.realValue + " - " + -this.imagValue + "i";
        }
    }

    public static ComplexNumber add(ComplexNumber c1, ComplexNumber c2) {
        z.realValue = c1.realValue + c2.realValue;
        z.imagValue = c1.imagValue + c2.imagValue;
        return z;
    }

    public static ComplexNumber subtract(ComplexNumber c1, ComplexNumber c2) {
        z.realValue = c1.realValue - c2.realValue;
        z.imagValue = c1.imagValue - c2.imagValue;
        return z;
    }

    public static ComplexNumber multiply(ComplexNumber c1, ComplexNumber c2) {
        z.realValue = (c1.realValue * c2.realValue) - (c1.imagValue * c2.imagValue);
        z.imagValue = (c1.realValue * c2.imagValue) + (c1.imagValue * c2.realValue);
        return z;
    }

    public static ComplexNumber divide(ComplexNumber c1, ComplexNumber c2) {
        double r = Math.pow(c2.realValue, 2) + Math.pow(c2.imagValue, 2);
        z.realValue = ((c1.realValue * c2.realValue) + (c1.imagValue * c2.imagValue)) / r;
        z.imagValue = ((c1.imagValue * c2.realValue) - (c1.realValue * c2.imagValue)) / r;
        return z;
    }

    public static ComplexNumber toStandardForm(RectForm r) {
        z.realValue = r.a;
        z.imagValue = r.b;
        return z;
    }

    public static ComplexNumber toStandardForm(PolarForm p) {
        z.realValue = p.r * Math.cos(p.theta);
        z.imagValue = p.r * Math.sin(p.theta);
        return z;
    }
}


class RectForm {
    public double a;
    public double b;
}


class PolarForm {
    public double r;
    public double theta;
}
