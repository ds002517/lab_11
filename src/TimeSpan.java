import exceptions.NegativeTimeSpanException;
import exceptions.IllegalInputTimeSpanException;
import exceptions.IllegalScaleFactorException;

public class TimeSpan {
    private int hours;
    private int minutes;

    public TimeSpan() {
        this.hours = 0;
        this.minutes = 0;
    }

    public TimeSpan(int minutes) {
        if (minutes >= 0) {
            this.hours = minutes / 60;
            this.minutes = minutes % 60;
        } else {
            throw new IllegalInputTimeSpanException(
                    "Minutes cannot be negative");
        }
    }

    public TimeSpan(int hours, int minutes) {
        if (hours >= 0 && minutes >= 0 && minutes <= 59) {
            this.hours = hours;
            this.minutes = minutes;
        } else {
            throw new IllegalInputTimeSpanException(
                    "Invalid hours or minutes");
        }
    }

    public TimeSpan(TimeSpan span) {
        if (span != null) {
            this.hours = span.getHours();
            this.minutes = span.getMinutes();
        } else {
            throw new IllegalInputTimeSpanException(
                    "TimeSpan cannot be null");
        }
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void add(int hours, int minutes) {
        if (hours >= 0 && minutes >= 0 && minutes <= 59) {
            this.hours += hours;
            this.minutes += minutes;

            if (this.minutes >= 60) {
                this.hours += this.minutes / 60;
                this.minutes = this.minutes % 60;
            }
        } else {
            throw new IllegalInputTimeSpanException(
                    "Invalid hours or minutes");
        }
    }

    public void add(int minutes) {
        if (minutes >= 0) {
            add(minutes / 60, minutes % 60);
        } else {
            throw new IllegalInputTimeSpanException(
                    "Minutes cannot be negative");
        }
    }

    public void add(TimeSpan span) {
        if (span != null) {
            add(span.getHours(), span.getMinutes());
        } else {
            throw new IllegalInputTimeSpanException(
                    "TimeSpan cannot be null");
        }
    }

    public void subtract(int hours, int minutes) {
        if (hours >= 0 && minutes >= 0 && minutes <= 59) {
            if (this.hours > hours ||
                    (this.hours == hours && this.minutes >= minutes)) {

                this.hours -= hours;
                this.minutes -= minutes;

                if (this.minutes < 0) {
                    this.hours--;
                    this.minutes += 60;
                }
            } else {
                throw new NegativeTimeSpanException(
                        "TimeSpan cannot become negative");
            }
        } else {
            throw new IllegalInputTimeSpanException(
                    "Invalid hours or minutes");
        }
    }

    public void subtract(int minutes) {
        if (minutes >= 0) {
            subtract(minutes / 60, minutes % 60);
        } else {
            throw new IllegalInputTimeSpanException(
                    "Minutes cannot be negative");
        }
    }

    public void subtract(TimeSpan span) {
        if (span != null) {
            subtract(span.getHours(), span.getMinutes());
        } else {
            throw new IllegalInputTimeSpanException(
                    "TimeSpan cannot be null");
        }
    }

    public double getTotalHours() {
        return hours + minutes / 60.0;
    }

    public int getTotalMinutes() {
        return hours * 60 + minutes;
    }

    public void scale(int factor) {
        if (factor > 0) {
            int totalMinutes = getTotalMinutes() * factor;
            this.hours = totalMinutes / 60;
            this.minutes = totalMinutes % 60;
        } else {
            throw new IllegalScaleFactorException(
                    "Scale factor must be positive");
        }
    }
}