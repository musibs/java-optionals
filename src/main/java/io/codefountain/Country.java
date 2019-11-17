package io.codefountain;

import java.util.Optional;

public class Country {

    private String countryCode;

    public Country(String countryCode) {
        this.countryCode = countryCode;
    }

    public Optional<String> getCountryCode() {
        return Optional.ofNullable(countryCode);
    }
}
