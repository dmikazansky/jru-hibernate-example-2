package com.javarush;

import com.javarush.dao.*;
import com.javarush.domain.*;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.io.File;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.*;

public class Main {
    private final SessionFactory sessionFactory;
    private final ActorDAO actorDAO;
    private final AddressDAO addressDAO;
    private final CategoryDAO categoryDAO;
    private final CityDAO cityDAO;
    private final CountryDAO countryDAO;
    private final CustomerDAO customerDAO;
    private final FilmDAO filmDAO;
    private final FilmTextDAO filmTextDAO;
    private final InventoryDAO inventoryDAO;
    private final LanguageDAO languageDAO;
    private final PaymentDAO paymentDAO;
    private final RentalDAO rentalDAO;
    private final StaffDAO staffDAO;
    private final StoreDAO storeDAO;

    public Main() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        actorDAO = new ActorDAO(sessionFactory);
        addressDAO = new AddressDAO(sessionFactory);
        categoryDAO = new CategoryDAO(sessionFactory);
        cityDAO = new CityDAO(sessionFactory);
        countryDAO = new CountryDAO(sessionFactory);
        customerDAO = new CustomerDAO(sessionFactory);
        filmDAO = new FilmDAO(sessionFactory);
        inventoryDAO = new InventoryDAO(sessionFactory);
        filmTextDAO = new FilmTextDAO(sessionFactory);
        languageDAO = new LanguageDAO(sessionFactory);
        paymentDAO = new PaymentDAO(sessionFactory);
        rentalDAO = new RentalDAO(sessionFactory);
        staffDAO = new StaffDAO(sessionFactory);
        storeDAO = new StoreDAO(sessionFactory);
    }

    @SneakyThrows
    public static void main(String[] args) {
        Main main = new Main();
//        System.setOut(new PrintStream(new File("C:\\Users\\Олег\\IdeaProjects\\jru-hibernate-example-2\\src\\main\\resources\\log.txt")));

//        Rental rental = main.customerGetFilm();
//        main.customerRent();
        main.newFilmForRent();
    }


    @Transactional
    private void newFilmForRent() {
        try (Session currentSession = sessionFactory.getCurrentSession()) {
            currentSession.beginTransaction();
            Language language = languageDAO.getItems(0, 10).stream().unordered().findAny().get();
            List<Category> categories = categoryDAO.getItems(0, 3);
            List<Actor> actors = actorDAO.getItems(0, 20);

            Film film = new Film();
            film.setActors(new HashSet<>(actors));
            film.setLanguage(language);
            film.setRating(Rating.R);
            film.setSpecialFeatures(Set.of(Feature.COMMENTARIES, Feature.TRAILERS));
            film.setLength((short) 22);
            film.setReplacementCost(BigDecimal.ZERO);
            film.setRentalRate(BigDecimal.ONE);
            film.setDescription("This is a film");
            film.setTitle("Film Title");
            film.setRentalDuration((byte) 46);
            film.setOriginalLanguage(language);
            film.setCategories(new HashSet<>(categories));
            film.setYear(Year.now());
            filmDAO.save(film);
        }
    }

    private void customerRent() {
        try (Session currentSession = sessionFactory.getCurrentSession()) {
            currentSession.beginTransaction();

            Customer customer = customerDAO.getById(1);

            Film filmForRent = filmDAO.getFilmForRent();
            Inventory inventory = new Inventory();
            inventory.setFilm(filmForRent);
            Store store = storeDAO.getById(1);
            inventory.setStore(store);
            inventoryDAO.save(inventory);

            Rental rental = new Rental();
            rental.setCustomer(customer);
            rental.setInventory(inventory);
            rental.setRentalDate(LocalDateTime.now());
            rental.setStaff(store.getStaff());
            rentalDAO.save(rental);

            Payment payment = new Payment();
            payment.setCustomer(customer);
            payment.setRental(rental);
            payment.setStaff(store.getStaff());
            payment.setAmount(BigDecimal.TEN);
            payment.setPaymentDate(LocalDateTime.now());
            paymentDAO.save(payment);

            currentSession.getTransaction().commit();

        }
    }

    private Rental customerGetFilm() {
        try (Session currentSession = sessionFactory.getCurrentSession()) {
            currentSession.beginTransaction();
            Rental notReturnedRental = rentalDAO.getNotReturnedRental();
            notReturnedRental.setReturnDate(LocalDateTime.now());
            rentalDAO.save(notReturnedRental);
            currentSession.getTransaction().commit();
            return notReturnedRental;
        }
    }


    private Customer createCustomer(String firstName, String lastName, String email) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Customer customer = new Customer();
            customer.setStore(storeDAO.getById(1));
            customer.setAddress(addressDAO.getById(1));
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setEmail(email);
            customer.setIsActive(true);
            customerDAO.save(customer);
            session.getTransaction().commit();
            return new Customer();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}


