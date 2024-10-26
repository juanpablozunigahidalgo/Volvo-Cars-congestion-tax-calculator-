package congestion.calculator.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TaxCalculatorService {

    private static final String JSONBIN_URL = "https://api.jsonbin.io/v3/b/671ba67dad19ca34f8be6252";
    private static final String API_KEY = "$2b$10$xFC7BlC/9mfhK2jwRMo.IemTR8HRFha0TZyWFgA8n./iRCF2kjqpG";

    public Map<String, Integer> calculateTotalTax() {
        Map<String, Integer> vehicleTaxMap = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();

        // Set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Master-Key", API_KEY);

        // Create HttpEntity with headers
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        // Fetch JSON data from JSONBIN with headers
        JsonNode response = restTemplate.exchange(JSONBIN_URL, HttpMethod.GET, entity, JsonNode.class).getBody();

        // Remove logging of the JSON response
        if (response == null || !response.has("record")) {
            throw new RuntimeException("Failed to fetch valid data from JSONBIN");
        }

        for (JsonNode node : response.get("record")) {
            String type = node.get("type").asText();
            if (!type.equals("RegularVehicle")) continue;

            String registrationNumber = node.get("registrationNumber").asText();
            Date date = parseDate(node.get("date").asText());

            int tax = calculateTaxForTime(date);
            vehicleTaxMap.put(registrationNumber, vehicleTaxMap.getOrDefault(registrationNumber, 0) + tax);
        }

        return vehicleTaxMap;
    }

    private Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException("Date parsing error: " + e.getMessage());
        }
    }

    private int calculateTaxForTime(Date date) {
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();
        int totalMinutes = hour * 60 + minute;

        if (isWithinTaxableHours(date)) {
            if (totalMinutes >= 360 && totalMinutes < 390) return 8;    // 06:00–06:29
            if (totalMinutes >= 390 && totalMinutes < 420) return 13;   // 06:30–06:59
            if (totalMinutes >= 420 && totalMinutes < 480) return 18;   // 07:00–07:59
            if (totalMinutes >= 480 && totalMinutes < 510) return 13;   // 08:00–08:29
            if (totalMinutes >= 510 && totalMinutes < 900) return 8;    // 08:30–14:59
            if (totalMinutes >= 900 && totalMinutes < 930) return 13;   // 15:00–15:29
            if (totalMinutes >= 930 && totalMinutes < 1020) return 18;  // 15:30–16:59
            if (totalMinutes >= 1020 && totalMinutes < 1080) return 13; // 17:00–17:59
            if (totalMinutes >= 1080 && totalMinutes < 1110) return 8;  // 18:00–18:29
        }

        return 0; // No tax for other times
    }

    private boolean isWithinTaxableHours(Date date) {
        return !isWeekend(date);
    }

    private boolean isWeekend(Date date) {
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return (localDateTime.getDayOfWeek() == DayOfWeek.SATURDAY ||
                localDateTime.getDayOfWeek() == DayOfWeek.SUNDAY);
    }
}
