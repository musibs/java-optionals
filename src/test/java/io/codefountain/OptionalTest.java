package io.codefountain;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertTrue;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OptionalTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void whenEmptyOptionalThenExpectNoSuchElementExceptionOnGet(){
        expectedException.expect(NoSuchElementException.class);
        Optional<String> optional = Optional.empty();
        optional.get();
    }

    @Test
    public void whenNullOptionalExpectNullPointerException(){
        Person person = null;
        expectedException.expect(NullPointerException.class);
        Optional<Person> user = Optional.of(person);
    }

    @Test
    public void whenOfNullableExpectEither(){
        Person person = null;
        Optional<Person> optional = Optional.ofNullable(person);
        assertThat(optional, is(Optional.empty()));
    }

    @Test
    public void whenNonNullOptionalThenOk(){
        Optional<String> user = Optional.of("John");
        assertThat(user.get(), is("John"));
    }

    @Test
    public void whenOptionIfPresentThenOk(){
        Person person = new Person("John", "1456345609");
        Optional<Person> optional = Optional.ofNullable(person);
        assertThat(optional.isPresent(), is(true));

        optional.ifPresent(p -> assertThat(p.getName(), is("John")));
    }

    @Test
    public void whenOptionIfPresentThenNotOk(){
        Optional<Person> optional = Optional.ofNullable(null);
        optional.ifPresent(p -> assertThat(p.getName(), is("John")));
    }

    @Test
    public void whenNonEmptyObjectThenGet(){
        Person person = new Person("Somnath", "76543210");
        Optional<Person> optionalPerson = Optional.ofNullable(person);
        assertThat(optionalPerson.isPresent(), is(true));
        assertThat(optionalPerson.get(), is(person));
    }

    @Test
    public void whenEmptyObjectThenReturnDefault(){
        Person person1 = null;
        Person person2 = new Person("Somnath", "76543210");
        Person optionalPerson = Optional.ofNullable(person1).orElse(person2);
        assertThat(optionalPerson, is(person2));
    }

    @Test
    public void whenNonEmptyObjectThenOk(){
        Person person1 = new Person("John", "76543290");;
        Person person2 = new Person("Somnath", "76543210");
        Person optionalPerson = Optional.ofNullable(person1).orElse(person2);
        assertThat(optionalPerson, is(person1));
    }

    @Test
    public void whenEmptyObjectThenOrElseGet(){
        Person person1 = null;
        Person person2 = new Person("Somnath", "76543210");
        Person optionalPerson = Optional.ofNullable(person1).orElseGet(() -> person2);
        assertThat(optionalPerson, is(person2));
    }

    @Test
    public void whenNonEmptyObjectThenOrElseGet(){
        Person person1 = new Person("John", "76543290");;
        Person optionalPerson = Optional.ofNullable(person1).orElseGet(() -> new Person("Somnath", "76543210"));
        assertThat(optionalPerson, is(person1));
    }

    @Test
    public void whenEmptyObjectThenOrElseThrow(){
        expectedException.expect(RuntimeException.class);
        Person person1 = null;
        Person optionalPerson = Optional.ofNullable(person1).orElseThrow(RuntimeException::new);
    }

    @Test
    public void whenOptionalAndMapThenOk(){
        Person person1 = new Person("John", "876543009");
        String result = Optional.ofNullable(person1).map( p -> p.getName().toUpperCase()).orElse("Unknown");
        assertThat(result, is("JOHN"));
    }

    @Test
    public void whenEmptyOptionalAndMapThenOrElse(){
        Person person1 = null;
        String result = Optional.ofNullable(person1).map( p -> p.getName()).orElse("Unknown");
        assertThat(result, is("Unknown"));
    }

    @Test
    public void whenFlatMapThenOk(){
        Person person = new Person("Terry", "8760654311");
        person.setProfession("Journalist");
        String profession = Optional.ofNullable(person).flatMap(p -> p.getProfession()).orElse("Unknown");
        assertThat(profession, is("Journalist"));
    }

    @Test
    public void whenFilterThenOk(){
        Person person = new Person("Tim", "87654309112");
        person.setProfession("Real Estate Broker");
        Optional<Person> filteredPerson = Optional.ofNullable(person).filter(p -> p.getProfession().isPresent());
        assertThat(filteredPerson.isPresent(), is(true));
    }

    @Test
    public void whenChainingThenOk(){
        Country newZealand = new Country("NZ");
        Address address = new Address(newZealand);
        Person person = new Person("Ross", "98700987");
        person.setAddress(address);

        String countryCode =  Optional.of(person)
                .flatMap(Person::getAddress)
                .flatMap(Address::getCountry)
                .flatMap(Country::getCountryCode)
                .orElse("UnKnown");

        assertThat(countryCode, is("NZ"));
    }

    @Test
    public void whenUseOrThenOk(){
        Person person = null;
        Person optionalPerson = Optional.ofNullable(person)
                .or(() -> Optional.of(new Person("default", "9898"))).get();
        assertThat(optionalPerson.getContactNo(), is("9898"));
    }

    @Test
    public void whenUseIfPresentOrElseThenOk(){
        Person person = null;
        Optional.ofNullable(person)
                .ifPresentOrElse(p -> System.out.println(p.getName()), () -> System.out.println("Runnable"));

    }

    @Test
    public void whenOptionalStreamThenCollect(){
        Person person = new Person("Ross", "8765430091");
        List<String> name = Optional.ofNullable(person)
                .stream()
                .map(p -> p.getName())
                .collect(Collectors.toList());

        assertThat(name, hasItem("Ross"));
    }


}
