package org.imaginemos.billingservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clientId;


    private double total;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha = new Date();
}

