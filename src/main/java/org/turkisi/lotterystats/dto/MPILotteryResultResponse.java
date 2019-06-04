package org.turkisi.lotterystats.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
@Getter
@Setter
public class MPILotteryResultResponse {

    private Boolean success;
    private MPILotteryResult data;
}
