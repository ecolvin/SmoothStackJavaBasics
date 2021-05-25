/**
 * 
 */
package com.ss.utopia.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import com.ss.utopia.entity.Airplane;
import com.ss.utopia.entity.Airport;
import com.ss.utopia.entity.Flight;
import com.ss.utopia.entity.FlightBooking;
import com.ss.utopia.entity.Route;
import com.ss.utopia.entity.User;
import com.ss.utopia.entity.UserRole;
import com.ss.utopia.service.AdminService;

/**
 * @author Eric Colvin
 *
 */
public class Main {
	
	private static AdminService database = new AdminService();
	private static Scanner input = new Scanner(System.in);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.print("Welcome to the Utopia Airlines Management System.");
		getUserCat();
	}

	public static void getUserCat() {
		System.out.println("Which category of user are you:");
		System.out.println("");
		System.out.println("\t1) Employee");
		System.out.println("\t2) Administrator");
		System.out.println("\t3) Traveler");
		System.out.println("\t4) Quit");
		System.out.println();
		Integer userCat = parseInt(input.nextLine());
		
		if(userCat == -1) {
			getUserCat();
			return;
		}
		switch(userCat) {
			case 1:
				emp1();
				return;
			case 2:
				admin1();
				return;
			case 3:
				traveler();
				return;
			case 4:
				input.close();
				return;
			default:
				System.out.println("That is not a valid choice. Please try again.");
				getUserCat();
				return;
		}
	}
	
	public static void emp1() {
		System.out.println("\t1) Enter Flights You Manage");
		System.out.println("\t2) Quit to Previous");
		System.out.println();
		Integer selection = parseInt(input.nextLine());
		if(selection == -1) {
			emp1();
			return;
		}
		switch(selection) {
			case 1:
				emp2();
				return;
			case 2:
				getUserCat();
				return;
			default:
				System.out.println("That is not a valid choice. Please try again.");
				emp1();
				return;
		}
	}
	
	public static void emp2() {
		List<Flight> flights = database.getFlights();
		int index = 1;
		for(Flight f:flights) {
			System.out.println("\t" + index + ") " + f.getRoute().getOriginAirport().getAirportCode() + ", " + f.getRoute().getOriginAirport().getCityName() + " -> " + f.getRoute().getDestAirport().getAirportCode() + ", " + f.getRoute().getDestAirport().getCityName());
			index++;
		}
		System.out.println("\t" + (flights.size()+1) + ") Quit to previous");
		System.out.println();
		Integer selection = parseInt(input.nextLine());
		if(selection == -1) {
			emp2();
			return;
		} else if(selection == flights.size() + 1) {
			emp1();
			return;
		} else if(selection >= 1 && selection <= flights.size()) {
			emp3(flights.get(selection-1));
			return;
		} else {
			System.out.println("That is not a valid choice. Please try again.");
			emp2();
			return;
		}
	}
	
	public static void emp3(Flight f) {
		System.out.println("\t1) View more details about the flight");
		System.out.println("\t2) Update the details of the flight");
		System.out.println("\t3) Update the number of available seats");
		System.out.println("\t4) Quit to previous");
		System.out.println();
		Integer selection = parseInt(input.nextLine());
		if(selection == -1) {
			emp3(f);
			return;
		}
		switch (selection) {
			case 1:
				empViewFlightDetails(f);
				return;
			case 2:
				empUpdateFlightDetails(f);
				return;
			case 3:
				empAddSeatsToFlight(f);
				return;
			case 4:
				emp2();
				return;
			default:
				System.out.println("That is not a valid choice. Please try again.");
				emp3(f);
				return;
		}
	}
	
	public static void empViewFlightDetails(Flight f) {
		System.out.println("You have chosen to view the Flight with Flight id: " + f.getId() + " from " + f.getRoute().getOriginAirport().getCityName() + " to " + f.getRoute().getDestAirport().getCityName() + ".");
		System.out.println();
		System.out.println("Departure Airport: " + f.getRoute().getOriginAirport().getAirportCode() + " | Arrival Airport: " + f.getRoute().getDestAirport().getAirportCode() + " |");
		System.out.println("Departure Date and Time: " + f.getDeparture() + " |");
		System.out.println("Seats Taken: " + f.getReservedSeats() + "/" + f.getAirplane().getType().getMaxCapacity() + " | Seat Price: " + f.getPrice());
		System.out.println();
		System.out.println("Press ENTER to return to the previous menu.");
		input.nextLine();
		emp3(f);
	}
	
	public static void empUpdateFlightDetails(Flight f) {
		Flight temp = f;
		System.out.println("You have chosen to view the Flight with Flight id: " + f.getId() + " from " + f.getRoute().getOriginAirport().getCityName() + " to " + f.getRoute().getDestAirport().getCityName() + ".");
		System.out.println("Enter \'quit\' at any prompt to cancel operation.");
		System.out.println();
		System.out.println("Please enter new Origin Airport Code or enter N/A for no change:");
		String origin = input.nextLine();
		if("QUIT".equals(origin.toUpperCase())) {
			emp3(f);
			return;
		}
		
		System.out.println("Please enter new Destination Airport Code or enter N/A for no change:");
		String dest = input.nextLine();
		if("QUIT".equals(dest.toUpperCase())) {
			emp3(f);
			return;
		}
		
		System.out.println("Please enter new Departure Time (YYYY-MM-DD HH:MI:SS) or enter N/A for no change:");
		String departure = input.nextLine();
		if("QUIT".equals(departure.toUpperCase())) {
			emp3(f);
			return;
		} else if(!"N/A".equals(departure.toUpperCase())) {
			try {
				new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse(departure);
			} catch (ParseException e) {
				System.out.println("Incorrect Formatting. Please try again.");
				empUpdateFlightDetails(f);
				return;
			}
			temp.setDeparture(departure);
		}
		
		System.out.println("Please enter new Seat Price or enter N/A for no change:");
		String pr = input.nextLine();
		if("QUIT".equals(pr.toUpperCase())) {
			emp3(f);
			return;
		} else if(!"N/A".equals(pr.toUpperCase())) {
			float price = parseFloat(pr);
			if(price == -1.0f) {
				empUpdateFlightDetails(f);
				return;
			}
			temp.setPrice(price);
		}
		
		int status = database.updateFlight(temp);
		if(status == -1) {
			System.out.println("Flight could not be updated. Origin Airport could not be found.");
		} else if (status == -2) {
			System.out.println("Flight could not be updated. Destination Airport could not be found.");
		} else if (status == -3) {
			System.out.println("Flight could not be updated. Both Origin and Destination Airports could not be found.");
		}
		emp3(f);
		return;
	}
	
	public static void empAddSeatsToFlight(Flight f) {
		Integer maxSeats = f.getAirplane().getType().getMaxCapacity();
		Integer curSeats = maxSeats - f.getReservedSeats();
		System.out.println("Current number of seats available: " + curSeats + "/" + maxSeats);
		System.out.println("Please enter the new number of seats available: ");
		Integer newSeats = parseInt(input.nextLine());
		if(newSeats == -1) {
			empAddSeatsToFlight(f);
			return;
		} else if(newSeats > maxSeats) {
			System.out.println("There cannot be more than " + maxSeats + " seats available. Please try again.");
			empAddSeatsToFlight(f);
			return;
		} else if(newSeats < 0) {
			newSeats = 0;
		}
		
		f.setReservedSeats(maxSeats - newSeats);
		int status = database.updateFlight(f);
		if(status != 0) {
			System.out.println("Something went wrong. Flight could not be updated.");
		}
		emp3(f);
		return;
	}
	
	public static void admin1() {
		System.out.println("\t1) Add data");
		System.out.println("\t2) Update data");
		System.out.println("\t3) Delete data");
		System.out.println("\t4) Quit to previous");
		System.out.println();
		Integer selection = parseInt(input.nextLine());
		if(selection == -1) {
			admin1();
			return;
		} else if (selection == 4) {
			getUserCat();
			return;
		} else if (selection > 0 && selection <= 3) {
			admin2(selection);
			return;
		} else {
			System.out.println("That is not a valid choice. Please try again.");
			admin1();
			return;
		}
	}
	
	public static void admin2(int operation) {
		System.out.println("\t1) Airport Table");
		System.out.println("\t2) Route Table");
		System.out.println("\t3) Flight Table");
		System.out.println("\t4) User Table");
		System.out.println("\t5) Quit to previous");
		Integer selection = parseInt(input.nextLine());
		switch (selection) {
			case -1:
				admin2(operation);
				return;
			case 1:
				if(operation == 1) {
					adminAirportAdd();
					return;
				} else if (operation == 2) {
					adminAirportUpdate();
					return;
				} else {
					adminAirportDelete();
					return;
				}
			case 2:
				if(operation == 1) {
					adminRouteAdd();
					return;
				} else if (operation == 2) {
					adminRouteUpdate();
					return;
				} else {
					adminRouteDelete();
					return;
				}
			case 3:
				if(operation == 1) {
					adminFlightAdd();
					return;
				} else if (operation == 2) {
					adminFlightUpdate();
					return;
				} else {
					adminFlightDelete();
					return;
				}
			case 4:
				if(operation == 1) {
					adminUserAdd();
					return;
				} else if (operation == 2) {
					adminUserUpdate();
					return;
				} else {
					adminUserDelete();
					return;
				}
			case 5:
				admin1();
				return;
			default:
				System.out.println("That is not a valid choice. Please try again.");
				admin2(operation);
				return;
		}
	}
	
	public static void adminAirportAdd() {
		Airport a = new Airport();
		System.out.println("Please enter new Airport Code or enter QUIT to quit:");
		String code = input.nextLine();
		if("QUIT".equals(code.toUpperCase())) {
			admin2(1);
			return;
		}
		if(code.length() != 3) {
			System.out.println("Invalid code formatting. Please try again.");
			adminAirportAdd();
			return;
		}
		System.out.println("Please enter new Airport City or enter QUIT to quit:");
		String city = input.nextLine();
		if("QUIT".equals(city.toUpperCase())) {
			admin2(1);
			return;
		}
		a.setAirportCode(code);
		a.setCityName(city);		
		database.addAirport(a);
		admin1();
		return;	
	}
	
	public static void adminRouteAdd() {
		Route r = new Route();
		
		List<Airport> airports = database.getAirports();
		int i = 1;
		for(Airport a : airports) {
			System.out.println("\t" + i + ") " + a.getAirportCode() + ", " + a.getCityName());
			i++;
		}
		System.out.println("\t" + i + ") Quit to previous");
		System.out.println();
		System.out.println("Please select your origin airport:");
		Integer origin = parseInt(input.nextLine());
		if(origin == -1) {
			adminRouteAdd();
			return;
		} else if(origin <= 0 || origin > i) {
			System.out.println("That is not a valid choice. Please try again.");
			adminRouteAdd();
			return;
		} else if(origin == i) {
			admin2(1);
			return;
		}
		System.out.println("Please select your destination airport:");
		Integer dest = parseInt(input.nextLine());
		if(dest == -1) {
			adminRouteAdd();
			return;
		} else if(dest <= 0 || dest > i) {
			System.out.println("That is not a valid choice. Please try again.");
			adminRouteAdd();
			return;
		} else if(dest == i) {
			admin2(1);
			return;
		}
		
		r.setOriginAirport(airports.get(origin - 1));
		if(dest == origin) {
			System.out.println("Destination Airport must be different than the Origin Airport. Please try again.");
			adminRouteAdd();
			return;
		} else {
			r.setDestAirport(airports.get(dest - 1));
		}				
		
		database.addRoute(r);
		admin1();
		return;
	}
	
	public static void adminFlightAdd() {
		Flight f = new Flight();
		
		List<Route> routes = database.getRoutes();
		int routeIndex = 1;
		for(Route r : routes) {
			System.out.println("\t" + routeIndex + ") " + r.getOriginAirport().getAirportCode() + " -> " + r.getDestAirport().getAirportCode());
			routeIndex++;
		}
		System.out.println("\t" + routeIndex + ") Quit to previous");
		System.out.println();
		System.out.println("Please select new route:");
		Integer route = parseInt(input.nextLine());
		if(route == -1) {
			adminFlightUpdate();
			return;
		} else if(route <= 0 || route > routeIndex) {
			System.out.println("That is not a valid choice. Please try again.");
			adminFlightUpdate();
			return;
		} else if(route == routeIndex) {
			admin2(1);
			return;
		}
		
		List<Airplane> airplanes = database.getAirplanes();
		int planeIndex = 1;
		for(Airplane a : airplanes) {
			System.out.println("\t" + planeIndex + ") " + a.getId() + " - " + a.getType().getMaxCapacity() + " max passengers");
			planeIndex++;
		}
		System.out.println("\t" + planeIndex + ") Quit to previous");
		System.out.println();
		System.out.println("Please select new airplane:");
		Integer plane = parseInt(input.nextLine());
		if(plane == -1) {
			adminFlightUpdate();
			return;
		} else if(plane <= 0 || plane > planeIndex) {
			System.out.println("That is not a valid choice. Please try again.");
			adminFlightUpdate();
			return;
		} else if(plane == planeIndex) {
			admin2(1);
			return;
		}
		
		System.out.println("Please enter new Departure Time (YYYY-MM-DD HH:MI:SS) or enter QUIT to quit:");
		String departure = input.nextLine();
		if("QUIT".equals(departure.toUpperCase())) {
			admin2(1);
			return;
		}
		try {
			new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse(departure);
		} catch (ParseException e) {
			System.out.println("Incorrect Formatting. Please try again.");
			adminFlightUpdate();
			return;
		}
		
		System.out.println("Please enter new number of reserved seats or enter QUIT to quit:");
		String s = input.nextLine();
		Integer seats = 0;
		if("QUIT".equals(s.toUpperCase())) {
			admin2(1);
			return;
		} else {
			seats = parseInt(s);
			if(seats == -1) {
				adminFlightUpdate();
				return;
			}
		}
		
		System.out.println("Please enter new seat price or enter QUIT to quit:");
		String pr = input.nextLine();
		Float price = 0.0f;
		if("QUIT".equals(pr.toUpperCase())) {
			admin2(1);
			return;
		} else {
			price = parseFloat(pr);
			if(price == -1.0f) {
				adminFlightUpdate();
				return;
			}
		}
		
		f.setRoute(routes.get(route-1));
		f.setAirplane(airplanes.get(plane-1));
		f.setDeparture(departure);
		f.setReservedSeats(seats);
		f.setPrice(price);	
		f.setId((f.getRoute().getId() * 1000) + f.getAirplane().getId() + f.getReservedSeats());
		while(database.getFlight(f.getId()).size() != 0) {
			f.setId(f.getId()+1);
		}
		
		database.addFlight(f);				
		admin1();
		return;
	}
	
	public static void adminUserAdd() {
		User u = new User();
		
		List<UserRole> urs = database.getUserRoles();
		int i = 1;
		for(UserRole ur : urs) {
			System.out.println("\t" + i + ") " + ur.getName());
			i++;
		}
		System.out.println("\t" + i + ") Quit to Previous");
		System.out.println();
		System.out.println("Please select new user role:");
		Integer role = parseInt(input.nextLine());
		if(role == -1) {
			adminUserUpdate();
			return;
		} else if (role <= 0 || role > i) {
			System.out.println("That is not a valid choice. Please try again.");
			adminUserUpdate();
			return;
		} else if (role == i) {
			admin2(1);
			return;
		}
		
		System.out.println("Please enter new first name or enter QUIT to quit:");
		String firstName = input.nextLine();
		if("QUIT".equals(firstName.toUpperCase())) {
			admin2(1);
			return;
		}
		System.out.println("Please enter new last name or enter QUIT to quit:");
		String lastName = input.nextLine();
		if("QUIT".equals(lastName.toUpperCase())) {
			admin2(1);
			return;
		}
		System.out.println("Please enter new username or enter QUIT to quit:");
		String username = input.nextLine();
		if("QUIT".equals(username.toUpperCase())) {
			admin2(1);
			return;
		}
		System.out.println("Please enter new email or enter QUIT to quit:");
		String email = input.nextLine();
		if("QUIT".equals(email.toUpperCase())) {
			admin2(1);
			return;
		}
		System.out.println("Please enter new password or enter QUIT to quit:");
		String password = input.nextLine();
		if("QUIT".equals(password.toUpperCase())) {
			admin2(1);
			return;
		}
		System.out.println("Please enter new phone or enter QUIT to quit:");
		String phone = input.nextLine();
		if("QUIT".equals(phone.toUpperCase())) {
			admin2(1);
			return;
		}
		
		u.setRole(urs.get(role-1));
		u.setFirstName(firstName);
		u.setLastName(lastName);
		u.setUsername(username);
		u.setEmail(email);
		u.setPassword(password);
		u.setPhone(phone);
		
		database.addUser(u);
		admin1();
		return;
	}
	
	public static void adminAirportUpdate() {
		List<Airport> airports = database.getAirports();
		int index = 1;
		for(Airport a : airports) {
			System.out.println("\t" + index + ") " + a.getAirportCode() + ", " + a.getCityName());
			index++;
		}
		System.out.println("\t" + index + ") Quit to previous");
		System.out.println();
		Integer selection = parseInt(input.nextLine());
		if(selection == -1) {
			adminAirportUpdate();
			return;
		} else if (selection == index) {
			admin2(3); //3 = delete
			return;
		} else if (selection > 0 && selection < index) {
			Airport a = airports.get(selection - 1);
			System.out.println("Please enter new Airport Code or enter N/A for no change:");
			String code = input.nextLine();
			if(code.length() != 3) {
				System.out.println("Invalid code formatting. Please try again.");
				adminAirportUpdate();
				return;
			}
			System.out.println("Please enter new Airport City or enter N/A for no change:");
			String city = input.nextLine();
			if(!"N/A".equals(code.toUpperCase())) {
				a.setAirportCode(code);
			}
			if(!"N/A".equals(city.toUpperCase())) {
				a.setCityName(city);
			}
			if(database.updateAirport(a) != 0) {
				System.out.println("Something went wrong, airport could not be updated.");				
			}
			admin1();
			return;			
		} else {
			System.out.println("That is not a valid choice. Please try again.");
			adminAirportUpdate();
			return;
		}
	}
	
	public static void adminRouteUpdate() {
		List<Route> routes = database.getRoutes();
		int index = 1;
		for(Route r : routes) {
			System.out.println("\t" + index + ") " + r.getOriginAirport().getAirportCode() + " -> " + r.getDestAirport().getAirportCode());
			index++;
		}
		System.out.println("\t" + index + ") Quit to previous");
		System.out.println();
		Integer selection = parseInt(input.nextLine());
		if(selection == -1) {
			adminRouteUpdate();
			return;
		} else if (selection == index) {
			admin2(3); //3 = delete
			return;
		} else if (selection > 0 && selection < index) {
			Route r = routes.get(selection - 1);
			
			List<Airport> airports = database.getAirports();
			int i = 1;
			for(Airport a : airports) {
				System.out.println("\t" + i + ") " + a.getAirportCode() + ", " + a.getCityName());
				i++;
			}
			System.out.println("\t" + i + ") No Change");
			System.out.println();
			System.out.println("Please select your origin airport:");
			Integer origin = parseInt(input.nextLine());
			if(origin == -1) {
				adminRouteUpdate();
				return;
			} else if(origin <= 0 || origin > i) {
				System.out.println("That is not a valid choice. Please try again.");
				adminRouteUpdate();
				return;
			}
			System.out.println("Please select your destination airport:");
			Integer dest = parseInt(input.nextLine());
			if(dest == -1) {
				adminRouteUpdate();
				return;
			} else if(dest <= 0 || dest > i) {
				System.out.println("That is not a valid choice. Please try again.");
				adminRouteUpdate();
				return;
			}
			if(origin > 0 && origin < i) {
				r.setOriginAirport(airports.get(origin - 1));
			}
			if(dest > 0 && dest < i) {
				if(dest == origin || (origin == i && airports.get(dest - 1).getAirportCode() == r.getOriginAirport().getAirportCode())) {
					System.out.println("Destination Airport must be different than the Origin Airport. Please try again.");
					adminRouteUpdate();
					return;
				} else {
					r.setDestAirport(airports.get(dest - 1));
				}				
			}
			
			if(database.updateRoute(r) != 0) {
				System.out.println("Something went wrong, route could not be updated.");				
			}
			admin1();
			return;
		} else {
			System.out.println("That is not a valid choice. Please try again.");
			adminRouteUpdate();
			return;
		}
	}
	
	public static void adminFlightUpdate() {
		List<Flight> flights = database.getFlights();
		int index = 1;
		for(Flight f:flights) {
			System.out.println("\t" + index + ") " + f.getRoute().getOriginAirport().getAirportCode() + ", " + f.getRoute().getOriginAirport().getCityName() + " -> " + f.getRoute().getDestAirport().getAirportCode() + ", " + f.getRoute().getDestAirport().getCityName());
			index++;
		}
		System.out.println("\t" + index + ") Quit to previous");
		System.out.println();
		Integer selection = parseInt(input.nextLine());
		if(selection == -1) {
			adminFlightUpdate();
			return;
		} else if (selection == index) {
			admin2(3); //3 = delete
			return;
		} else if (selection > 0 && selection < index) {
			Flight f = flights.get(selection - 1);
		
			List<Route> routes = database.getRoutes();
			int routeIndex = 1;
			for(Route r : routes) {
				System.out.println("\t" + routeIndex + ") " + r.getOriginAirport().getAirportCode() + " -> " + r.getDestAirport().getAirportCode());
				routeIndex++;
			}
			System.out.println("\t" + routeIndex + ") No Change");
			System.out.println();
			System.out.println("Please select new route:");
			Integer route = parseInt(input.nextLine());
			if(route == -1) {
				adminFlightUpdate();
				return;
			} else if(route <= 0 || route > routeIndex) {
				System.out.println("That is not a valid choice. Please try again.");
				adminFlightUpdate();
				return;
			}
			
			List<Airplane> airplanes = database.getAirplanes();
			int planeIndex = 1;
			for(Airplane a : airplanes) {
				System.out.println("\t" + planeIndex + ") " + a.getId() + " - " + a.getType().getMaxCapacity() + " max passengers");
				planeIndex++;
			}
			System.out.println("\t" + planeIndex + ") No Change");
			System.out.println();
			System.out.println("Please select new airplane:");
			Integer plane = parseInt(input.nextLine());
			if(plane == -1) {
				adminFlightUpdate();
				return;
			} else if(plane <= 0 || plane > planeIndex) {
				System.out.println("That is not a valid choice. Please try again.");
				adminFlightUpdate();
				return;
			}
			
			System.out.println("Please enter new Departure Time (YYYY-MM-DD HH:MI:SS) or enter N/A for no change:");
			String departure = input.nextLine();
			if("N/A".equals(departure.toUpperCase())) {
				departure = f.getDeparture();
			}
			try {
				new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse(departure);
			} catch (ParseException e) {
				System.out.println("Incorrect Formatting. Please try again.");
				adminFlightUpdate();
				return;
			}
			
			System.out.println("Please enter new number of reserved seats or enter N/A for no change:");
			String s = input.nextLine();
			Integer seats = 0;
			if("N/A".equals(s.toUpperCase())) {
				seats = f.getReservedSeats();
			} else {
				seats = parseInt(s);
				if(seats == -1) {
					adminFlightUpdate();
					return;
				}
			}
			
			System.out.println("Please enter new seat price or enter N/A for no change:");
			String pr = input.nextLine();
			Float price = 0.0f;
			if("N/A".equals(pr.toUpperCase())) {
				price = f.getPrice();
			} else {
				price = parseFloat(pr);
				if(price == -1.0f) {
					adminFlightUpdate();
					return;
				}
			}
			
			if(route != routeIndex) {
				f.setRoute(routes.get(route-1));
			}
			if(plane != planeIndex) {
				f.setAirplane(airplanes.get(plane-1));
			}
			f.setDeparture(departure);
			f.setReservedSeats(seats);
			f.setPrice(price);			
			
			if(database.updateFlight(f) != 0) {
				System.out.println("Something went wrong, flight could not be updated.");				
			}		
			admin1();
			return;
		} else {
			System.out.println("That is not a valid choice. Please try again.");
			adminFlightUpdate();
			return;
		}
	}
	
	public static void adminUserUpdate() {
		List<User> users = database.getUsers();
		int index = 1;
		for(User u : users) {
			System.out.println("\t" + index + ") " + u.getFirstName() + " " + u.getLastName() + " (" + u.getEmail() + " / " + u.getUsername() + ")");
			index++;
		}
		System.out.println("\t" + index + ") Quit to previous");
		System.out.println();
		Integer selection = parseInt(input.nextLine());
		if(selection == -1) {
			adminUserUpdate();
			return;
		} else if (selection == index) {
			admin2(3); //3 = delete
			return;
		} else if (selection > 0 && selection < index) {
			User u = users.get(selection - 1);
			
			List<UserRole> urs = database.getUserRoles();
			int i = 1;
			for(UserRole ur : urs) {
				System.out.println("\t" + i + ") " + ur.getName());
				i++;
			}
			System.out.println("\t" + i + ") No Change");
			System.out.println();
			System.out.println("Please select new user role:");
			Integer role = parseInt(input.nextLine());
			if(role == -1) {
				adminUserUpdate();
				return;
			} else if (role <= 0 || role > i) {
				System.out.println("That is not a valid choice. Please try again.");
				adminUserUpdate();
				return;
			}
			
			System.out.println("Please enter new first name or enter N/A for no change:");
			String firstName = input.nextLine();
			System.out.println("Please enter new last name or enter N/A for no change:");
			String lastName = input.nextLine();
			System.out.println("Please enter new username or enter N/A for no change:");
			String username = input.nextLine();
			System.out.println("Please enter new email or enter N/A for no change:");
			String email = input.nextLine();
			System.out.println("Please enter new password or enter N/A for no change:");
			String password = input.nextLine();
			System.out.println("Please enter new phone or enter N/A for no change:");
			String phone = input.nextLine();
			
			if(role > 0 && role < i) {
				u.setRole(urs.get(role-1));
			}
			if(!"N/A".equals(firstName.toUpperCase())) {
				u.setFirstName(firstName);
			}
			if(!"N/A".equals(lastName.toUpperCase())) {
				u.setLastName(lastName);
			}
			if(!"N/A".equals(username.toUpperCase())) {
				u.setUsername(username);
			}
			if(!"N/A".equals(email.toUpperCase())) {
				u.setEmail(email);
			}
			if(!"N/A".equals(password.toUpperCase())) {
				u.setPassword(password);
			}
			if(!"N/A".equals(phone.toUpperCase())) {
				u.setPhone(phone);
			}
			
			if(database.updateUser(u) != 0) {
				System.out.println("Something went wrong, user could not be updated.");				
			}
			admin1();
			return;
		} else {
			System.out.println("That is not a valid choice. Please try again.");
			adminUserUpdate();
			return;
		}
	}
	
	public static void adminAirportDelete() {
		List<Airport> airports = database.getAirports();
		int index = 1;
		for(Airport a : airports) {
			System.out.println("\t" + index + ") " + a.getAirportCode() + ", " + a.getCityName());
			index++;
		}
		System.out.println("\t" + index + ") Quit to previous");
		System.out.println();
		Integer selection = parseInt(input.nextLine());
		if(selection == -1) {
			adminAirportDelete();
			return;
		} else if (selection == index) {
			admin2(3); //3 = delete
			return;
		} else if (selection > 0 && selection < index) {
			database.deleteAirport(airports.get(selection - 1));
			admin1();
			return;
		} else {
			System.out.println("That is not a valid choice. Please try again.");
			adminAirportDelete();
			return;
		}
	}
	
	public static void adminRouteDelete() {
		List<Route> routes = database.getRoutes();
		int index = 1;
		for(Route r : routes) {
			System.out.println("\t" + index + ") " + r.getOriginAirport().getAirportCode() + " -> " + r.getDestAirport().getAirportCode());
			index++;
		}
		System.out.println("\t" + index + ") Quit to previous");
		System.out.println();
		Integer selection = parseInt(input.nextLine());
		if(selection == -1) {
			adminRouteDelete();
			return;
		} else if (selection == index) {
			admin2(3); //3 = delete
			return;
		} else if (selection > 0 && selection < index) {
			database.deleteRoute(routes.get(selection - 1));
			admin1();
			return;
		} else {
			System.out.println("That is not a valid choice. Please try again.");
			adminRouteDelete();
			return;
		}
	}
	
	public static void adminFlightDelete() {
		List<Flight> flights = database.getFlights();
		int index = 1;
		for(Flight f:flights) {
			System.out.println("\t" + index + ") " + f.getRoute().getOriginAirport().getAirportCode() + ", " + f.getRoute().getOriginAirport().getCityName() + " -> " + f.getRoute().getDestAirport().getAirportCode() + ", " + f.getRoute().getDestAirport().getCityName());
			index++;
		}
		System.out.println("\t" + index + ") Quit to previous");
		System.out.println();
		Integer selection = parseInt(input.nextLine());
		if(selection == -1) {
			adminFlightDelete();
			return;
		} else if (selection == index) {
			admin2(3); //3 = delete
			return;
		} else if (selection > 0 && selection < index) {
			database.deleteFlight(flights.get(selection - 1));
			admin1();
			return;
		} else {
			System.out.println("That is not a valid choice. Please try again.");
			adminFlightDelete();
			return;
		}
	}
	
	public static void adminUserDelete() {
		List<User> users = database.getUsers();
		int index = 1;
		for(User u : users) {
			System.out.println("\t" + index + ") " + u.getFirstName() + " " + u.getLastName() + " (" + u.getEmail() + " / " + u.getUsername() + ")");
			index++;
		}
		System.out.println("\t" + index + ") Quit to previous");
		System.out.println();
		Integer selection = parseInt(input.nextLine());
		if(selection == -1) {
			adminUserDelete();
			return;
		} else if (selection == index) {
			admin2(3); //3 = delete
			return;
		} else if (selection > 0 && selection < index) {
			database.deleteUser(users.get(selection - 1));
			admin1();
			return;
		} else {
			System.out.println("That is not a valid choice. Please try again.");
			adminUserDelete();
			return;
		}
	}
	
	public static void traveler() {
		System.out.println("Enter your Membership Number:");
		Integer id = parseInt(input.nextLine());
		if(id == -1) {
			traveler();
			return;
		}
		User user = database.validateTraveler(id);
		if(user == null) {
			System.out.println("Invalid membership number. Please try again.");
			traveler();
			return;
		}
		traveler1(user);
	}
	
	public static void traveler1(User user) {
		System.out.println("\t1) Book a Ticket");
		System.out.println("\t2) Cancel an Upcoming Trip");
		System.out.println("\t3) Quit to Previous");
		System.out.println();
		Integer selection = parseInt(input.nextLine());
		if(selection == -1) {
			traveler1(user);
			return;
		}
		switch(selection) {
			case 1:
				travelerBookTicket(user);
				return;
			case 2:
				travelerCancelTrip(user);
				return;
			case 3:
				getUserCat();
				return;
			default:
				System.out.println("That is not a valid choice. Please try again.");
				traveler1(user);
				return;
		}
	}
	
	public static void travelerBookTicket(User user) {
		List<Flight> flights = database.getFlights();
		int index = 1;
		for(Flight f:flights) {
			if(f.getAirplane().getType().getMaxCapacity() - f.getReservedSeats() > 0) {
				System.out.println("\t" + index + ") " + f.getRoute().getOriginAirport().getAirportCode() + ", " + f.getRoute().getOriginAirport().getCityName() + " -> " + f.getRoute().getDestAirport().getAirportCode() + ", " + f.getRoute().getDestAirport().getCityName());
				index++;
			}
		}
		System.out.println("\t" + index + ") Quit to previous");
		System.out.println();		
		Integer selection = parseInt(input.nextLine());
		if(selection == -1) {
			travelerBookTicket(user);
			return;
		} else if(selection == index) {
			traveler1(user);
			return;
		} else if(selection >= 1 && selection < index) {
			database.addBooking(flights.get(selection - 1), user);
			traveler1(user);
			return;
		} else {
			System.out.println("That is not a valid choice. Please try again.");
			travelerBookTicket(user);
			return;
		}
	}
	
	public static void travelerCancelTrip(User user) {
		List<FlightBooking> fbs = database.getFlightBookings(user);
		int index = 1;
		for(FlightBooking fb : fbs) {
			System.out.println("\t" + index + ") " + fb.getFlight().getRoute().getOriginAirport().getAirportCode() + " -> " + fb.getFlight().getRoute().getDestAirport().getAirportCode());
			index++;
		}
		System.out.println("\t" + index + ") Quit to previous");
		System.out.println();
		Integer selection = parseInt(input.nextLine());
		if(selection == -1) {
			travelerCancelTrip(user);
			return;
		} else if(selection == index) {
			traveler1(user);
			return;
		} else if(selection >= 1 && selection < index) {
			database.deleteBooking(fbs.get(selection - 1).getBooking());
			traveler1(user);
			return;
		} else {
			System.out.println("That is not a valid choice. Please try again.");
			travelerBookTicket(user);
			return;
		}
	}
	
	public static Float parseFloat(String s) {
		try {
			Float f = Float.parseFloat(s);
			if(f == -1.0f) {
				return -1.001f; //-1.0f is used as an error code
			} else {
				return f;
			}
		} catch (Exception e) {
			System.out.println("That is not a valid float. Please try again.");
			return -1.0f;
		}
	}
	
	public static Integer parseInt(String s) {
		try {
			Integer i = Integer.parseInt(s);
			if(i == -1) {
				return -2; //-1 is used as an error code
			} else {
				return i;
			}
		} catch (Exception e) {
			System.out.println("That is not a valid integer. Please try again.");
			return -1;
		}
	}
}
