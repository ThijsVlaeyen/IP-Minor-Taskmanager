package be.ucll.Taskmanager;

import be.ucll.Taskmanager.Domain.DateFormatter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DateTimeFormatterTest {
    @Autowired
    private DateFormatter formatter;

    @Test
    public void dateTimeFormatterTest(){
        String datum = 	"2018-11-04T05:07:33";
        try {
            LocalDateTime time = formatter.parse(datum, Locale.ENGLISH);
            assertEquals(time.getYear(),2018);
            assertEquals(time.getMonthValue(),11);
            assertEquals(time.getDayOfMonth(),4);
            assertEquals(time.getHour(),5);
            assertEquals(time.getMinute(),7);
            assertEquals(time.getSecond(),33);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void dateTimeFormatterTestPrint(){
        LocalDateTime time = LocalDateTime.of(2018,11,4,5,7,33);
        String datum = 	"2018-11-04 05:07:33";
        assertEquals(formatter.print(time,Locale.ENGLISH),datum);
    }
}
