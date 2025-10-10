import java.util.Scanner;
/*
 * Vehicle Management System
 * This Java program demonstrates the use of abstract classes and interfaces
 * to model different types of vehicles: ElectricCar, GasCar, and HybridCar.
 * 
 * The `refuel()` method is abstract in `Vehicle`, requiring each subclass to provide 
 * its own specific implementation based on how it refuels (charging or adding gas).
 */

// Main class to test the vehicle system
public class VehicleSolutionAbstractInterface {
    public static void main(String[] args) {
        // Creating instances of different vehicle types
        ElectricCar ioniq = new ElectricCar("Hyundai Ioniq5", 60);
        GasCar honda = new GasCar("Honda Civic", 50);
        HybridCar prius = new HybridCar("Toyota Prius", 55);
        
        // Testing ElectricCar
        ioniq.accelerate(10);
        ioniq.refuel();
        System.out.println();
        
        // Testing GasCar
        honda.accelerate(15);
        honda.refuel();
        System.out.println();
        
        // Testing HybridCar
        prius.accelerate(12);
        prius.refuel();


    }
}

// Abstract class representing shared characteristics of all vehicles
abstract class Vehicle {
    private String brand;
    private int speed;
    
    public Vehicle(String brand, int speed) {
        this.brand = brand;
        this.speed = speed;
    }
    
    //Brand Getter
	public String getBrand()
	{
		return brand;
	}
    
    //Speed Getter
	public int getSpeed()
	{
		return speed;
	}
    
    // Method to accelerate the vehicle
    public void accelerate(int increase) {
        speed += increase;
        System.out.println(brand + " accelerates to " + speed + " mph.");
    }
    
    // Abstract method to be implemented by subclasses
    public abstract void refuel();
}

// Interface for electric vehicles
interface Electric {
    void chargeBattery();
}

// Interface for gas-powered vehicles
interface GasPowered {
    void refuelGas();
}

// Concrete class for electric cars
class ElectricCar extends Vehicle implements Electric {
    public ElectricCar(String brand, int speed) {
        super(brand, speed);
    }
    
    // Implementing method from Electric interface
    @Override
	public void chargeBattery() {
        System.out.println("Charging battery for " + this.getBrand());
    }
    
    /*
     * Implements the abstract `refuel()` method from Vehicle.
     * Since electric cars do not use gas, they "refuel" by charging.
     */
    @Override
    public void refuel() {
        chargeBattery(); // Electric cars "refuel" by charging
    }
}

// Concrete class for gas-powered cars
class GasCar extends Vehicle implements GasPowered {
    public GasCar(String brand, int speed) {
        super(brand, speed);
    }
    
    // Implementing method from GasPowered interface
    @Override
    public void refuelGas() {
        System.out.println("Refueling gas tank for " + this.getBrand());
    }
    
    // Implementing abstract method from Vehicle class
    @Override
    public void refuel() {
        refuelGas();
    }
}

// Concrete class for hybrid cars
class HybridCar extends Vehicle implements Electric, GasPowered {
    public HybridCar(String brand, int speed) {
        super(brand, speed);
    }
    
    // Implementing methods from Electric and GasPowered interfaces
    @Override
    public void chargeBattery() {
        System.out.println("Charging battery for " + this.getBrand());
    }
    
    @Override
    public void refuelGas() {
        System.out.println("Refueling gas tank for " + this.getBrand());
    }
    
    /*
     * Implements the abstract `refuel()` method from Vehicle.
     * Hybrid cars can refuel using both charging and gas, so the user must choose.
     */
    @Override
    public void refuel() {
        Scanner input = new Scanner(System.in);
        System.out.println("Choose refueling method for " + this.getBrand() + " (1: Charge Battery, 2: Refuel Gas): ");
        int choice = input.nextInt();
        if (choice == 1) {
            chargeBattery();
        } else if (choice == 2) {
            refuelGas();
        } else {
            System.out.println("Invalid choice, no refueling performed.");
        }
        input.close();
        
    }
}

