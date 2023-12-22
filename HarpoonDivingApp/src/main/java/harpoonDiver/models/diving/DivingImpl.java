package harpoonDiver.models.diving;

import harpoonDiver.models.diver.Diver;
import harpoonDiver.models.divingSite.DivingSite;

import java.util.ArrayDeque;
import java.util.Collection;

public class DivingImpl implements Diving {
    @Override
    public void searching(DivingSite divingSite, Collection<Diver> divers) {

        ArrayDeque<String> creatures = fillDeque(divingSite);

        for (Diver diver : divers) {

            while (diver.canDive()) { //може списъка със създания да е празен в началото
                String creature = creatures.poll();

                diver.getSeaCatch().getSeaCreatures().add(creature);
                diver.shoot();
                divingSite.getSeaCreatures().remove(creature);
                if (creatures.isEmpty()) {
                    return;
                }
            }
        }
    }
    private static ArrayDeque<String> fillDeque(DivingSite divingSite) {
        ArrayDeque<String> creatures = new ArrayDeque<>();
        for (String divS : divingSite.getSeaCreatures()) {
            creatures.offer(divS);
        }
        return creatures;
    }
}
