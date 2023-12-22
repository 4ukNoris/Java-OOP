package harpoonDiver.core;

import harpoonDiver.common.ConstantMessages;
import harpoonDiver.common.ExceptionMessages;
import harpoonDiver.models.diver.DeepWaterDiver;
import harpoonDiver.models.diver.Diver;
import harpoonDiver.models.diver.OpenWaterDiver;
import harpoonDiver.models.diver.WreckDiver;
import harpoonDiver.models.diving.Diving;
import harpoonDiver.models.diving.DivingImpl;
import harpoonDiver.models.divingSite.DivingSite;
import harpoonDiver.models.divingSite.DivingSiteImpl;
import harpoonDiver.repositories.DiverRepository;
import harpoonDiver.repositories.DivingSiteRepository;
import harpoonDiver.repositories.Repository;

import java.util.List;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {
    private static final int NEEDED_MIN_OXYGEN = 30;
    private static final String EMPTY_CREATURES_LIST = "None";
    private Repository<Diver> divers;
    private Repository<DivingSite> divingSites;
    private int countSites;

    public ControllerImpl() {
        this.divers = new DiverRepository();
        this.divingSites = new DivingSiteRepository();
    }

    @Override
    public String addDiver(String kind, String diverName) {
        Diver diver;
        switch (kind) {
            case "DeepWaterDiver":
                diver = new DeepWaterDiver(diverName);
                break;
            case "OpenWaterDiver":
                diver = new OpenWaterDiver(diverName);
                break;
            case "WreckDiver":
                diver = new WreckDiver(diverName);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.DIVER_INVALID_KIND);
        }
        this.divers.add(diver);
        return String.format(ConstantMessages.DIVER_ADDED, kind, diverName);
    }

    @Override
    public String addDivingSite(String siteName, String... seaCreatures) {
        DivingSite divingSites = new DivingSiteImpl(siteName);
        for (String cr : seaCreatures) {
            divingSites.getSeaCreatures().add(cr);
        }
        this.divingSites.add(divingSites);
        return String.format(ConstantMessages.DIVING_SITE_ADDED, siteName);
    }

    @Override
    public String removeDiver(String diverName) {
        Diver diver = this.divers.byName(diverName);
        if (diver == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.DIVER_DOES_NOT_EXIST, diverName));
        }
        this.divers.remove(diver);
        return String.format(ConstantMessages.DIVER_REMOVE, diverName);
    }

    @Override
    public String startDiving(String siteName) {
        List<Diver> suitableDivers = this.divers.getCollection()
                .stream()
                .filter(diver -> diver.getOxygen() > NEEDED_MIN_OXYGEN)
                .collect(Collectors.toList());
        if (suitableDivers.isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessages.SITE_DIVERS_DOES_NOT_EXISTS);
        }
        DivingSite divingSite = this.divingSites.byName(siteName);
        Diving diving = new DivingImpl();
        diving.searching(divingSite, suitableDivers);
        long removesDivers = suitableDivers.stream().filter(d-> !d.canDive()).count();
        this.countSites++;
        return String.format(ConstantMessages.SITE_DIVING, siteName, removesDivers);
    }

    @Override
    public String getStatistics() {
        StringBuilder finalResult = new StringBuilder(String.format(ConstantMessages.FINAL_DIVING_SITES, this.countSites))
                .append(System.lineSeparator())
                .append(ConstantMessages.FINAL_DIVERS_STATISTICS);
        for (Diver div : this.divers.getCollection()) {
            String catchCreaturesNames = EMPTY_CREATURES_LIST;
            if (!div.getSeaCatch().getSeaCreatures().isEmpty()) {
                catchCreaturesNames = String.join(ConstantMessages.FINAL_DIVER_CATCH_DELIMITER, div.getSeaCatch().getSeaCreatures());
            }
            finalResult.append(System.lineSeparator())
                    .append(String.format(ConstantMessages.FINAL_DIVER_NAME, div.getName()))
                    .append(System.lineSeparator())
                    .append(String.format(ConstantMessages.FINAL_DIVER_OXYGEN, div.getOxygen()))
                    .append(System.lineSeparator())
                    .append(String.format(ConstantMessages.FINAL_DIVER_CATCH, catchCreaturesNames));
        }
        return finalResult.toString().trim();
    }
}
