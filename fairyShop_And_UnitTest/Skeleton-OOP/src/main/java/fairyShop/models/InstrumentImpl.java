package fairyShop.models;

import fairyShop.common.ExceptionMessages;

public class InstrumentImpl implements Instrument {
    private static final int DECREASE_POWER = 10;
    private int power;

    public InstrumentImpl(int power) {
        this.setPower(power);
    }

    @Override
    public int getPower() {
        return this.power;
    }

    protected void setPower(int power) {
        if (power < 0) {
            throw new IllegalArgumentException(ExceptionMessages.INSTRUMENT_POWER_LESS_THAN_ZERO);
        }
        this.power = power;
    }

    @Override
    public void use() {
        this.setPower(Math.max(0, this.power - DECREASE_POWER));
    }

    @Override
    public boolean isBroken() {
        return this.power <= 0;
    }
}
