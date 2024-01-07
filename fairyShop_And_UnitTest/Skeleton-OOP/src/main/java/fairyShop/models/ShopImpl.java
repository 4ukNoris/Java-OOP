package fairyShop.models;

import java.util.Collection;

public class ShopImpl implements Shop {
    @Override
    public void craft(Present present, Helper helper) {

        Collection<Instrument> instruments = helper.getInstruments();
        boolean isDonePresent = false;

        for (Instrument instrument : instruments) {
            while (!instrument.isBroken() && helper.canWork()) {
                helper.work();
                present.getCrafted();
                instrument.use();
                if (present.isDone()) {
                    isDonePresent = true;
                    break;
                }
            }
            if (isDonePresent) {
                break;
            }
        }

    }
}
