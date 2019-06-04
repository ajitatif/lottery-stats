package org.turkisi.lotterystats.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
@Getter
@Setter
public class MPILotteryResult {

    private String oid;
    private Integer hafta;
    private String buyukIkramiyeKazananIl;
    private String cekilisTarihi;
    private String cekilisTuru;
    private String rakamlar;
    private String rakamlarNumaraSirasi;
    private Boolean devretti;
    private Integer devirSayisi;
    private BigDecimal kibrisHasilati;
    private BigDecimal devirTutari;
    private Integer kolonSayisi;
    private BigDecimal kdv;
    private BigDecimal toplamHasilat;
    private BigDecimal hasilat;
    private BigDecimal sov;
    private BigDecimal ikramiyeEH;
    private BigDecimal buyukIkramiye;
    private BigDecimal haftayaDevredenTutar;
    private List<MPIDistrict> buyukIkrKazananIlIlceler = new ArrayList<>();
    private List<MPIWinTable> bilenKisiler = new ArrayList<>();
}
