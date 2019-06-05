package org.turkisi.lotterystats.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.turkisi.lotterystats.model.LotteryResult;
import org.turkisi.lotterystats.repository.LotteryResultRepository;

import static org.junit.Assert.assertNotNull;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LotteryResultServiceTest {

    @Autowired
    private LotteryResultService service;
    @Autowired
    private LotteryResultRepository repository;

    @Test
    public void should_create_lottery_result_in_database() throws Exception {
        // Given
        // When
        LotteryResult lotteryResult = service.saveMpiSayisalLotteryByDate("20180106");
        // Then
        assertNotNull(repository.findById(lotteryResult.getOid()));
    }
}