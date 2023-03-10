package basic;

import org.junit.Test;

import java.text.ParseException;
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
    public void test1() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(new Date()));
        System.out.println(sdf.parse("2022-09-10"));

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println(LocalDateTime.now().format(dtf));
        System.out.println(dtf.parse("2022-09-10"));
    }

    @Test
    public void test2() {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println(date);
        System.out.println(time);
        System.out.println(dateTime);

        LocalDate date1 = LocalDate.of(2022, 9, 10);
        LocalTime time1 = LocalTime.of(12, 10, 22);
        LocalDateTime dateTime1 = LocalDateTime.of(2022, 9, 10, 12, 10, 22);
        System.out.println(date1);
        System.out.println(time1);
        System.out.println(dateTime1);

        LocalDate date2 = LocalDate.parse("2022-09-10");
        LocalTime time2 = LocalTime.parse("12:10:22");
        LocalDateTime dateTime2 = LocalDateTime.parse("2022-09-10T12:10:22");
        System.out.println(date2);
        System.out.println(time2);
        System.out.println(dateTime2);
    }
}