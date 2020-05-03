package com.ad.json.pojo;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "address",
    "port",
    "dbname",
    "username",
    "password"
})
public class DatosConexion implements Serializable
{

    @JsonProperty("address")
    private String address;
    @JsonProperty("port")
    private String port;
    @JsonProperty("dbname")
    private String dbname;
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
    private final static long serialVersionUID = -3683661991262421735L;

    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(String address) {
        this.address = address;
    }

    @JsonProperty("port")
    public String getPort() {
        return port;
    }

    @JsonProperty("port")
    public void setPort(String port) {
        this.port = port;
    }

    @JsonProperty("dbname")
    public String getDbname() {
        return dbname;
    }

    @JsonProperty("dbname")
    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("address", address).append("port", port).append("dbname", dbname).append("username", username).append("password", password).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(password).append(address).append(dbname).append(port).append(username).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DatosConexion) == false) {
            return false;
        }
        DatosConexion rhs = ((DatosConexion) other);
        return new EqualsBuilder().append(password, rhs.password).append(address, rhs.address).append(dbname, rhs.dbname).append(port, rhs.port).append(username, rhs.username).isEquals();
    }

}
