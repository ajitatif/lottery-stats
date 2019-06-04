package org.turkisi.lotterystats.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class District {
    private Integer cityId;
    private String cityName;
    private Integer districtId;
    private String districtName;
}
