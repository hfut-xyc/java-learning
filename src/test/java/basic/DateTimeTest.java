package basic;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @date 2022-7-22
 **/
public class DateTimeTest {

    @Test
    public void test1() {
        Date date = new Date();

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf1.format(date));

        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
        System.out.println(sdf2.format(date));

        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf3.format(date));
    }

    @Test
    public void test2() {
        LocalDate date = LocalDate.now();
        System.out.println(date);

        LocalTime time = LocalTime.now();
        System.out.println(time);

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(dateTime.format(dtf));
    }

    @Test
    public void test3() {
        LocalDate date1 = LocalDate.of(2022, 1, 14);
        LocalDate date2 = LocalDate.parse("2022-01-14");

        LocalTime time1 = LocalTime.of(12, 10, 22);
        LocalTime time2 = LocalTime.parse("12:10:22");

        LocalDateTime dateTime1 = LocalDateTime.of(2022, 1, 14, 12, 10, 22);
        LocalDateTime dateTime2 = LocalDateTime.parse("2022-01-14 12:10:22");
    }

}
