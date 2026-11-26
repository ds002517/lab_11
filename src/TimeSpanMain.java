import exceptions.NegativeTimeSpanException;
import exceptions.IllegalInputTimeSpanException;
import exceptions.IllegalScaleFactorException;

public class TimeSpanMain {

    public static void main(String[] args) {

        // Перевірка коректних операцій

        TimeSpan span = new TimeSpan(2, 30);

        System.out.println("Initial TimeSpan:");
        System.out.println(span.getHours());
        System.out.println(span.getMinutes());

        span.add(45);

        System.out.println("\nAfter add(45):");
        System.out.println(span.getHours());
        System.out.println(span.getMinutes());

        span.subtract(30);

        System.out.println("\nAfter subtract(30):");
        System.out.println(span.getHours());
        System.out.println(span.getMinutes());

        span.scale(2);

        System.out.println("\nAfter scale(2):");
        System.out.println(span.getHours());
        System.out.println(span.getMinutes());

        // Перевірка некоректних вхідних даних

        try {
            TimeSpan invalid = new TimeSpan(-1, 30);
        } catch (IllegalInputTimeSpanException e) {
            System.out.println("\nIllegalInputTimeSpanException:");
            System.out.println(e.getMessage());
        }

        try {
            TimeSpan invalid = new TimeSpan(2, 60);
        } catch (IllegalInputTimeSpanException e) {
            System.out.println("\nIllegalInputTimeSpanException:");
            System.out.println(e.getMessage());
        }

        try {
            span.add(-30);
        } catch (IllegalInputTimeSpanException e) {
            System.out.println("\nIllegalInputTimeSpanException:");
            System.out.println(e.getMessage());
        }

        // Перевірка від'ємного часового інтервалу

        try {
            span.subtract(10, 0);
        } catch (NegativeTimeSpanException e) {
            System.out.println("\nNegativeTimeSpanException:");
            System.out.println(e.getMessage());
        }

        // Перевірка некоректного множника

        try {
            span.scale(0);
        } catch (IllegalScaleFactorException e) {
            System.out.println("\nIllegalScaleFactorException:");
            System.out.println(e.getMessage());
        }

        try {
            span.scale(-2);
        } catch (IllegalScaleFactorException e) {
            System.out.println("\nIllegalScaleFactorException:");
            System.out.println(e.getMessage());
        }

        // Перевірка стану об'єкта після виключень

        System.out.println("\nFinal TimeSpan:");
        System.out.println(span.getHours());
        System.out.println(span.getMinutes());

        System.out.println("\nProgram completed successfully.");
    }
}