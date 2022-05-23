package com.example.cryptus.model;

public class Birthday {
    Birthday birthDate;

    public Birthday(Birthday birthDate) {
        this.birthDate = birthDate;
    }

    public Birthday getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Birthday birthDate) {
        this.birthDate = birthDate;
    }
}
