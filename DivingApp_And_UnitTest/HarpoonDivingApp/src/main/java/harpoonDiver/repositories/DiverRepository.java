package harpoonDiver.repositories;

import harpoonDiver.models.diver.Diver;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class DiverRepository implements Repository<Diver> {
    private Map<String, Diver> divers;

    public DiverRepository() {
        this.divers = new LinkedHashMap<>();
    }

    @Override
    public Collection<Diver> getCollection() {
        return Collections.unmodifiableCollection(this.divers.values());
    }

    @Override
    public void add(Diver diver) {
        this.divers.putIfAbsent(diver.getName(), diver);
    }

    @Override
    public boolean remove(Diver diver) {
        return this.divers.remove(diver.getName()) != null;
    }

    @Override
    public Diver byName(String diverName) {
        return this.divers.get(diverName);
    }
}
