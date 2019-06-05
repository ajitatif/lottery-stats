package org.turkisi.lotterystats.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class District implements Serializable {
    private Integer cityId;
    private String cityName;
    private Integer districtId;
    private String districtName;
}
