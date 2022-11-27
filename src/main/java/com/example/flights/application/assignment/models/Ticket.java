package com.example.flights.application.assignment.models;


public class Ticket {

    private Integer id;
    private Integer destinationId;
    private String baggageId;
    private Boolean checkinStatus;

    Ticket() {
        // empty
    }

    private Ticket(final Builder builder) {
        setId(builder.id);
        setDestinationId(builder.destinationId);
        setBaggageId(builder.baggageId);
        setCheckinStatus(builder.checkinStatus);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Integer getDestinationId() {
        return this.destinationId;
    }

    public void setDestinationId(final Integer destinationId) {
        this.destinationId = destinationId;
    }

    public String getBaggageId() {
        return this.baggageId;
    }

    public void setBaggageId(final String baggageId) {
        this.baggageId = baggageId;
    }

    public Boolean getCheckinStatus() {
        return this.checkinStatus;
    }

    public void setCheckinStatus(final Boolean checkinStatus) {
        this.checkinStatus = checkinStatus;
    }

    public static final class Builder {
        private Integer id;
        private Integer destinationId;
        private String baggageId;
        private Boolean checkinStatus;

        private Builder() {
        }

        public Builder id(final Integer val) {
            this.id = val;
            return this;
        }

        public Builder destinationId(final Integer val) {
            this.destinationId = val;
            return this;
        }

        public Builder baggageId(final String val) {
            this.baggageId = val;
            return this;
        }

        public Builder checkinStatus(final Boolean val) {
            this.checkinStatus = val;
            return this;
        }

        public Ticket build() {
            return new Ticket(this);
        }
    }
}
