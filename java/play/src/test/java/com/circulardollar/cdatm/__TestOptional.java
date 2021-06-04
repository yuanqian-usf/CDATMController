package com.circulardollar.cdatm;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertFalse;

public class __TestOptional {
    public interface Person {
        String getName();
        Optional<Integer> getAge();

    }
    public interface Car {
        Person getDriver();
    }
    public interface LicenseService {
        DriversLicence getDriversLicence(Person person);
    }

    public interface DriversLicence {
        String getName();
    }

    @Test public void optionalTest_Empty_isPresent() {
        assertFalse(Optional.empty().isPresent());
    }

//    @Test public void optionalTest() {
//        //flatMap
//        //public<U> Optional<U> flatMap(Function<? super T, Optional<U>> mapper) {
//        //map
//        //public<U> Optional<U>     map(Function<? super T, ? extends U> mapper) {
//        Optional<Car> car = getNextCarIfPresent();
//        Optional<Integer> driversAge =
//            car.map(Car::getDriver).flatMap(Person::getAge);
//        LicenseService licenceService = new LicenseService() {
//            @Override public DriversLicence getDriversLicence(Person person) {
//                return null;
//            }
//        };
//        Optional<DriversLicence> driversLicence =
//            car
//                .map(Car::getDriver)
//                .map(licenceService::getDriversLicence);
//
//        Car car1 = null;
//        DriversLicence driversLicence1 = getDriverLicense(car1, licenceService);
//        boolean isOfLegalAge =
//            car.map(Car::getDriver).flatMap(Person::getAge).orElse(0) > 18;
//
//        Optional<Person> illegalDriver;
//        Optional<Person> driver = car.map(Car::getDriver);
//        boolean isOfLegalAge1 = driver.flatMap(Person::getAge).get() > 18;
//        if (!isOfLegalAge1) {
//            illegalDriver = driver;
//        } else {
//            illegalDriver = Optional.empty();
//        }
//
//
//        illegalDriver = car.map(Car::getDriver).filter(p -> p.getAge().orElse(0) < 18);
//    }

    public DriversLicence getDriverLicense(Car car, LicenseService licenseService) {
        Person driver;
        if (car != null) {
            driver = car.getDriver();
            if (driver != null) {
                return licenseService.getDriversLicence(driver);
            }
        }
        return null;
    }

    private Optional<Car> getNextCarIfPresent() {
        return Optional.empty();
    }
}
