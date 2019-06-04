package org.turkisi.lotterystats.model;

import lombok.*;

import javax.persistence.Entity;
import java.math.BigDecimal;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WinTableEntry {

    private String oid;
    private BigDecimal winPerPerson;
    private Integer totalPeopleWon;
}
