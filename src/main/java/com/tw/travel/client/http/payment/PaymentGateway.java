package com.tw.travel.client.http.payment;

import java.util.Objects;

public class PaymentGateway {
    private String host;

    @Override
    public String toString() {
        return "PaymentGateway{" +
                "host='" + host + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentGateway that = (PaymentGateway) o;
        return Objects.equals(host, that.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host);
    }

    public String getHost() {
        return host;
    }

    public PaymentGateway(String host) {
        this.host = host;
    }
}
