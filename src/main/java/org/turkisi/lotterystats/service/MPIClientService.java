package org.turkisi.lotterystats.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.turkisi.lotterystats.dto.LotteryDateSelection;
import org.turkisi.lotterystats.dto.MPILotteryResult;
import org.turkisi.lotterystats.dto.MPILotteryResultResponse;
import org.turkisi.lotterystats.exception.UnexpectedResponseException;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
@Component
public class MPIClientService {

    private static final String MPI_RESULTS_BASE_URL = "http://mpi.gov.tr/sonuclar/";
    private static ObjectMapper objectMapper = new ObjectMapper();

    public List<String> getAvailableDates() {

        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(MPI_RESULTS_BASE_URL).pathSegment("listCekilisleriTarihleri.php").queryParam("tur", "sayisal");
        URI url = uriComponentsBuilder.build().toUri();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        if (responseEntity.getBody() != null) {
            try {
                String content = responseEntity.getBody().trim().substring(1); // a BOM-like character !?
                List<LotteryDateSelection> selectionList = objectMapper.readValue(content, new TypeReference<List<LotteryDateSelection>>() {

                });
                return selectionList.stream().map(LotteryDateSelection::getDate).collect(Collectors.toUnmodifiableList());
            } catch (IOException e) {
                throw new UnexpectedResponseException("Could not convert date selection to a concrete class ?!", e);
            }
        } else {
            throw new UnexpectedResponseException("Received null response from getAvailableDates for Milli Piyango");
        }
    }

    public MPILotteryResult getLotteryResult(String date) {

        URI uri = UriComponentsBuilder.fromHttpUrl(MPI_RESULTS_BASE_URL).pathSegment("cekilisler", "sayisal", "SAY_{date}.json").build(Map.of("date", date));
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Void> headResponse = restTemplate.exchange(uri, HttpMethod.HEAD, HttpEntity.EMPTY, Void.class);
            if (HttpStatus.NOT_FOUND.equals(headResponse.getStatusCode())) {
                // this is an older date, need to reconstruct the URL
                return getLotteryResultFromEarlierDate(date);
            } else {
                MPILotteryResultResponse body = restTemplate.getForEntity(uri, MPILotteryResultResponse.class).getBody();
                if (body != null) {
                    return body.getData();
                } else {
                    throw new UnexpectedResponseException("Could not get lottery result");
                }
            }
        } catch (HttpStatusCodeException e) {
            if (HttpStatus.NOT_FOUND.equals(e.getStatusCode())) {
                // this is an older date, need to reconstruct the URL
                return getLotteryResultFromEarlierDate(date);
            } else {
                throw e;
            }
        }
    }

    private MPILotteryResult getLotteryResultFromEarlierDate(String date) {
        URI uri = UriComponentsBuilder.fromHttpUrl(MPI_RESULTS_BASE_URL).pathSegment("cekilisler", "sayisal", "{date}.json").build(Map.of("date", date));
        MPILotteryResultResponse body = new RestTemplate().getForEntity(uri, MPILotteryResultResponse.class).getBody();
        if (body != null) {
            return body.getData();
        } else {
            throw new UnexpectedResponseException("Could not get lottery result");
        }
    }
}
