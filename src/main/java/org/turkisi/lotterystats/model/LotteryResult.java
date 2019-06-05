package org.turkisi.lotterystats.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
@Entity
@Table(name = "milli_piyango_result")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LotteryResult implements Serializable {
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
    @Column(name = "is_carried_over")
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
    private List<District> winnerDistricts = new ArrayList<>();
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<WinTableEntry> winTable = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "lottery_oid")
    private Set<LotteryResultNumber> numberList = new HashSet<>();

    @Column
    private Integer wonAfterCarryOvers;
}
