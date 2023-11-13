package com.backend.tomato.entitites;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
public class OneTimePassword {

    @Id
    @NonNull
    private String id;
    @NonNull
    private Integer oneTimePasswordCode;
    @NonNull
    private Date expires;
}