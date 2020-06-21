package com.luminor.task.payment.db.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "client_ip", schema = "payments")
public class ClientIpEntity {
    private int id;
    private int ipAddressInt;
    private String ipAddress;
    private String domain;
    private String countryCode;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ip_address_int")
    public int getIpAddressInt() {
        return ipAddressInt;
    }

    public void setIpAddressInt(int ipAddressInt) {
        this.ipAddressInt = ipAddressInt;
    }

    @Basic
    @Column(name = "ip_address")
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Basic
    @Column(name = "domain")
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Basic
    @Column(name = "country_code")
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientIpEntity that = (ClientIpEntity) o;
        return id == that.id &&
            ipAddressInt == that.ipAddressInt &&
            Objects.equals(ipAddress, that.ipAddress) &&
            Objects.equals(domain, that.domain) &&
            Objects.equals(countryCode, that.countryCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ipAddressInt, ipAddress, domain, countryCode);
    }
}
