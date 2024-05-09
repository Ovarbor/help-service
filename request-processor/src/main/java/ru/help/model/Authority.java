package ru.help.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Authority {

    @Enumerated(EnumType.STRING)
    private EnumAuth authority;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Authority authority1)) return false;
        return getAuthority() == authority1.getAuthority();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getAuthority());
    }
}
