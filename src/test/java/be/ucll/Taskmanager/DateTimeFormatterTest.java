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
            assertEquals(time.getYear(),2011);
            assertEquals(time.getMonthValue(),12);
            assertEquals(time.getDayOfMonth(),3);
            assertEquals(time.getHour(),10);
            assertEquals(time.getMinute(),15);
            assertEquals(time.getSecond(),30);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void dateTimeFormatterTestPrint(){
        LocalDateTime time = LocalDateTime.of(2011,12,3,10,15,30);
        String datum = 	"2018-11-04 05:07:33";
        assertEquals(formatter.print(time,Locale.ENGLISH),datum);
    }
}
