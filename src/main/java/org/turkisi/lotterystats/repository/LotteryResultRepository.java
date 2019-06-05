package org.turkisi.lotterystats.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.turkisi.lotterystats.model.LotteryResult;

import java.time.LocalDate;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
public interface LotteryResultRepository extends CrudRepository<LotteryResult, String> {

    @Query("select max(l.lotteryDate) from LotteryResult l")
    LocalDate getLatestLotteryResultDate();
}
