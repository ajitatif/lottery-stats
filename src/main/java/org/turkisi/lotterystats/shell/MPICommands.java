package org.turkisi.lotterystats.shell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.turkisi.lotterystats.model.LotteryResult;
import org.turkisi.lotterystats.service.LotteryResultService;

import java.text.MessageFormat;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
@ShellComponent
public class MPICommands {
    private static final Logger logger = LoggerFactory.getLogger(MPICommands.class);

    private LotteryResultService lotteryResultService;

    public MPICommands(LotteryResultService lotteryResultService) {
        this.lotteryResultService = lotteryResultService;
    }

    @ShellMethod(key = {"save-all-available"}, value = "save all available dates not already on db")
    public String saveAllAvailableDates() {

        List<LotteryResult> resultList = lotteryResultService.saveAllAvailableMpiSayisalLotteries();
        StringJoiner stringJoiner = new StringJoiner("\n").setEmptyValue("<none>");
        resultList.stream().map(LotteryResult::getLotteryDate).forEach(localDate -> stringJoiner.add(localDate.toString()));
        return MessageFormat.format("Saved lotteries for dates: \n{0}", stringJoiner.toString());
    }
}
