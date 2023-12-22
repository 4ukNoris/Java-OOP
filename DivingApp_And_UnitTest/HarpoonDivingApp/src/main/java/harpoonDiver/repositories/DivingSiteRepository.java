package harpoonDiver.repositories;

import harpoonDiver.models.divingSite.DivingSite;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class DivingSiteRepository implements Repository<DivingSite> {
    private Map<String, DivingSite> sites;

    public DivingSiteRepository() {
        this.sites = new LinkedHashMap<>();
    }

    @Override
    public Collection<DivingSite> getCollection() {
        return Collections.unmodifiableCollection(this.sites.values());
    }

    @Override
    public void add(DivingSite divingSite) {
        this.sites.putIfAbsent(divingSite.getName(), divingSite);
    }

    @Override
    public boolean remove(DivingSite divingSite) {
        return this.sites.remove(divingSite.getName()) != null;
    }

    @Override
    public DivingSite byName(String divingSiteName) {
        return this.sites.get(divingSiteName);
    }
}
