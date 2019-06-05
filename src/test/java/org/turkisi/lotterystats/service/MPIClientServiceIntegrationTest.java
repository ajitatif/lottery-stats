package org.turkisi.lotterystats.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.turkisi.lotterystats.dto.MPILotteryResult;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MPIClientServiceIntegrationTest {

    @Autowired
    private MPIClientService parserService;

    @Test
    public void should_get_dates_from_milli_piyango() throws Exception {
        // Given
        // When
        List<String> availableDates = parserService.getAvailableDates();
        // Then
        assertFalse(availableDates.isEmpty());
    }

    @Test
    public void should_get_lottery_with_old_date() throws Exception {
        // Given
        String oldDate = "20180106";
        // When
        MPILotteryResult result = parserService.getLotteryResult(oldDate);
        // Then
        assertEquals("3zyggfjc3nog6s00", result.getOid());
    }

    @Test
    public void shoud_get_lottery_with_new_date() throws Exception {
        // Given
        String oldDate = "20190601";
        // When
        MPILotteryResult result = parserService.getLotteryResult(oldDate);
        // Then
        assertEquals("3zw96gjwdtp5o400", result.getOid());
    }
}