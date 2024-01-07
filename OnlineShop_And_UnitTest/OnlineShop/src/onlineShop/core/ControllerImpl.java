package onlineShop.core;

import onlineShop.common.constants.ExceptionMessages;
import onlineShop.common.constants.OutputMessages;
import onlineShop.core.interfaces.Controller;
import onlineShop.models.products.Product;
import onlineShop.models.products.components.*;
import onlineShop.models.products.computers.Computer;
import onlineShop.models.products.computers.DesktopComputer;
import onlineShop.models.products.computers.Laptop;
import onlineShop.models.products.peripherals.*;

import java.util.*;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {
    private Map<Integer, Computer> computers;

    //TODO: Създай списък с компоненти и списък с периферий!!!
    public ControllerImpl() {
        this.computers = new LinkedHashMap<>();
    }

    @Override
    public String addComputer(String computerType, int id, String manufacturer, String model, double price) {
        if (this.computers.containsKey(id)) {
            throw new IllegalArgumentException(ExceptionMessages.EXISTING_COMPUTER_ID);
        }
        Computer computer = createComputer(computerType, id, manufacturer, model, price);
        this.computers.put(id, computer);
        return String.format(OutputMessages.ADDED_COMPUTER, id);
    }

    @Override
    public String addPeripheral(int computerId, int id, String peripheralType, String manufacturer, String model, double price, double overallPerformance, String connectionType) {
        checkIsExistComputer(computerId);
        Peripheral peripheral = this.computers.get(computerId)
                .getPeripherals()
                .stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
        if (peripheral != null) {
            throw new IllegalArgumentException(ExceptionMessages.EXISTING_PERIPHERAL_ID);
        }
        peripheral = createPeripheral(id, peripheralType, manufacturer, model, price, overallPerformance, connectionType);
        this.computers.get(computerId).addPeripheral(peripheral);
        return String.format(OutputMessages.ADDED_PERIPHERAL, peripheralType, id, computerId);
    }

    @Override
    public String removePeripheral(String peripheralType, int computerId) {
        checkIsExistComputer(computerId);
        Computer computer = this.computers.get(computerId);
        int peripheralId = computer.getPeripherals()
                .stream()
                .filter(p -> p.getClass().getSimpleName().equals(peripheralType))
                .mapToInt(Product::getId)
                .findFirst()
                .orElse(0);

        computer.removePeripheral(peripheralType);
        return String.format(OutputMessages.REMOVED_PERIPHERAL, peripheralType, peripheralId);
    }

    @Override
    public String addComponent(int computerId, int id, String componentType, String manufacturer, String model, double price, double overallPerformance, int generation) {
        checkIsExistComputer(computerId);

        Computer computer = this.computers.get(computerId);
        Component component = computer.getComponents()
                .stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
        if (component != null) {
            throw new IllegalArgumentException(ExceptionMessages.EXISTING_COMPONENT_ID);
        }
        component = createComponent(id, componentType, manufacturer, model, price, overallPerformance, generation);
        computer.addComponent(component);
        return String.format(OutputMessages.ADDED_COMPONENT, componentType, id, computerId);
    }


    @Override
    public String removeComponent(String componentType, int computerId) {
        checkIsExistComputer(computerId);
        Computer computer = this.computers.get(computerId);
        int componentId = computer.getComponents()
                .stream()
                .filter(c -> c.getClass().getSimpleName().equals(componentType))
                .mapToInt(Component::getId)
                .findFirst()
                .orElse(0);
        computer.removeComponent(componentType);
        return String.format(OutputMessages.REMOVED_COMPONENT, componentType, componentId);
    }

    @Override
    public String buyComputer(int id) {
        checkIsExistComputer(id);
        Computer computer = this.computers.remove(id);
        return computer.toString();
    }

    @Override
    public String BuyBestComputer(double budget) {
        List<Computer> computersInBudget = getBudgetComputers(budget);
        List<Computer> sortedComputers = computersInBudget.stream()
                .sorted((sec, fir) -> Double.compare(fir.getOverallPerformance(), sec.getOverallPerformance()))
                .collect(Collectors.toList());
        Computer computer = sortedComputers.get(0);
        this.computers.remove(computer.getId());
        return computer.toString();
    }

    @Override
    public String getComputerData(int id) {
        checkIsExistComputer(id);
        Computer computer = this.computers.get(id);
        return computer.toString();
    }

    private List<Computer> getBudgetComputers(double budget) {
        List<Computer> computersListInBudget = this.computers.values()
                .stream()
                .filter(comp -> comp.getPrice() <= budget)
                .collect(Collectors.toList());
        if (computersListInBudget.isEmpty()) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.CAN_NOT_BUY_COMPUTER, budget));
        }
        return computersListInBudget;
    }

    private static Component createComponent(int id, String componentType, String manufacturer, String model, double price, double overallPerformance, int generation) {
        Component component;
        switch (componentType) {
            case "CentralProcessingUnit":
                component = new CentralProcessingUnit(id, manufacturer, model, price, overallPerformance, generation);
                break;
            case "Motherboard":
                component = new Motherboard(id, manufacturer, model, price, overallPerformance, generation);
                break;
            case "PowerSupply":
                component = new PowerSupply(id, manufacturer, model, price, overallPerformance, generation);
                break;
            case "RandomAccessMemory":
                component = new RandomAccessMemory(id, manufacturer, model, price, overallPerformance, generation);
                break;
            case "SolidStateDrive":
                component = new SolidStateDrive(id, manufacturer, model, price, overallPerformance, generation);
                break;
            case "VideoCard":
                component = new VideoCard(id, manufacturer, model, price, overallPerformance, generation);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_COMPONENT_TYPE);
        }
        return component;
    }

    private static Peripheral createPeripheral(int id, String peripheralType, String manufacturer, String model, double price, double overallPerformance, String connectionType) {
        Peripheral peripheral;
        switch (peripheralType) {
            case "Headset":
                peripheral = new Headset(id, manufacturer, model, price, overallPerformance, connectionType);
                break;
            case "Keyboard":
                peripheral = new Keyboard(id, manufacturer, model, price, overallPerformance, connectionType);
                break;
            case "Monitor":
                peripheral = new Monitor(id, manufacturer, model, price, overallPerformance, connectionType);
                break;
            case "Mouse":
                peripheral = new Mouse(id, manufacturer, model, price, overallPerformance, connectionType);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_PERIPHERAL_TYPE);
        }
        return peripheral;
    }

    private static Computer createComputer(String computerType, int id, String manufacturer, String model, double price) {
        Computer computer;
        switch (computerType) {
            case "DesktopComputer":
                computer = new DesktopComputer(id, manufacturer, model, price);
                break;
            case "Laptop":
                computer = new Laptop(id, manufacturer, model, price);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_COMPUTER_TYPE);
        }
        return computer;
    }

    private void checkIsExistComputer(int computerId) {
        if (!this.computers.containsKey(computerId)) {
            throw new IllegalArgumentException(ExceptionMessages.NOT_EXISTING_COMPUTER_ID);
        }
    }
}
