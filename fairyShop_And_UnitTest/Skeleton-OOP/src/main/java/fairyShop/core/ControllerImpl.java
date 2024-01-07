package fairyShop.core;

import fairyShop.common.ConstantMessages;
import fairyShop.common.ExceptionMessages;
import fairyShop.models.*;
import fairyShop.repositories.HelperRepository;
import fairyShop.repositories.PresentRepository;

import java.util.Collection;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {
    private static final int MIN_POWER_TO_WORK = 50;
    private HelperRepository helpers;
    private PresentRepository presents;
    private int countCraftedPresents;

    public ControllerImpl() {
        this.helpers = new HelperRepository();
        this.presents = new PresentRepository();
    }

    @Override
    public String addHelper(String type, String helperName) {
        Helper helper;
        switch (type) {
            case "Happy":
                helper = new Happy(helperName);
                break;
            case "Sleepy":
                helper = new Sleepy(helperName);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.HELPER_TYPE_DOESNT_EXIST);
        }
        this.helpers.add(helper);
        return String.format(ConstantMessages.ADDED_HELPER, type, helperName);
    }

    @Override
    public String addInstrumentToHelper(String helperName, int power) {
        Instrument instrument = new InstrumentImpl(power);
        Helper helper = this.helpers.findByName(helperName);
        if (helper == null) {
            throw new IllegalArgumentException(ExceptionMessages.HELPER_DOESNT_EXIST);
        }
        helper.addInstrument(instrument);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_INSTRUMENT_TO_HELPER, power, helperName);
    }

    @Override
    public String addPresent(String presentName, int energyRequired) {
        Present present = new PresentImpl(presentName, energyRequired);
        this.presents.add(present);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_PRESENT, presentName);
    }

    @Override
    public String craftPresent(String presentName) {
        Present present = this.presents.findByName(presentName);
        Collection<Helper> helpersCanWork = this.helpers.getModels()
                .stream()
                .filter(h -> h.getEnergy() > MIN_POWER_TO_WORK)
                .collect(Collectors.toList());
        if (helpersCanWork.isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessages.NO_HELPER_READY);
        }

        Shop shop = new ShopImpl();
        int countBrokenInstruments = 0;
        for (Helper helper : helpersCanWork) {
            shop.craft(present, helper);
            countBrokenInstruments += helper.getInstruments().stream().filter(Instrument::isBroken).count();
            if (present.isDone()) {
                this.countCraftedPresents++;
                break;
            }
        }
        String presentInfo = "done";
        if (!present.isDone()) {
            presentInfo = "not done";
        }
        return String.format(ConstantMessages.PRESENT_DONE, presentName, presentInfo)
                + String.format(ConstantMessages.COUNT_BROKEN_INSTRUMENTS, countBrokenInstruments);
    }

    @Override
    public String report() {
        //"{countCraftedPresents} presents are done!"
        //"Helpers info:"
        //"Name: {helperName1}"
        //"Energy: {helperEnergy1}"
        //"Instruments: {countInstruments} not broken left"

        StringBuilder printInformation = new StringBuilder(String.format("%d presents are done!", this.countCraftedPresents));
        printInformation.append(System.lineSeparator())
                .append("Helpers info:");
        for (Helper helper : helpers.getModels()){
            printInformation.append(System.lineSeparator());
            printInformation.append(String.format("Name: %s", helper.getName()))
                    .append(System.lineSeparator())
                    .append(String.format("Energy: %d", helper.getEnergy()))
                    .append(System.lineSeparator())
                    .append(String.format("Instruments: %d not broken left", helper.getInstruments()
                            .stream()
                            .filter(instrument -> !instrument.isBroken())
                            .count()));
        }
        return printInformation.toString().trim();
    }
}
