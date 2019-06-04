/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
package org.turkisi.lotterystats.model;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;