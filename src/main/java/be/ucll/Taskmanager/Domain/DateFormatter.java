package be.ucll.Taskmanager.Domain;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class DateFormatter implements Formatter<LocalDateTime> {
    @Override
    public LocalDateTime parse(String text, Locale locale) throws ParseException {
        return LocalDateTime.parse(text, java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    @Override
    public String print(LocalDateTime date, Locale locale) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return date.format(formatter);
    }
}
