package com.example.springweather.city;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Сущность города в базе данных")
@Table(name = "city")
public class CityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "id", example = "1")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @Column(nullable = false, name = "name")
    @Schema(description = "Название города", example = "Санкт-Петербург")
    @NotNull
    private String name;
    @Column(nullable = false, name = "latitude")
    @Schema(description = "Широта", example = "59.938732")
    @NotNull
    private String latitude;
    @Column(nullable = false, name = "longitude")
    @Schema(description = "Широта", example = "30.316229")
    @NotNull
    private String longitude;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || Hibernate.getClass(this) != Hibernate.getClass(obj)) return false;
        CityEntity that = (CityEntity) obj;
        return getId() != null && Objects.equals(getId(), that.getId());
    }


}
