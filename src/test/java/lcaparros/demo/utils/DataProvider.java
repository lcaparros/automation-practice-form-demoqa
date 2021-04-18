package lcaparros.demo.utils;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class DataProvider {

    private static final Logger logger = LoggerFactory.getLogger(DataProvider.class);

    public static final List<String> subjects = Arrays.asList(
            "Maths", "Accounting", "Arts", "Social Studies", "Biology", "Physics", "Chemistry",
            "Computer Science", "Commerce", "Economics", "Civics", "Hindi", "English", "History"
    );

    private static final List<String> addresses = Arrays.asList(
            "1600 Pennsylvania Avenue, Washington DC",
            "11 Wall Street New York, NY",
            "350 Fifth Avenue New York, NY 10118",
            "221 B Baker St, London, England",
            "Tour Eiffel Champ de Mars, Paris",
            "4059 Mt Lee Dr. Hollywood, CA 90068",
            "Buckingham Palace, London, England",
            "Statue of Liberty, Liberty Island New York, NY 10004",
            "Manager Square, Bethlehem, West Bank",
            "2 Macquarie Street, Sydney"
    );

    private static final Map<String, List<String>> cities = ImmutableMap.of(
            "NCR", Arrays.asList(
                    "Delhi",
                    "Gurgaon",
                    "Noida"
            ),
            "Uttar Pradesh", Arrays.asList(
                    "Agra",
                    "Lucknow",
                    "Merrut"
            ),
            "Haryana", Arrays.asList(
                    "Karnal",
                    "Panipat"
            ),
            "Rajasthan", Arrays.asList(
                    "Jaipur",
                    "Jaiselmer"
            )
    );

    public static String generateRandomPhoneNumber() {
        String digits1 = String.valueOf(ThreadLocalRandom.current().nextInt(100, 999));
        String digits2 = String.valueOf(ThreadLocalRandom.current().nextInt(100, 999));
        String digits3 = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 9999));
        return digits1 + digits2 +  digits3;
    }

    public static String getRandomDateString() {
        LocalDate start = LocalDate.of(1940, Month.JANUARY, 1);
        LocalDate end = LocalDate.now();
        return getRandomDateStringBetween(start, end);
    }

    public static String getRandomDateStringBetween(LocalDate earlierDate, LocalDate laterDater) {
        long startEpochDay = earlierDate.toEpochDay();
        long endEpochDay = laterDater.toEpochDay();
        long randomDay = ThreadLocalRandom
                .current()
                .nextLong(startEpochDay, endEpochDay);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");

        return LocalDate.ofEpochDay(randomDay).format(formatter);
    }

    public static List<String> getRandomSubjects() {
        List<String> selectedSubjects = new ArrayList<>(subjects);
        Collections.shuffle(selectedSubjects);
        int startIndex = ThreadLocalRandom.current().nextInt(0,selectedSubjects.size());
        return startIndex < selectedSubjects.size()
                ? selectedSubjects.subList(startIndex, ThreadLocalRandom.current().nextInt(startIndex,selectedSubjects.size()))
                : Collections.emptyList();
    }

    public static String getRandomPicture() {
        // Included different image types and with different extensions to simulate more test cases
        List<String> pictureList = Arrays.asList("p1.png", "p2.jpg", "p3.jpeg", "p4.ico");
        Collections.shuffle(pictureList);
        return CommonUtils.getFilePath("pictures/" + pictureList.get(0));
    }

    public static String getRandomAddress() {
        List<String> deck = new ArrayList<>(addresses);
        return getRandomFromList(deck);
    }

    public static String getRandomState() {
        List<String> deck = new ArrayList<>(cities.keySet());
        return getRandomFromList(deck);
    }

    public static String getRandomCityByState(String state) {
        List<String> deck = cities.get(state);
        return getRandomFromList(deck);
    }

    private static String getRandomFromList(List<String> deck) {
        Collections.shuffle(deck);
        return deck.get(0);
    }
}
