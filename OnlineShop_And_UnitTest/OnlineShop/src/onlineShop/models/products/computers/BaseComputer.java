package onlineShop.models.products.computers;

import onlineShop.common.constants.ExceptionMessages;
import onlineShop.common.constants.OutputMessages;
import onlineShop.models.products.BaseProduct;
import onlineShop.models.products.Product;
import onlineShop.models.products.components.Component;
import onlineShop.models.products.peripherals.Peripheral;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseComputer extends BaseProduct implements Computer {
    private List<Component> components;
    private List<Peripheral> peripherals;

    public BaseComputer(int id, String manufacturer, String model, double price, double overallPerformance) {
        super(id, manufacturer, model, price, overallPerformance);
        this.components = new ArrayList<>();
        this.peripherals = new ArrayList<>();
    }

    @Override
    public List<Component> getComponents() {
        return this.components;
    }

    @Override
    public List<Peripheral> getPeripherals() {
        return this.peripherals;
    }

    @Override
    public void addComponent(Component component) {
        String componentType = component.getClass().getSimpleName();

        Component currentComponent = this.components.stream()
                .filter(c -> c.getClass().getSimpleName().equals(componentType))
                .findFirst()
                .orElse(null);

        if (currentComponent != null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.EXISTING_COMPONENT,
                    componentType, this.getClass().getSimpleName(), getId()));
        }
        this.components.add(component);
    }

    @Override
    public Component removeComponent(String componentType) {
        Component component = this.components.stream()
                .filter(c -> c.getClass().getSimpleName().equals(componentType))
                .findFirst()
                .orElse(null);

        if (component == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.NOT_EXISTING_COMPONENT,
                    componentType, this.getClass().getSimpleName(), getId()));
        }
        this.components.remove(component);
        return component;
    }

    @Override
    public void addPeripheral(Peripheral peripheral) {
        String peripheralType = peripheral.getClass().getSimpleName();

        Peripheral currentPeripheral = this.peripherals.stream()
                .filter(p -> p.getClass().getSimpleName().equals(peripheralType))
                .findFirst()
                .orElse(null);

        if (currentPeripheral != null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.EXISTING_PERIPHERAL,
                    peripheralType, this.getClass().getSimpleName(), getId()));
        }
        this.peripherals.add(peripheral);
    }

    @Override
    public Peripheral removePeripheral(String peripheralType) {
        Peripheral peripheral = this.peripherals.stream()
                .filter(p -> p.getClass().getSimpleName().equals(peripheralType))
                .findFirst()
                .orElse(null);

        if (peripheral == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.NOT_EXISTING_PERIPHERAL,
                    peripheralType, this.getClass().getSimpleName(), getId()));
        }
        this.peripherals.remove(peripheral);
        return peripheral;
    }


    @Override
    public String toString() {
        StringBuilder computerData = new StringBuilder();

        double totalPrice = this.getPrice();

        double averageComponentsPerformance = this.getOverallPerformance();
        if (!this.components.isEmpty()) {
            averageComponentsPerformance = this.components.stream()
                    .mapToDouble(Product::getOverallPerformance)
                    .average()
                    .getAsDouble() + this.getOverallPerformance();
            totalPrice += this.components.stream().mapToDouble(Component::getPrice).sum();
        }

        double averagePeripheralPerformance = 0;
        if (!this.peripherals.isEmpty()) {
            averagePeripheralPerformance = this.peripherals.stream()
                    .mapToDouble(Peripheral::getOverallPerformance)
                    .average()
                    .getAsDouble();
            totalPrice += this.peripherals.stream().mapToDouble(Peripheral::getPrice).sum();
        }

        computerData.append(String.format("Overall Performance: %.2f. Price: %.2f - %s: %s %s (Id: %d)",
           averageComponentsPerformance, totalPrice, this.getClass().getSimpleName(), getManufacturer(), getModel(), getId()))
                .append(System.lineSeparator())
                .append(String.format(" " + OutputMessages.COMPUTER_COMPONENTS_TO_STRING, this.components.size()))
                .append(System.lineSeparator());

        for (Component component : this.components) {
            computerData.append("  ").append(component)
                    .append(System.lineSeparator());
        }


        computerData.append(String.format(" " + OutputMessages.COMPUTER_PERIPHERALS_TO_STRING,
                this.peripherals.size(), averagePeripheralPerformance))
                .append(System.lineSeparator());
        for (Peripheral peripheral : this.peripherals) {
            computerData.append("  ").append(peripheral)
                    .append(System.lineSeparator());
        }

        return computerData.toString().trim();
    }
}
