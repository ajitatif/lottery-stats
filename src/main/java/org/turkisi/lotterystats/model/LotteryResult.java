package org.turkisi.lotterystats.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
@Entity
@Table(name = "milli_piyango_results")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LotteryResult {
    @Id
    private String oid;
    @Column
    private LocalDate lotteryDate;
    @Column
    private Integer lotteryNumber;
    @Column
    private String winnerCity;
    @Column
    private String lotteryType;
    @Column
    private String numbers;
    @Column
    private String numbersOrdered;
    @Column
    private Boolean carriedOver;
    @Column
    private Integer carryOverCount;
    @Column
    private BigDecimal grossCyprus;
    @Column
    private BigDecimal carryOverAmount;
    @Column
    private Integer totalColumnsPlayed;
    @Column
    private BigDecimal vat;
    @Column
    private BigDecimal totalAmount;
    @Column
    private BigDecimal totalFortuneGameTax;
    @Column
    private BigDecimal amountForPrize;
    @Column
    private BigDecimal biggestPrize;
    @Column
    private BigDecimal prizeCarryOverAmount;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<District> winnerDistricts;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<WinTableEntry> winTable;
}
