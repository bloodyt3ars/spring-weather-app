package com.example.springweather.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Сущность города в базе данных")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "id", example = "1")
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


}
