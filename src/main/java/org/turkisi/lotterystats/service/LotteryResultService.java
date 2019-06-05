package org.turkisi.lotterystats.service;

import org.glassfish.jersey.internal.guava.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.turkisi.lotterystats.dto.MPIDistrict;
import org.turkisi.lotterystats.dto.MPILotteryResult;
import org.turkisi.lotterystats.dto.MPIWinTable;
import org.turkisi.lotterystats.model.*;
import org.turkisi.lotterystats.repository.LotteryResultRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
@Component
public class LotteryResultService {

    private static final Logger logger = LoggerFactory.getLogger(LotteryResultService.class);

    private static final DateTimeFormatter slashedDateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter comboValueDateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    private MPIClientService mpiClientService;
    private LotteryResultRepository repository;

    public LotteryResultService(MPIClientService mpiClientService, LotteryResultRepository repository) {
        this.mpiClientService = mpiClientService;
        this.repository = repository;
    }

    public List<LotteryResult> saveAllAvailableMpiSayisalLotteries() {

        LocalDate latestLotteryResultDate = repository.getLatestLotteryResultDate();
        logger.debug("Latest lottery result date recorded is: {}", latestLotteryResultDate);
        List<String> availableDates = mpiClientService.getAvailableDates();
        logger.debug("There are {} available dates in MPI's site", availableDates.size());
        if (latestLotteryResultDate != null) {
            availableDates = availableDates.stream().filter(dateStr -> LocalDate.parse(dateStr, comboValueDateTimeFormatter).isAfter(latestLotteryResultDate)).collect(Collectors.toList());
        }

        logger.debug("Reduced the dates to request to {}", availableDates.size());
        List<LotteryResult> resultList = new ArrayList<>(availableDates.size());
        if (!availableDates.isEmpty()) {
            availableDates.forEach(date -> resultList.add(saveMpiSayisalLotteryByDate(date)));
        }
        postSaveProcessing();
        return resultList;
    }

    public LotteryResult saveMpiSayisalLotteryByDate(String date) {
        logger.debug("Fetching lottery result from MPI for date {}", date);
        MPILotteryResult mpiLotteryResult = mpiClientService.getLotteryResult(date);
        logger.debug("Saving lottery result for date {}", date);
        LotteryResult lotteryResult = getLotteryResult(mpiLotteryResult);

        return repository.save(lotteryResult);
    }

    private void postSaveProcessing() {
        logger.debug("Post Save Process started");
        ArrayList<LotteryResult> list = Lists.newArrayList(repository.findAll());
        list.sort(Comparator.comparing(LotteryResult::getLotteryDate).reversed());
        for (int i = 0; i < list.size(); i++) {
            if (i < list.size() - 1) {
                LotteryResult current = list.get(i);
                LotteryResult earlier = list.get(i + 1);
                if (current.getCarryOverCount() == 0 && earlier.getCarryOverCount() > 0) {
                    current.setWonAfterCarryOvers(earlier.getCarryOverCount());
                }
            }
        }
        repository.saveAll(list);
        logger.debug("Post Save Process ended");
    }

    private LotteryResult getLotteryResult(MPILotteryResult mpiLotteryResult) {
        return LotteryResult.builder()
                .amountForPrize(mpiLotteryResult.getIkramiyeEH()).biggestPrize(mpiLotteryResult.getBuyukIkramiye())
                .carriedOver(mpiLotteryResult.getDevretti()).carryOverAmount(mpiLotteryResult.getHaftayaDevredenTutar())
                .carryOverCount(mpiLotteryResult.getDevirSayisi()).grossCyprus(mpiLotteryResult.getKibrisHasilati())
                .lotteryDate(formatDate(mpiLotteryResult.getCekilisTarihi())).lotteryNumber(mpiLotteryResult.getHafta())
                .lotteryType(mpiLotteryResult.getCekilisTuru()).numbers(mpiLotteryResult.getRakamlar())
                .numbersOrdered(mpiLotteryResult.getRakamlarNumaraSirasi()).oid(mpiLotteryResult.getOid())
                .prizeCarryOverAmount(mpiLotteryResult.getDevirTutari()).totalAmount(mpiLotteryResult.getToplamHasilat())
                .totalColumnsPlayed(mpiLotteryResult.getKolonSayisi()).totalFortuneGameTax(mpiLotteryResult.getSov())
                .vat(mpiLotteryResult.getKdv()).winnerCity(mpiLotteryResult.getBuyukIkramiyeKazananIl())
                .winnerDistricts(mpiLotteryResult.getBuyukIkrKazananIlIlceler().stream().map(this::getDistrict).collect(Collectors.toList()))
                .winTable(mpiLotteryResult.getBilenKisiler().stream().map(this::getWinTableEntry).collect(Collectors.toList()))
                .numberList(getNumberList(mpiLotteryResult))
                .build();
    }

    private Set<LotteryResultNumber> getNumberList(MPILotteryResult mpiLotteryResult) {
        HashSet<LotteryResultNumber> set = new HashSet<>();
        String[] numbers = mpiLotteryResult.getRakamlar().split("#");
        for (String numberStr : numbers) {
            set.add(LotteryResultNumber.builder()
                    .id(LotteryResultNumberId.builder().lotteryOid(mpiLotteryResult.getOid()).number(Integer.valueOf(numberStr)).build())
                    .lotteryDate(formatDate(mpiLotteryResult.getCekilisTarihi()))
                    .lotteryType(mpiLotteryResult.getCekilisTuru())
                    .build());
        }
        return set;
    }

    private WinTableEntry getWinTableEntry(MPIWinTable mpiWinTable) {
        return WinTableEntry.builder().oid(mpiWinTable.getOid())
                .totalPeopleWon(mpiWinTable.getKisiSayisi())
                .winPerPerson(mpiWinTable.getKisiBasinaDusenIkramiye())
                .build();
    }

    private District getDistrict(MPIDistrict mpiDistrict) {
        return District.builder()
                .cityId(mpiDistrict.getIl())
                .cityName(mpiDistrict.getIlView())
                .districtId(mpiDistrict.getIlce())
                .districtName(mpiDistrict.getIlceView())
                .build();
    }

    private LocalDate formatDate(String cekilisTarihi) {
        return LocalDate.parse(cekilisTarihi, slashedDateTimeFormatter);
    }
}
