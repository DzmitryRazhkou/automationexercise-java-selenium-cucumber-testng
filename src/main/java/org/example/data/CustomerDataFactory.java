package org.example.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class CustomerDataFactory {

    private static final Faker faker = new Faker(Locale.US);
    private static final Random random = new Random();

    private static final List<String> DOMAINS = List.of("gmail.com", "yahoo.com", "outlook.com", "icloud.com", "aol.com", "protonmail.com");

    private static final List<String> PHONE_NUMBERS = List.of("(954) 5695114", "(985) 4804759", "(773) 7848033", "(980) 7867291", "(512) 4442041", "(407) 7810762", "(469) 7208507", "(424) 7066776", "(718) 7236151", "(770) 4547294", "(773) 3296180", "(972) 7365660");

    private static final List<String> STATES = List.of("AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY");

    private static final List<String> GENDERS = List.of("Male", "Female", "Transgender");

    private CustomerDataFactory() {
    }

    public static CustomerInfo generateCustomer() {
        CustomerInfo c = new CustomerInfo();

        String subject = faker.beer().name();
        String message = faker.lorem().sentence(5);
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String fullName = firstName + " " + lastName;

        c.setFirstName(firstName);
        c.setLastName(lastName);
        c.setFullName(fullName);
        c.setSubject(subject);
        c.setSubject(message);

        c.setEmail(generateRandomEmail(fullName));
        c.setPhoneNumber(generatePhoneNumber());
        c.setCity(faker.address().city());
        c.setAddress1(faker.address().streetAddress());
        c.setAddress2(faker.address().secondaryAddress());

        c.setTotalPrice(faker.number().numberBetween(1, 100));
        c.setBookName(faker.book().title());

        c.setUpdateFirstName(faker.name().firstName());
        c.setUpdateLastName(faker.name().lastName());
        c.setUpdateTotalPrice(faker.number().numberBetween(1, 50));
        c.setUpdateBookName(faker.book().title());

        c.setDepositPaid(randomDeposit());
        c.setUpdateDepositPaid(randomDeposit());

        c.setState(randomState());
        c.setZipCode(randomZip());
        c.setCountry("United States");
        c.setDob(randomDOB());
        c.setGender(randomGender());

        return c;
    }

    // -------- helpers --------

    private static String generateRandomEmail(String fullName) {
        String base = fullName.toLowerCase().replaceAll("[^a-z0-9]", "");
        String domain = DOMAINS.get(random.nextInt(DOMAINS.size()));
        return base + "@" + domain;
    }

    private static String generatePhoneNumber() {
        return PHONE_NUMBERS.get(random.nextInt(PHONE_NUMBERS.size()));
    }

    private static String randomState() {
        return STATES.get(random.nextInt(STATES.size()));
    }

    private static String randomZip() {
        return String.valueOf(10000 + random.nextInt(90000));
    }

    private static String randomDOB() {
        LocalDate start = LocalDate.of(1954, 1, 1);
        LocalDate end = LocalDate.of(2024, 12, 31);

        long startEpoch = start.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
        long endEpoch = end.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();

        long randomEpoch = startEpoch + (long) (random.nextDouble() * (endEpoch - startEpoch));
        LocalDate date = LocalDate.ofEpochDay(randomEpoch / 86400);

        return String.format("%02d-%02d-%d", date.getMonthValue(), date.getDayOfMonth(), date.getYear());
    }

    private static String randomGender() {
        return GENDERS.get(random.nextInt(GENDERS.size()));
    }

    private static boolean randomDeposit() {
        return random.nextBoolean();
    }
}