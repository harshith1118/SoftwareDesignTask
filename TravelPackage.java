package TravelPackage;

import java.util.List;
import java.util.ArrayList;

class Destination {
    String name;
    List<Activity> activities;

    Destination(String name) {
        this.name = name;
        this.activities = new ArrayList<>();
    }

    void addActivity(Activity activity) {
        activities.add(activity);
    }
}

class Activity {
    String name;
    String description;
    double cost;
    int capacity;
    Destination destination;

    Activity(String name, String description, double cost, int capacity, Destination destination) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.capacity = capacity;
        this.destination = destination;
    }

    boolean hasAvailableSpace() {
        return capacity > 0;
    }

    void signUp(Passenger passenger) {
        if (hasAvailableSpace() && passenger.hasSufficientBalance(cost)) {
            passenger.deductBalance(cost);
            capacity--;
            passenger.activities.add(this);
        }
    }
}

class Passenger {
    String name;
    int passengerNumber;
    double balance;
    PassengerType type;
    List<Activity> activities;

    Passenger(String name, int passengerNumber, PassengerType type) {
        this.name = name;
        this.passengerNumber = passengerNumber;
        this.type = type;
        this.activities = new ArrayList<>();
        this.balance = type == PassengerType.GOLD ? 0.9 : 1.0; // Initialize balance with discount for gold passengers
    }

    boolean hasSufficientBalance(double cost) {
        return balance >= cost;
    }

    void deductBalance(double cost) {
        balance -= cost;
    }
}

enum PassengerType {
    STANDARD, GOLD, PREMIUM
}

class TravelPackage {
    String name;
    int passengerCapacity;
    List<Destination> itinerary;
    List<Passenger> passengers;

    TravelPackage(String name, int passengerCapacity) {
        this.name = name;
        this.passengerCapacity = passengerCapacity;
        this.itinerary = new ArrayList<>();
        this.passengers = new ArrayList<>();
    }

    void addDestination(Destination destination) {
        itinerary.add(destination);
    }

    void addPassenger(Passenger passenger) {
        passengers.add(passenger);
    }

    void printItinerary() {
        System.out.println("Itinerary for " + name + ":");
        for (Destination destination : itinerary) {
            System.out.println("Destination: " + destination.name);
            for (Activity activity : destination.activities) {
                System.out.println("  Activity: " + activity.name + ", Cost: " + activity.cost + ", Capacity: "
                        + activity.capacity);
            }
        }
    }

    void printPassengerList() {
        System.out.println("Passenger List for " + name + ":");
        System.out.println("Capacity: " + passengerCapacity + ", Enrolled Passengers: " + passengers.size());
        for (Passenger passenger : passengers) {
            System.out.println("  Passenger: " + passenger.name + ", Number: " + passenger.passengerNumber);
        }
    }

    void printPssengerDetails(Passenger passenger) {
        System.out.println("   Details for passenger " + passenger.name + " : ");
        System.out.println("Number :" + passenger.passengerNumber + " Balnce :" + passenger.balance);
        System.out.println(" Enrolled activites ");
        for (Activity activity : passenger.activities) {
            System.out.println("Activity:" + activity.name + " at " + activity.destination.name + ",Cost:"
                    + activity.cost);
        }
    }

    void printAvilableActvites() {
        System.out.println("Avilable actvites for " + name + " :");
        for (Destination destination : itinerary) {
            for (Activity activity : destination.activities) {
                if (activity.hasAvailableSpace()) {
                    System.out.println(
                            "Activity: " + activity.name + " at " + destination.name + " ,cost " + activity.cost);
                }
            }
        }
    }

}

// Test-case

class TravelAgencySystem {
    private Object org;

    public static void main(String[] args) {
        // create travel package
        TravelPackage travelPackage = new TravelPackage("Package1", 10);

        // create destination
        Destination city1 = new Destination("Banglore");
        Destination beach1 = new Destination("Malpe");
        Destination mountain1 = new Destination("Kudremukh");

        // create activities

        Activity sightseeing = new Activity("Sightseeing", "City tour", 50, 5, city1);
        Activity snorkeling = new Activity("Snorkeling", "Under water advanture", 150, 3, beach1);
        Activity hiking = new Activity("Hiking", "Explore the trails", 340, 3, mountain1);

        // add activites to destination
        city1.addActivity(sightseeing);
        beach1.addActivity(snorkeling);
        mountain1.addActivity(hiking);

        // add destination
        travelPackage.addDestination(city1);
        travelPackage.addDestination(beach1);
        travelPackage.addDestination(mountain1);

        // create passenger
        Passenger john = new Passenger("john", 1, PassengerType.STANDARD);
        Passenger Krishna = new Passenger("Krishna", 2, PassengerType.GOLD);
        Passenger Suhana = new Passenger("Suhana", 3, PassengerType.PREMIUM);

        // add passenger to trvael package

        travelPackage.addPassenger(john);
        travelPackage.addPassenger(Krishna);
        travelPackage.addPassenger(Suhana);

        // print itineary
        travelPackage.printItinerary();

        // printpassenger list
        travelPackage.printPassengerList();

        // sign up for activites
        sightseeing.signUp(john);
        snorkeling.signUp(Krishna);
        hiking.signUp(Suhana);

        // PRINT PASSENGER DETAILS

        travelPackage.printPssengerDetails(john);
        travelPackage.printPssengerDetails(Krishna);
        travelPackage.printPssengerDetails(Suhana);

        travelPackage.printAvilableActvites();

    }
}