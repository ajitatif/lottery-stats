package org.turkisi.lotterystats.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LotteryResultNumberId implements Serializable {

    @Column(name = "lottery_oid")
    private String lotteryOid;
    @Column
    private Integer number;
}
