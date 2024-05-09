package ru.help.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Phone {

    private String phone;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Phone phone1)) return false;
        return Objects.equals(getPhone(), phone1.getPhone());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPhone());
    }
}
