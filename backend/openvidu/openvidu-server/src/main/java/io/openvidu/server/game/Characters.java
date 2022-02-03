package io.openvidu.server.game;

import io.openvidu.server.core.Participant;

public class Characters {
    private Roles roles;
    private boolean isAlive = true;
    private Participant participant;

    public Characters(Roles roles, Participant participant) {
        this.roles = roles;
        this.participant = participant;
    }

    public Roles getRoles() {
        return roles;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }
}
