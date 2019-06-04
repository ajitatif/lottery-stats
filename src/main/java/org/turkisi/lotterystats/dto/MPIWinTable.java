package org.turkisi.lotterystats.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
@Getter
@Setter
public class MPIWinTable {
    private String oid;
    private BigDecimal kisiBasinaDusenIkramiye;
    private Integer kisiSayisi;
    private String tur;
}
