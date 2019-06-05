package org.turkisi.lotterystats.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
@Entity
@Table(name = "milli_piyango_number")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class LotteryResultNumber implements Serializable {

    @EmbeddedId
    private LotteryResultNumberId id;

    @Column
    private LocalDate lotteryDate;

    @Column
    private String lotteryType;
}
