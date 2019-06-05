package org.turkisi.lotterystats.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class WinTableEntry implements Serializable {

    private String oid;
    private BigDecimal winPerPerson;
    private Integer totalPeopleWon;
}
