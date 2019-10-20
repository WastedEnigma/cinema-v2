import cinema.Cinema;
import cinema.people.*;
import cinema.sessions.*;
import cinema.structure.CinemaHall;
import database.VisitorDataBase;

import java.util.*;

import static generators.MovieSessionGenerator.*;

public class Main {

    private static final double RAND_BALANCE = (Math.random() * (400 - 200)) + 200;
    private static final VisitorDataBase visitorDataBase = VisitorDataBase.getInstance();
    private static final Administrator admin = Administrator.getInstance();

    public static void main(String[] args) {
        initConsoleUI();
    }

    private static void initConsoleUI() {
        Scanner sc = new Scanner(System.in);
        String s;

        do {
            System.out.print("Are you visitor or admin? (v/a) ");
            s = sc.next();

            switch (s) {
                case "v":
                    initVisitorUI();
                    break;
                case "a":
                    initAdminUI();
                    break;
                default:
                    if (!s.equalsIgnoreCase("exit"))
                        System.out.println("Wrong input");
            }
        } while (!s.equalsIgnoreCase("exit"));
    }

    private static void initVisitorUI() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your first name: ");
        String fn = sc.nextLine();
        System.out.print("Enter your last name: ");
        String ln = sc.nextLine();
        Visitor visitor = new Visitor(fn, ln, RAND_BALANCE);

        // accessing visitor through database...
        if (visitorDataBase.getVisitors() != null) {
            for (Visitor v : visitorDataBase.getVisitors()) {
                if (v.equals(visitor))
                    visitor = v;
            }
        }

        String o;

        do {
            System.out.println("Choose options" +
                    "\n(1) lookup all cinemas" +
                    "\n(2) lookup all movies" +
                    "\n(3) lookup all movie sessions" +
                    "\n(4) lookup movie sessions by movie" +
                    "\n(5) lookup cinemas by movie" +
                    "\n(6) buy ticket to movie session" +
                    "\n(7) view all purchased tickets" +
                    "\n(8) check balance");

            System.out.print("Option: ");
            o = sc.nextLine();

            switch (o) {
                case "1":
                    visitor.lookupAllCinemas();
                    break;
                case "2":
                    visitor.lookupAllMovies();
                    break;
                case "3":
                    visitor.lookupAllMovieSessions();
                    break;
                case "4":
                    visitor.lookupMovieSessionsByMovie();
                    break;
                case "5":
                    visitor.lookupCinemasByMovie();
                    break;
                case "6":
                    visitor.buyTicket();
                    break;
                case "7":
                    visitor.viewAllTickets();
                    break;
                case "8":
                    visitor.checkBalance();
                    break;
                default:
                    if (!o.equalsIgnoreCase("exit"))
                        System.out.println("Wrong input");
            }
        } while (!o.equalsIgnoreCase("exit"));

        visitorDataBase.save(visitor);
    }

    private static void initAdminUI() {
        Scanner sc = new Scanner(System.in);
        String o;

        do {
            System.out.println("Choose options" +
                    "\n(1) add/delete cinema" +
                    "\n(2) add/delete movie session" +
                    "\n(3) view all visitors with tickets");

            System.out.print("Option: ");
            o = sc.nextLine();

            switch (o) {
                case "1 add":
                    admin.addCinema();
                    break;
                case "1 del":
                    admin.deleteCinema();
                    break;
                case "2 add":
                    admin.addMovieSession();
                    break;
                case "2 del":
                    admin.deleteMovieSession();
                    break;
                case "3":
                    admin.viewAllVisitorsWithTickets();
                    break;
                default:
                    if (!o.equalsIgnoreCase("exit"))
                        System.out.println("Wrong input");
            }
        } while (!o.equalsIgnoreCase("exit"));
    }

    // This method was used for a hardcoded data generation
    // at the first program execution
    private void initStandartCinemaData() {
        CinemaHall cinemaHall1_1 = new CinemaHall(1);
        CinemaHall cinemaHall1_2 = new CinemaHall(2);
        CinemaHall cinemaHall1_3 = new CinemaHall(3);

        CinemaHall cinemaHall2_1 = new CinemaHall(1);
        CinemaHall cinemaHall2_2 = new CinemaHall(2);

        CinemaHall cinemaHall3_1 = new CinemaHall(1);
        CinemaHall cinemaHall3_2 = new CinemaHall(2);

        Map<MovieSession, Movie> sessionMovieMap1_1 = generateMovieSessionMap(5);
        Map<MovieSession, Movie> sessionMovieMap1_2 = generateMovieSessionMap(3);
        Map<MovieSession, Movie> sessionMovieMap1_3 = generateMovieSessionMap(3);

        Map<MovieSession, Movie> sessionMovieMap2_1 = generateMovieSessionMap(4);
        Map<MovieSession, Movie> sessionMovieMap2_2 = generateMovieSessionMap(3);

        Map<MovieSession, Movie> sessionMovieMap3_1 = generateMovieSessionMap(4);
        Map<MovieSession, Movie> sessionMovieMap3_2 = generateMovieSessionMap(6);

        cinemaHall1_1.setSessionMovieMap(sessionMovieMap1_1);
        cinemaHall1_2.setSessionMovieMap(sessionMovieMap1_2);
        cinemaHall1_3.setSessionMovieMap(sessionMovieMap1_3);

        cinemaHall2_1.setSessionMovieMap(sessionMovieMap2_1);
        cinemaHall2_2.setSessionMovieMap(sessionMovieMap2_2);

        cinemaHall3_1.setSessionMovieMap(sessionMovieMap3_1);
        cinemaHall3_2.setSessionMovieMap(sessionMovieMap3_2);

        List<CinemaHall> cinemaHalls1 = new ArrayList<>();
        cinemaHalls1.add(cinemaHall1_1);
        cinemaHalls1.add(cinemaHall1_2);
        cinemaHalls1.add(cinemaHall1_3);

        List<CinemaHall> cinemaHalls2 = new ArrayList<>();
        cinemaHalls2.add(cinemaHall2_1);
        cinemaHalls2.add(cinemaHall2_2);

        List<CinemaHall> cinemaHalls3 = new ArrayList<>();
        cinemaHalls3.add(cinemaHall3_1);
        cinemaHalls3.add(cinemaHall3_2);

        Cinema cinema1 = new Cinema("Barton's Cinema", cinemaHalls1);
        Cinema cinema2 = new Cinema("Newton's Cinema", cinemaHalls2);
        Cinema cinema3 = new Cinema("Roman's Cinema", cinemaHalls3);

       /* cinemas.add(cinema1);
        cinemas.add(cinema2);
        cinemas.add(cinema3);*/
    }
}
