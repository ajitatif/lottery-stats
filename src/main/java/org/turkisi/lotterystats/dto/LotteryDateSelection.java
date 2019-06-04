package org.turkisi.lotterystats.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
@Getter
@Setter
@NoArgsConstructor
public class LotteryDateSelection {

    @JsonProperty("tarih")
    private String date;
    @JsonProperty("tarihView")
    private String dateView;
}
