package dktech.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", nullable = false)
    private long accountID;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToMany
    @JoinTable(
        name = "account_authorize_group",
        joinColumns = @JoinColumn(name = "account_id"),
        inverseJoinColumns = @JoinColumn(name = "authorize_group_id")
    )
    private Set<AuthorizeGroup> authorizeGroups;

    // Constructors, Getters, Setters, and toString

    public Account() {
        super();
    }

    public Account(String username, String password, String email, Employee employee) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.employee = employee;
        this.authorizeGroups = new HashSet<>();
    }

    // Getters and Setters
    public long getAccountID() {
        return accountID;
    }

    public void setAccountID(long accountID) {
        this.accountID = accountID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Set<AuthorizeGroup> getAuthorizeGroups() {
        return authorizeGroups;
    }

    public void setAuthorizeGroups(Set<AuthorizeGroup> authorizeGroups) {
        this.authorizeGroups = authorizeGroups;
    }

    @Override
    public String toString() {
        return "Account [accountID=" + accountID + ", username=" + username + ", email=" + email + ", employee=" + employee + "]";
    }
}
