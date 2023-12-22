package robotService.core;

import robotService.common.ConstantMessages;
import robotService.common.ExceptionMessages;
import robotService.entities.robot.FemaleRobot;
import robotService.entities.robot.MaleRobot;
import robotService.entities.robot.Robot;
import robotService.entities.services.MainService;
import robotService.entities.services.SecondaryService;
import robotService.entities.services.Service;
import robotService.entities.supplements.MetalArmor;
import robotService.entities.supplements.PlasticArmor;
import robotService.entities.supplements.Supplement;
import robotService.repositories.SupplementRepository;

import java.util.LinkedHashMap;
import java.util.Map;

public class ControllerImpl implements Controller {
    private SupplementRepository supplements;
    private Map<String, Service> services;

    public ControllerImpl() {
        this.supplements = new SupplementRepository();
        this.services = new LinkedHashMap<>();
    }

    @Override
    public String addService(String type, String name) {
        Service service;
        switch (type) {
            case "MainService":
                service = new MainService(name);
                break;
            case "SecondaryService":
                service = new SecondaryService(name);
                break;
            default:
                throw new NullPointerException(ExceptionMessages.INVALID_SERVICE_TYPE);
        }
        this.services.putIfAbsent(name, service);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_SERVICE_TYPE, type);
    }

    @Override
    public String addSupplement(String type) {
        Supplement supplement;
        switch (type) {
            case "PlasticArmor":
                supplement = new PlasticArmor();
                break;
            case "MetalArmor":
                supplement = new MetalArmor();
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_SUPPLEMENT_TYPE);
        }
        this.supplements.addSupplement(supplement);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_SUPPLEMENT_TYPE, type);
    }

    @Override
    public String supplementForService(String serviceName, String supplementType) {
        Supplement supplement = this.supplements.findFirst(supplementType);
        if (supplement == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.NO_SUPPLEMENT_FOUND, supplementType));
        }
        this.services.get(serviceName).addSupplement(supplement);
        this.supplements.removeSupplement(supplement);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_SUPPLEMENT_IN_SERVICE, supplementType, serviceName);
    }

    @Override
    public String addRobot(String serviceName, String robotType, String robotName, String robotKind, double price) {
        Robot robot;
        switch (robotType) {
            case "MaleRobot":
                robot = new MaleRobot(robotName, robotKind, price);
                break;
            case "FemaleRobot":
                robot = new FemaleRobot(robotName, robotKind, price);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_ROBOT_TYPE);
        }
        String serviceType = this.services.get(serviceName).getClass().getSimpleName();
        if ((serviceType.equals("MainService") && robotType.equals("MaleRobot"))
                || (serviceType.equals("SecondaryService") && robotType.equals("FemaleRobot"))) {
            this.services.get(serviceName).addRobot(robot);
            return String.format(ConstantMessages.SUCCESSFULLY_ADDED_ROBOT_IN_SERVICE, robotType, serviceName);
        }
            return ConstantMessages.UNSUITABLE_SERVICE;
    }

    @Override
    public String feedingRobot(String serviceName) {
        this.services.get(serviceName).feeding();
        int countRobots = this.services.get(serviceName).getRobots().size();
        return String.format(ConstantMessages.FEEDING_ROBOT, countRobots);
    }

    @Override
    public String sumOfAll(String serviceName) {
        double sumService = this.services.get(serviceName).getRobots().stream().mapToDouble(Robot::getPrice).sum();
        double sumSupplements = this.services.get(serviceName).getSupplements().stream().mapToDouble(Supplement::getPrice).sum();
        double totalPrice = sumService + sumSupplements;
        return String.format(ConstantMessages.VALUE_SERVICE, serviceName, totalPrice);
    }

    @Override
    public String getStatistics() {
        StringBuilder outputMessage = new StringBuilder();
        //this.services.values().forEach(Service::getStatistics);
        for (Service service : this.services.values()) {
            outputMessage.append(service.getStatistics()).append(System.lineSeparator());
        }
        return outputMessage.toString().trim();
    }
}
