package aquarium.entities.aquariums;

import aquarium.common.ConstantMessages;
import aquarium.common.ExceptionMessages;
import aquarium.entities.decorations.Decoration;
import aquarium.entities.fish.Fish;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public abstract class BaseAquarium implements Aquarium {
    private String name;
    private int capacity;
    private Collection<Decoration> decorations;
    private Collection<Fish> fish;

    public BaseAquarium(String name, int capacity) {
        this.setName(name);
        this.capacity = capacity;
        this.decorations = new ArrayList<>();
        this.fish = new ArrayList<>();
    }

    @Override
    public int calculateComfort() {
        return this.decorations.stream().mapToInt(Decoration::getComfort).sum();
    }

    @Override
    public String getName() {
        return this.name;
    }

    protected void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException(ExceptionMessages.AQUARIUM_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    @Override
    public void addFish(Fish fish) {
        if (this.capacity == this.fish.size()) {
            throw new IllegalStateException(ConstantMessages.NOT_ENOUGH_CAPACITY);
        }
        //Взимам типа на рибата и махам последната част от текста
        String fishWaterType = fish.getClass().getSimpleName().replaceAll("Fish","");

        // Правя проверка дали в типа на аквариума се съдържа типа на рибата и ако не хвърлям грешка
        if (!this.getClass().getSimpleName().contains(fishWaterType)){
            throw new IllegalStateException(ConstantMessages.WATER_NOT_SUITABLE);
        }
        this.fish.add(fish);
    }

    @Override
    public void removeFish(Fish fish) {
        this.fish.remove(fish);
    }

    @Override
    public void addDecoration(Decoration decoration) {
        this.decorations.add(decoration);
    }

    @Override
    public void feed() {
        this.fish.forEach(Fish::eat);
    }

    @Override
    public String getInfo() {


        String fishData = "none";
        if (!this.fish.isEmpty()) {
            fishData = this.fish.stream().map(Fish::getName).collect(Collectors.joining(" "));
        }
        return String.format("%s (%s):%n" +
                "Fish: %s%n" +
                "Decorations: %d%n" +
                "Comfort: %d", this.name, this.getClass().getSimpleName(), fishData, decorations.size(), calculateComfort());

    }

    @Override
    public Collection<Fish> getFish() {
        return this.fish;
    }

    @Override
    public Collection<Decoration> getDecorations() {
        return this.decorations;
    }
}
